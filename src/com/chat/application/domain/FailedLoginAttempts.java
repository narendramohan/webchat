package com.chat.application.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

//@Entity
//@Table(name = "failedloginattempts")
public class FailedLoginAttempts {
	//@Column(name = "user_name")
	private String userName;
	
	//@Column(name="times_failed")
	private int failedAttempts;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getFailedAttempts() {
		return failedAttempts;
	}

	public void setFailedAttempts(int failedAttempts) {
		this.failedAttempts = failedAttempts;
	}
	

}
