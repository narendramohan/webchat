package com.chat.application.bruteforce;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;

/**
Registers all failed attempts to login. Main purpose to count attempts for particular account ant block user

*/
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
	protected static Logger logger = Logger.getLogger("controller");
   // LoginAttemptCacheService loginAttemptCacheService


	public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent e) {
       // loginAttemptCacheService.failLogin(e.authentication.name)
    	logger.debug("Successful login");
    }



}