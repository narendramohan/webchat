package com.chat.application.domain;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SybilAttack")
public class SybilAttack {
	@Id
	@Column(name = "User")
	private String user;
	
	@Column(name = "ip")
	private String ip;
	
	@Column(name = "date")
	private Date date;
	
	@Column(name = "sourceNode")
	private String sourceNode;
	
	@Column(name = "destinationNode")
	private String destinationNode;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getSourceNode() {
		return sourceNode;
	}

	public void setSourceNode(String sourceNode) {
		this.sourceNode = sourceNode;
	}

	public String getDestinationNode() {
		return destinationNode;
	}

	public void setDestinationNode(String destinationNode) {
		this.destinationNode = destinationNode;
	}
	
	

}
