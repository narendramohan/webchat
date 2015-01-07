package com.chat.application.dao.daoImpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

import com.chat.application.dao.ChatDao;
@Service("chatDao")
public class ChatDaoImpl implements ChatDao {
	@PersistenceContext
	private EntityManager entityManager;
	public String getChatResult(String userName, String chatUser) {
		//entityManager
		return null;
	}

}
