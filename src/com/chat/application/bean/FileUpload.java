package com.chat.application.bean;

import org.springframework.web.multipart.MultipartFile;

public class FileUpload {
	
	MultipartFile file;
	String userName;
	String nodeName;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	
	
}
