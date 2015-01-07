package com.chat.application.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Friend")
public class Friend {
	@Id
	@GeneratedValue
	@Column(name = "idfriend")
	private int idfriend;

	@Column(name = "friendUserName")
	private String friendUserName;

	@Column(name = "userName")
	private String userName;

	@Column(name = "status")
	private String status;

	public int getIdfriend() {
		return idfriend;
	}

	public void setIdfriend(int idfriend) {
		this.idfriend = idfriend;
	}

	public String getFriendUserName() {
		return friendUserName;
	}

	public void setFriendUserName(String friendUserName) {
		this.friendUserName = friendUserName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
