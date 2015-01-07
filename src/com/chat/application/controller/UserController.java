package com.chat.application.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.chat.application.bean.UserBean;
import com.chat.application.domain.Friend;
import com.chat.application.domain.Node;
import com.chat.application.domain.SybilAttack;
import com.chat.application.domain.User;
import com.chat.application.form.LoginForm;
import com.chat.application.service.EmailService;
import com.chat.application.service.NodeServie;
import com.chat.application.service.ReportService;
import com.chat.application.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private ReportService reportService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private NodeServie nodeServie;
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView saveUser(@RequestParam(value="error", required=false) boolean error, ModelMap model, @ModelAttribute("command") UserBean userBean,
			BindingResult result) {

		User user = prepareModel(userBean);
		boolean exists = userService.isUserExists(user.getLoginId());
		if(!exists)
			userService.addUser(user);
		else {
			userService.addUser(user);
			//return new ModelAndView("addUser"); 
		}
		return new ModelAndView("redirect:users");
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView registerUser(@RequestParam(value="error", required=false) boolean error, ModelMap model, @ModelAttribute("command") UserBean userBean,
			BindingResult result) {
		User user = prepareModel(userBean);
		user.setType(0);
		try {
			boolean exists = userService.isUserExists(user.getLoginId());
			if(!exists) {
				boolean emailExists = userService.isEmailExists(user.getEmail());
				if(!emailExists) {
					userService.addUser(user);
				} else {
					model.addAttribute("error", "This email id already exists.");
					return new ModelAndView("register"); 
				}
			} else {
				model.addAttribute("error", "User already exists.");
				return new ModelAndView("register"); 
			}
			return new ModelAndView("registerSuccess");
		} catch(Exception e){
			e.printStackTrace();
			return new ModelAndView("registerFailure");
		}
		
	}	

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public ModelAndView listUsers( HttpServletRequest request,HttpSession session, LoginForm loginForm) {
		User user1 = (User)session.getAttribute("user");
		if(user1.getType()!=1){
			SybilAttack s = new SybilAttack();
        	s.setDate(new Date(Calendar.getInstance().getTimeInMillis()));
        	s.setDestinationNode("n");
        	s.setSourceNode("m");
        	s.setIp(request.getRemoteAddr());
        	s.setUser((String)request.getSession().getAttribute("username"));
        	reportService.addReportData(s);
        	userService.blockUser((String)request.getSession().getAttribute("username"));
        	return new ModelAndView("login-page");
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("users", prepareListofBean(userService.listUser()));
		return new ModelAndView("listUser", model);
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView addUser(@RequestParam(value="error", required=false) boolean error, ModelMap model, @ModelAttribute("command") UserBean userBean,
			BindingResult result, LoginForm loginForm, HttpServletRequest request,HttpSession session) {
		User user1 = (User)session.getAttribute("user");
		if(user1.getType()!=1){
			SybilAttack s = new SybilAttack();
        	s.setDate(new Date(Calendar.getInstance().getTimeInMillis()));
        	s.setDestinationNode("n");
        	s.setSourceNode("m");
        	s.setIp(request.getRemoteAddr());
        	s.setUser((String)request.getSession().getAttribute("username"));
        	reportService.addReportData(s);
        	userService.blockUser((String)request.getSession().getAttribute("username"));
        	return new ModelAndView("login-page");
		}
		User user = new User();
		user.setType(0);
		model.put("user", user);
		//Map<String, Object> model = new HashMap<String, Object>();
		//model.put("users", prepareListofBean(userService.listUser()));
		return new ModelAndView("addUser");
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView welcome() {
		return new ModelAndView("index");
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView editUser(@ModelAttribute("command") UserBean userBean,
			BindingResult result, LoginForm loginForm, HttpServletRequest request,HttpSession session) {
		User user1 = (User)session.getAttribute("user");
		if(user1.getType()!=1){
			SybilAttack s = new SybilAttack();
        	s.setDate(new Date(Calendar.getInstance().getTimeInMillis()));
        	s.setDestinationNode("n");
        	s.setSourceNode("m");
        	s.setIp(request.getRemoteAddr());
        	s.setUser((String)request.getSession().getAttribute("username"));
        	reportService.addReportData(s);
        	userService.blockUser((String)request.getSession().getAttribute("username"));
        	return new ModelAndView("login-page");
		}
		userService.deleteUser(prepareModel(userBean));
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("user", null);
		model.put("users", prepareListofBean(userService.listUser()));
		return new ModelAndView("listUser", model);
	}
	
	@RequestMapping(value = "/activeUser", method = RequestMethod.GET)
	public String activeUser(HttpServletRequest request, HttpSession session){
		return "activeUsers";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView deleteUser(
			@ModelAttribute("command") UserBean userBean, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("user", prepareUserBean(userService.getUser(userBean.getId())));
		model.put("users", prepareListofBean(userService.listUser()));
		return new ModelAndView("addUser", model);
	}
	
	@RequestMapping(value = "activate", method = RequestMethod.GET)
	public ModelAndView activate(
			@ModelAttribute("command") UserBean userBean, BindingResult result, LoginForm loginForm, HttpServletRequest request,HttpSession session) {
		User user1 = (User)session.getAttribute("user");
		if(user1.getType()!=1){
			SybilAttack s = new SybilAttack();
        	s.setDate(new Date(Calendar.getInstance().getTimeInMillis()));
        	s.setDestinationNode("n");
        	s.setSourceNode("m");
        	s.setIp(request.getRemoteAddr());
        	s.setUser((String)request.getSession().getAttribute("username"));
        	reportService.addReportData(s);
        	userService.blockUser((String)request.getSession().getAttribute("username"));
        	return new ModelAndView("login-page");
		}
		userService.activateUser(prepareModel(userBean));
		Map<String, Object> model = new HashMap<String, Object>();
		//model.put("user", prepareUserBean(userService.getUser(userBean.getId())));
		model.put("users", prepareListofBean(userService.listUser()));
		return new ModelAndView("listUser", model);
	}

	/**
	 * Send a request for friend
	 * @param userBean
	 * @param result
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "invite", method = RequestMethod.GET)
	public ModelAndView invite(
			@ModelAttribute("command") UserBean userBean, BindingResult result, HttpServletRequest request,HttpSession session) {
		Friend friend = new Friend();
		friend.setFriendUserName(request.getParameter("user"));
		friend.setUserName((String)session.getAttribute("username"));
		Map<String, Object> model = new HashMap<String, Object>();
		boolean alreadyExists = userService.checkUserAleadyinvited(friend);
		model.remove("error");
		if(!alreadyExists){
			model.put("msg", "User Invitation sent");
			friend.setStatus("Invited");
			userService.inviteUser(friend);
			session.setAttribute("invited","1");
		} else {
			model.put("error", "User has been already your friend");
			//model.put("msg", "User Invitation sent");
			/*int invited=Integer.parseInt((String)session.getAttribute("invited"));
			if(invited>=3){
				Node node = nodeServie.getNode(request.getParameter("user"));
				Node node1 = nodeServie.getNode((String)request.getSession().getAttribute("username"));
				SybilAttack s = new SybilAttack();
	        	s.setDate(new Date(Calendar.getInstance().getTimeInMillis()));
	        	s.setDestinationNode(node!=null?node.getNodeName():"unknown");
	        	s.setSourceNode(node1!=null?node1.getNodeName():"unknown");
	        	s.setIp(request.getRemoteAddr());
	        	s.setUser((String)request.getSession().getAttribute("username"));
	        	reportService.addReportData(s);
	        	userService.blockUser((String)request.getSession().getAttribute("username"));
	        	return new ModelAndView("login-page");
			} else {
				session.setAttribute("invited",String.valueOf(++invited));
			}*/
		}
		
		//model.put("user", prepareUserBean(userService.getUser(userBean.getId())));
		model.put("users", prepareListofBean(userService.listUser()));
		return new ModelAndView("inviteFriend", model);
	}
	@RequestMapping(value = "onlineUsers", method = RequestMethod.GET)
	public String onlineUsers() {
		return "onlineUsers";
	}
	
	/**
	 * Send a request for friend
	 * @param userBean
	 * @param result
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "invites", method = RequestMethod.GET)
	public ModelAndView invites(
			@ModelAttribute("command") UserBean userBean, BindingResult result, HttpServletRequest request,HttpSession session) {
		Friend friend = new Friend();
		friend.setFriendUserName(request.getParameter("user"));
		friend.setUserName((String)session.getAttribute("username"));
		Map<String, Object> model = new HashMap<String, Object>();
		List<Friend> friendList = userService.getInviteeList((String)session.getAttribute("username"));
		System.out.println(friendList);
		model.put("invites", friendList);
		return new ModelAndView("acceptReject", model);
	}
	
	/**
	 * Send a request for friend
	 * @param userBean
	 * @param result
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "accept", method = RequestMethod.GET)
	public ModelAndView accept(
			@ModelAttribute("command") UserBean userBean, BindingResult result, HttpServletRequest request,HttpSession session) {
		Friend friend = new Friend();
		friend.setFriendUserName(request.getParameter("userName"));
		friend.setUserName((String)session.getAttribute("username"));
		Map<String, Object> model = new HashMap<String, Object>();
		userService.accept(friend);
		List<Friend> friendList = userService.getInviteeList((String)session.getAttribute("username"));
		emailService.sendMailForNode(request.getParameter("userName"), (String)session.getAttribute("username"));
		model.put("invites", friendList);
		return new ModelAndView("acceptReject", model);
	}
	
	/**
	 * Send a request for friend
	 * @param userBean
	 * @param result
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "reject", method = RequestMethod.GET)
	public ModelAndView reject(
			@ModelAttribute("command") UserBean userBean, BindingResult result, LoginForm loginForm, HttpServletRequest request,HttpSession session) {
		Friend friend = new Friend();
		friend.setIdfriend(Integer.parseInt(request.getParameter("userName")));
		friend.setUserName((String)session.getAttribute("username"));
		Map<String, Object> model = new HashMap<String, Object>();
		friend = userService.reject(friend);
		int count = userService.getRejectedRequestCount(friend.getFriendUserName());
		if(count>=3){
			Node node = nodeServie.getNode(request.getParameter("user"));
			Node node1 = nodeServie.getNode((String)request.getSession().getAttribute("username"));
			SybilAttack s = new SybilAttack();
        	s.setDate(new Date(Calendar.getInstance().getTimeInMillis()));
        	s.setDestinationNode(node!=null?node.getNodeName():"unknown");
        	s.setSourceNode(node1!=null?node1.getNodeName():"unknown");
        	s.setIp(request.getRemoteAddr());
        	s.setUser(friend.getFriendUserName());
        	reportService.addReportData(s);
        	userService.blockUser(friend.getFriendUserName());
        	//return new ModelAndView("login-page");			
		}
		List<Friend> friendList = userService.getInviteeList((String)session.getAttribute("username"));
		model.put("invites", friendList);
		return new ModelAndView("acceptReject", model);
	}	
	
	@RequestMapping(value = "friends", method = RequestMethod.GET)
	public ModelAndView friends(
			@ModelAttribute("command") UserBean userBean, BindingResult result, HttpServletRequest request,HttpSession session) {
		Map<String, Object> model = new HashMap<String, Object>();
		List<Friend> friendList = userService.friends((String)session.getAttribute("username"));
		model.put("friends", friendList);
		return new ModelAndView("friends", model);
	}
	private User prepareModel(UserBean userBean) {
		User user = new User();
		user.setName(userBean.getName());
		user.setPassword(userBean.getPassword());
		user.setEmail(userBean.getEmail());
		user.setLoginId(userBean.getLoginId());
		user.setType(userBean.getType());
		user.setId(userBean.getId()!=null?userBean.getId():-1);
		user.setStatus(userBean.getStatus());
		return user;
	}

	private List<UserBean> prepareListofBean(List<User> users) {
		List<UserBean> beans = null;
		if (users != null && !users.isEmpty()) {
			beans = new ArrayList<UserBean>();
			UserBean bean = null;
			for (User user : users) {
				bean = new UserBean();
				bean.setName(user.getName());
				bean.setId(user.getId());
				bean.setPassword(user.getPassword());
				bean.setEmail(user.getEmail());
				bean.setType(user.getType());
				bean.setLoginId(user.getLoginId());
				bean.setStatus(user.getStatus());
				beans.add(bean);
			}
		}
		return beans;
	}

	private UserBean prepareUserBean(User user) {

		UserBean bean = new UserBean();
		bean.setName(user.getName());
		bean.setId(user.getId());
		bean.setPassword(user.getPassword());
		bean.setEmail(user.getEmail());
		bean.setType(user.getType());
		bean.setLoginId(user.getLoginId());
		bean.setStatus(user.getStatus());
		return bean;
	}

}
