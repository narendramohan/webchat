package com.chat.application.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chat.application.bean.ActiveUserBean;
import com.chat.application.domain.User;
import com.chat.application.service.ChatService;

@Controller
public class ChatController {

	@Resource(name = "chatService")
	private ChatService service;
	protected static Logger logger = Logger.getLogger("controller");
	List<User> users = new ArrayList<User>();

	/*@RequestMapping(value = "/login-page")
	public String loadLoginPage(Map model, LoginForm loginForm) {
		model.put("loginForm", loginForm);
		return "login-page";
	}*/

	@RequestMapping(value = "/chat-page")
	public String loadChatPage(HttpServletRequest request, HttpSession session) {
		String userName = request.getParameter("user");
		request.setAttribute("chatUser", userName);
		request.setAttribute("userName", (String)session.getAttribute("username"));
		return "/chat-page";
	}
	
	//@RequestMapping(value = "/loadChat", method=RequestMethod.POST)
	public @ResponseBody void loadChat(HttpServletResponse response, HttpServletRequest request, HttpSession session) throws IOException {
		String chatUser = request.getParameter("chatUser");
		String userName = (String)session.getAttribute("ususernameername");
		request.setAttribute("chatUser", userName);
		request.setAttribute("userName", (String)session.getAttribute("ususernameername"));
		String xml = service.getChatResult(userName, chatUser);
		response.setCharacterEncoding("UTF-8");  
        response.setContentType("text/html");  
        response.getWriter().write(xml);  
		//return "/chat-page";
	}

	@RequestMapping(value = "/logout")
	public String logout(HttpSession session) {
		String userName = (String) session.getAttribute("userName");
		ServletContext context = session.getServletContext();
		List<ActiveUserBean> users = (List<ActiveUserBean>) context.getAttribute("onlineUsers");
		//context.removeAttribute("onlineUsers");
		ActiveUserBean usr = null;
		if(users!=null)
		for(ActiveUserBean user:users)
		{
			if(userName!= null && userName.equals(user.getName())){
				usr = user;
				break;
			}
		}
		if(usr!=null)
			users.remove(usr);
		
		session.removeAttribute("userName");
		session.removeAttribute("user");
		
		session.invalidate();
		return "/index";
	}

}