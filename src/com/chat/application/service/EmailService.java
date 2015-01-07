package com.chat.application.service;

import com.chat.application.form.LoginForm;

public interface EmailService {
	public void sendForgotPasswordMail(LoginForm loginForm);
	public void sendMailForNode(String fUserName, String toUser);
	public void sendMailForNodeAssigned(String userName);

}
