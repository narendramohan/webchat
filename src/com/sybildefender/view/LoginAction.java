package com.sybildefender.view;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.sybildefender.controller.LoginManager;

public class LoginAction extends ActionSupport implements SessionAware  {
	public static ServerSocket ssoc1;

	public static Socket sousoc1, ss1;
	ObjectOutputStream oso;	
	ObjectInputStream osi;	
	
	public String execute() throws Exception {
		return SUCCESS;
	}
	
	public String doLogin(){
		LoginManager login = new LoginManager();
		boolean isValid = login.isValidUser(getUsername(), getPassword());	

		if(!isValid){
			addActionError("Invalid user name or password! Please try again!");
			return ERROR;
		}else{			
			session.put("username", getUsername());
			ServletActionContext.getContext().getSession().put("softuser", getUsername());
			return SUCCESS;
		}
	}


	// ---- Username property ----

	/**
	 * <p>Field to store User username.</p>
	 * <p/>
	 */
	private String username = null;


	/**
	 * <p>Provide User username.</p>
	 *
	 * @return Returns the User username.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * <p>Store new User username</p>
	 *
	 * @param value The username to set.
	 */
	public void setUsername(String value) {
		username = value;
	}

	// ---- Username property ----

	/**
	 * <p>Field to store User password.</p>
	 * <p/>
	 */
	private String password = null;


	/**
	 * <p>Provide User password.</p>
	 *
	 * @return Returns the User password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * <p>Store new User password</p>
	 *
	 * @param value The password to set.
	 */
	public void setPassword(String value) {
		password = value;
	}
	
	private Map<String, Object> session;

	public void setSession(Map<String, Object> session) {
		this.session =session;
		
	}

}
