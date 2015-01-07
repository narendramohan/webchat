package com.chat.application.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.chat.application.common.EmailUtil;
import com.chat.application.common.RandomPasswordGenerator;
import com.chat.application.dao.UserDao;
import com.chat.application.domain.Friend;
import com.chat.application.domain.Node;
import com.chat.application.domain.User;
import com.chat.application.form.LoginForm;
import com.chat.application.service.EmailService;
import com.chat.application.service.NodeServie;
import com.chat.application.service.PasswordEncryptionService;
import com.chat.application.service.UserService;

@Service("emailService")
public class EmailServiceImpl implements EmailService {

	@Autowired
	private MailSender mailSender;
	@Autowired
	private UserService userService;
	@Autowired
	private NodeServie nodeServie;
	 
	@Autowired
	private PasswordEncryptionService passwordEncryptionService;
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}
 
	public void sendMail(String from, String to, String subject, String msg) {
 
		SimpleMailMessage message = new SimpleMailMessage();
 
		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(msg);
		mailSender.send(message);	
	}

	@Autowired
	private UserDao userDao;

	public void sendForgotPasswordMail(LoginForm loginForm) {
		String newPassword = null;
		int noOfCAPSAlpha = 1;
        int noOfDigits = 1;
        int noOfSplChars = 1;
        int minLen = 8;
        int maxLen = 12;
        newPassword = RandomPasswordGenerator.generatePswd(minLen, maxLen, noOfCAPSAlpha, noOfDigits, noOfSplChars);
        loginForm.setPassword(newPassword);
        userDao.updatUser(loginForm);
        org.springframework.mail.javamail.JavaMailSenderImpl ms =(org.springframework.mail.javamail.JavaMailSenderImpl)mailSender;
        //ms.setPassword(passwordEncryptionService.decrypt(ms.getPassword()));
		sendMail("Admin", loginForm.getEmailId(), "Password changed", String.format("Your new password for webchat is %s. Please change your passwor as soon as you login.", newPassword));
		
	}

	public void sendMailForNode(String fuserName, String tUser) {
        User user = userService.getUser(fuserName);
        User user1 = userService.getUser(tUser);
        Node node = nodeServie.getNode(fuserName);
        Node node1 = nodeServie.getNode(tUser);
        
        if(user!=null){
	        org.springframework.mail.javamail.JavaMailSenderImpl ms =(org.springframework.mail.javamail.JavaMailSenderImpl)mailSender;
	        try{
	        	sendMail("Admin", user.getEmail(), "Node information", String.format("Node: %s is used for the user: %s. ", node1.getNodeName(), user1.getLoginId()));
	        	sendMail("Admin", user1.getEmail(), "Node information", String.format("Node: %s is used for the user: %s. ", node.getNodeName(), user.getLoginId()));
	        } catch (Exception e){
	        	e.printStackTrace();
	        }
        }
		
	}

	public void sendMailForNodeAssigned(String userName) {
		 User user1 = userService.getUser(userName);
	     Node node1 = nodeServie.getNode(userName);
	     if(user1!=null){
		        org.springframework.mail.javamail.JavaMailSenderImpl ms =(org.springframework.mail.javamail.JavaMailSenderImpl)mailSender;
		        try{
		        	sendMail("Admin", user1.getEmail(), "Node information", String.format("You have been assigned to the node: %s, ", node1.getNodeName()));
		        } catch (Exception e){
		        	e.printStackTrace();
		        }
	        }
	     
	     List<Friend> friends = userService.friends(userName);
	     if(friends!=null)
	    	 for(Friend f:friends){
	    	     if(user1!=null){
	 		        org.springframework.mail.javamail.JavaMailSenderImpl ms =(org.springframework.mail.javamail.JavaMailSenderImpl)mailSender;
	 		        try{
	 		        	sendMail("Admin", userService.getUser(f.getFriendUserName()).getEmail(), "Node information", String.format("Node: %s is now assigned to the user: %s", node1.getNodeName(), user1.getLoginId()));
	 		        } catch (Exception e){
	 		        	e.printStackTrace();
	 		        }
	 	        }	    		 
	    	 }
	}

}
