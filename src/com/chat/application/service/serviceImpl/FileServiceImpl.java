package com.chat.application.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.chat.application.dao.FileDao;
import com.chat.application.domain.FileSharing;
import com.chat.application.service.FileService;
@Service(value="fileServie")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class FileServiceImpl implements FileService {

	@Autowired
	private FileDao fileDao;
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public FileSharing storeFile(FileSharing fileSharing) {
		return fileDao.storeFile(fileSharing);

	}

	public List<Integer> getFile(String fromUser, String toUser) {
		return fileDao.getFile(fromUser, toUser);
	}

	public boolean isValidUser(int id) {
		return fileDao.isValidUser(id);
	}

	public FileSharing getFile(int id, String fromUser) {
		return fileDao.getFile(id, fromUser);
	}

	public List<FileSharing> getFileSend(String fromUser) {	
		return fileDao.getFileSend(fromUser);
	}

	public List<FileSharing> getFileRecieved(String toUser) {
		return fileDao.getFileRecieved(toUser);
	}

	public FileSharing getFileToUser(int id, String toUser) {
		return fileDao.getFileToUser(id, toUser);
	}

}
