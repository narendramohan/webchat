package com.chat.application.service;

import com.chat.application.domain.User;
import com.chat.application.form.LoginForm;

public interface ChatService {
	public User loginUser(LoginForm loginForm);
	public User getEmail(LoginForm loginForm);
	public User updatUser(LoginForm loginForm);
	public String getChatResult(String userName, String chatUser);
}