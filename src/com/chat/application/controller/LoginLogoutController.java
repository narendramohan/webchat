/**
 * 
 */
package com.chat.application.controller;


import static jj.play.ns.nl.captcha.Captcha.NAME;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jj.play.ns.nl.captcha.Captcha;
import jj.play.ns.nl.captcha.backgrounds.GradiatedBackgroundProducer;
import jj.play.ns.nl.captcha.text.renderer.ColoredEdgesWordRenderer;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.chat.application.bean.ActiveUserBean;
import com.chat.application.domain.User;
import com.chat.application.form.LoginForm;
import com.chat.application.service.ChatService;
import com.chat.application.service.EmailService;
import com.chat.application.validator.EmailValidator;
import com.chat.application.validator.LoginValidator;

/**
 * Handles and retrieves the login or denied page depending on the URI template
 */
@Controller
@RequestMapping("/auth")
public class LoginLogoutController {
    private static final List<Color> COLORS = new ArrayList<Color>(2);
    private static final List<Font> FONTS = new ArrayList<Font>(3);
    
    static {
    	COLORS.add(Color.BLACK);
        COLORS.add(Color.BLUE);

        FONTS.add(new Font("Geneva", Font.ITALIC, 48));
        FONTS.add(new Font("Courier", Font.BOLD, 48));
        FONTS.add(new Font("Arial", Font.BOLD, 48));
    }

	static Map<String, Integer> failedLoginAttempts = new HashMap<String, Integer>();
	@Resource(name = "chatService")
	private ChatService service;
	
	@Resource(name = "emailService")
	private EmailService emailService;
	
	List<User> users = new ArrayList<User>();
	List<ActiveUserBean> activeUsers = new ArrayList<ActiveUserBean>();
	protected static Logger logger = Logger.getLogger("controller");
	@RequestMapping(value = "/captcha")
	public void genrateCapctha(HttpServletRequest request,HttpServletResponse response, HttpSession session){
        ColoredEdgesWordRenderer wordRenderer = new ColoredEdgesWordRenderer(COLORS, FONTS);
        Captcha captcha = new Captcha.Builder(200, 48).addText(wordRenderer)
                .gimp()
                .addNoise()
                .addBackground(new GradiatedBackgroundProducer())
                .build();

        //CaptchaServletUtil.writeImage(response, captcha.getImage());
        request.getSession().setAttribute(NAME, captcha.getAnswer());		
	}
	@RequestMapping(value = "/login-user")
	public String loginUser(@RequestParam(value="error", required=false) boolean error, ModelMap model, LoginForm loginForm,
			BindingResult model1, HttpServletRequest request, HttpSession session) {
		String submit = request.getParameter("submit");
		logger.debug("submit: "+submit);
		User user1 = (User)session.getAttribute("user");
		if(user1!=null)
			return "home-page";
		if(submit!=null) {
		LoginValidator validator = new LoginValidator();
		validator.validate(loginForm, model1);
		}
		
		if(model1.hasErrors()){
            loginForm.setUserId("");
            loginForm.setPassword("");
            logger.debug(model1.getAllErrors());
            
            return "login-page";
        } else if (loginForm.getUserId() != null && loginForm.getPassword() != null) {
			User user = service.loginUser(loginForm);
			logger.debug(user);
			
			if (user != null && user.getStatus()!=1) {
				if (session.getAttribute("userName") == null) {
					session = request.getSession(true);
					session.setAttribute("user", user);
					session.setAttribute("userName", user.getLoginId());
					session.setAttribute("username", user.getName());
					ServletContext context = session.getServletContext();
					users.add(user);
					ActiveUserBean aUser = new ActiveUserBean();
					aUser.setName(user.getLoginId());
					aUser.setIp(request.getRemoteAddr());
					System.out.println(request.getLocalAddr() +" "+request.getLocalName()+" "+request.getRemoteUser());
					activeUsers.add(aUser);
					context.setAttribute("onlineUsers", activeUsers);
					logger.debug(user);
					
				}
				if(failedLoginAttempts.containsKey(loginForm.getUserId())){
					int count = failedLoginAttempts.get(loginForm.getUserId());
					String captch = (String) session.getAttribute(NAME);
					if(count>3 && captch.equals(loginForm.getCaptcha())){
						failedLoginAttempts.put(loginForm.getUserId(), 0);
					} else {
						model.remove("error");
						model.addAttribute("error", "You have entered an invalid Captcha");
						request.setAttribute("failedtimes", 3);
						return "login-page";
					}
					
				}
				return "home-page";
			} else if(user != null && user.getStatus()==1){
				model.addAttribute("error", "You have been blocked due to unauthorized activity. please contact administrator.");
			} else {
				
				model.addAttribute("error", "You have entered an invalid username or password!");
				if(failedLoginAttempts.containsKey(loginForm.getUserId())){
					int count = failedLoginAttempts.get(loginForm.getUserId());
					failedLoginAttempts.put(loginForm.getUserId(), ++count);
					if(count>3) request.setAttribute("failedtimes", 3);
				} else {
					failedLoginAttempts.put(loginForm.getUserId(), 0);
				}
				
			}
		}
		return "login-page";
	}
	
	@RequestMapping(value = "/forgot-pwd")
	public String loginUser(ModelMap model, LoginForm loginForm,
			BindingResult model1, HttpServletRequest request, HttpSession session) {
		String submit = request.getParameter("submit");
		logger.debug("submit: "+submit);
		if(submit!=null) {
			EmailValidator validator = new EmailValidator();
			validator.validate(loginForm, model1);
		}
		if(model1.hasErrors()){
            loginForm.setEmailId("");
            logger.debug(model1.getAllErrors());
            return "forgot-pwd";
        } else if (loginForm.getEmailId() != null) {
        	User user =service.updatUser(loginForm);
        	if(user!= null)
        		emailService.sendForgotPasswordMail(loginForm);
        	
			
			return "forgot-pwd-sent";
        }
        System.out.println("test");	
		return "forgot-pwd";
	}	
	/**
	 * Handles and retrieves the login JSP page
	 * 
	 * @return the name of the JSP page
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String getLoginPage(@RequestParam(value="error", required=false) boolean error, 
			ModelMap model) {
		logger.debug("Received request to show login page");

		// Add an error message to the model if login is unsuccessful
		// The 'error' parameter is set to true based on the when the authentication has failed. 
		// We declared this under the authentication-failure-url attribute inside the spring-security.xml
		/* See below:
		 <form-login 
				login-page="/krams/auth/login" 
				authentication-failure-url="/krams/auth/login?error=true" 
				default-target-url="/krams/main/common"/>
		 */
		if (error == true) {
			// Assign an error message
			model.put("error", "You have entered an invalid username or password!");
		} else {
			model.put("error", "");
		}
		
		// This will resolve to /WEB-INF/jsp/loginpage.jsp
		return "loginpage";
	}
	
	/**
	 * Handles and retrieves the denied JSP page. This is shown whenever a regular user
	 * tries to access an admin only page.
	 * 
	 * @return the name of the JSP page
	 */
	@RequestMapping(value = "/denied", method = RequestMethod.GET)
 	public String getDeniedPage() {
		logger.debug("Received request to show denied page");
		
		// This will resolve to /WEB-INF/jsp/deniedpage.jsp
		return "deniedpage";
	}
}