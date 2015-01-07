package com.chat.application.bruteforce;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;

/**
Listener for successfull logins. Used for reseting number on unsuccessfull logins for specific account
*/
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent>{
	protected static Logger logger = Logger.getLogger("controller");
   //LoginAttemptCacheService loginAttemptCacheService


   public void onApplicationEvent(AuthenticationSuccessEvent e) {
       //loginAttemptCacheService.loginSuccess(e.authentication.name)
	   logger.debug("Failed login attempt");
   }
}