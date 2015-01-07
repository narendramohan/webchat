package com.chat.application.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="filesharing")
public class FileSharing {
	@Id
	@GeneratedValue
	@Column(name="idfilesharing")
	private int idfilesharing;
	
	@Column(name="filename")
	private String filename;
	
	@Column(name="fromUser")
	private String fromUser;
	
	@Column(name="toUser")
	private String toUser;
	
	@Column(name="contenttype")
	private String contenttype;

	public int getIdfilesharing() {
		return idfilesharing;
	}

	public void setIdfilesharing(int idfilesharing) {
		this.idfilesharing = idfilesharing;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFromUser() {
		return fromUser;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	public String getToUser() {
		return toUser;
	}

	public void setToUser(String toUser) {
		this.toUser = toUser;
	}

	public String getContenttype() {
		return contenttype;
	}

	public void setContenttype(String contenttype) {
		this.contenttype = contenttype;
	}
	
	
	

}
