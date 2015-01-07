package com.sybildefender.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

//@Entity
//@Table(name="user")
public class User {
	
	private String userName;
	private String password;
	private int id;
	private String email;
	//@Id
	//@GeneratedValue
	//@Column(name="id")
	public int getId(){
		return id;
	}
	
	//@Column(name="user_name")
	public String getUserName(){
		return userName;
	}
	
	//@Column(name="user_password")
	public String getPassword()
	{
		return password;
	}
	
	//@Column(name="email")
	public String getEmail(){
		return email;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
}
