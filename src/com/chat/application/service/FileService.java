package com.chat.application.service;

import java.util.List;

import com.chat.application.domain.FileSharing;

public interface FileService {
	FileSharing storeFile(FileSharing fileSharing);
	List<Integer> getFile(String fromUser, String toUser);
	List<FileSharing> getFileSend(String fromUser);
	List<FileSharing> getFileRecieved(String toUser);
	boolean isValidUser(int id);
	FileSharing getFile(int id, String fromUser);
	FileSharing getFileToUser(int id, String toUser);
}
