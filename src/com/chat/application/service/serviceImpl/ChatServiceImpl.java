package com.chat.application.service.serviceImpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chat.application.dao.ChatDao;
import com.chat.application.dao.UserDao;
import com.chat.application.domain.User;
import com.chat.application.form.LoginForm;
import com.chat.application.service.ChatService;

@Service("chatService")
public class ChatServiceImpl implements ChatService {

	@Resource(name = "userDao")
	private UserDao userDao;
	
	@Resource(name = "chatDao")
	private ChatDao chatDao;

	public User loginUser(LoginForm loginForm) {
		return userDao.loginUser(loginForm);
	}
	public User getEmail(LoginForm loginForm){
		return userDao.getEmail(loginForm);
	}
	public User updatUser(LoginForm loginForm){
		return userDao.updatUser(loginForm);
	}
	public String getChatResult(String userName, String chatUser) {
		// TODO Auto-generated method stub
		return chatDao.getChatResult(userName, chatUser);
	}
}