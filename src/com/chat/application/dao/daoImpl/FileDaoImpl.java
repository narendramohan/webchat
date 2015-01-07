package com.chat.application.dao.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.chat.application.dao.FileDao;
import com.chat.application.domain.FileSharing;
import com.chat.application.domain.User;

@Service(value="fileDao")
public class FileDaoImpl implements FileDao {
	@PersistenceContext
	private EntityManager entityManager;
	
	public FileSharing storeFile(FileSharing fileSharing) {
		return entityManager.merge(fileSharing);

	}

	public List<Integer> getFile(String fromUser, String toUser) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isValidUser(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	public FileSharing getFile(int id, String fromUser) {
		Query query = entityManager
				.createQuery("FROM FileSharing u WHERE u.idfilesharing = :id and u.fromUser = :fromUser");
		query.setParameter("id", id);
		query.setParameter("fromUser", fromUser);
		FileSharing fileSharings = null;
		try {
			fileSharings = (FileSharing) query.getSingleResult();
		} catch (Exception e){
			
		}
		return fileSharings;
	}
	
	public FileSharing getFileToUser(int id, String toUser) {
		Query query = entityManager
				.createQuery("FROM FileSharing u WHERE u.idfilesharing = :id and u.toUser = :toUser");
		query.setParameter("id", id);
		query.setParameter("toUser", toUser);
		FileSharing fileSharings = null;
		try {
			fileSharings = (FileSharing) query.getSingleResult();
		} catch (Exception e){
			
		}
		return fileSharings;
	}
	public List<FileSharing> getFileSend(String fromUser) {
		Query query = entityManager
				.createQuery("FROM FileSharing u WHERE u.fromUser = :fromUser");
		query.setParameter("fromUser", fromUser);
		List<FileSharing> fileSharings = query.getResultList();
		return fileSharings;
	}

	public List<FileSharing> getFileRecieved(String toUser) {
		Query query = entityManager
				.createQuery("FROM FileSharing u WHERE u.toUser = :toUser");
		query.setParameter("toUser", toUser);
		List<FileSharing> fileSharings = query.getResultList();
		return fileSharings;
	}

}
