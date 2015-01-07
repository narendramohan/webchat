package com.chat.application.dao;

import java.util.List;

import com.chat.application.domain.FileSharing;

public interface FileDao {
	FileSharing storeFile(FileSharing fileSharing);
	List<Integer> getFile(String fromUser, String toUser);
	boolean isValidUser(int id);
	FileSharing getFile(int id, String fromUser);
	List<FileSharing> getFileSend(String fromUser);
	List<FileSharing> getFileRecieved(String toUser);
	FileSharing getFileToUser(int id, String toUser);

}
