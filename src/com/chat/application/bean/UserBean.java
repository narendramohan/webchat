package com.chat.application.bean;

public class UserBean {

	private Integer id;
	private String name;
	private String email;
	private String password;
	private int type;
	private String loginId;
	private int status;

	public Integer getId() {
		
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
		
	}

	public String getName() {
		
		return name;
	}

	public String getPassword() {
		
		return password;
	}

	public String getEmail() {
		return email;
	}

	public void setName(String userName) {
		this.name = userName;
		
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getUserType(){
		if(type==1) {
			return "Admin";
		} else {
			return "User";
		}
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
		
	}

	public String getLoginId() {
		return loginId;
	}
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
		
}
