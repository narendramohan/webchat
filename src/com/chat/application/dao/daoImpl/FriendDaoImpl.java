package com.chat.application.dao.daoImpl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.chat.application.dao.FriendDao;
import com.chat.application.domain.Friend;

@Service("friendDao")
public class FriendDaoImpl implements FriendDao {
	@PersistenceContext
	private EntityManager entityManager;
	public void inviteUser(Friend friend) {
		friend.setStatus("invitee");
		entityManager.merge(friend);
		Friend friend1 = new Friend();
		friend1.setFriendUserName(friend.getUserName());
		friend1.setUserName(friend.getFriendUserName());
		friend1.setStatus("Invited");
		entityManager.merge(friend1);
	}

	public boolean checkUserAleadyinvited(Friend friend) {
		Query query = entityManager
				.createQuery("FROM Friend u WHERE u.friendUserName = :fuser and u.userName = :userName and status='accepted'");
		query.setParameter("fuser", friend.getFriendUserName());
		query.setParameter("userName", friend.getUserName());
		List list = query.getResultList();
		if(list.size()>0)
			return true;
		else
			return false;

	}
	
	public List<Friend> getInviteeList(String userName){
		Query query = entityManager
				.createQuery("FROM Friend u WHERE u.userName = :userName and u.status='Invited'");
		query.setParameter("userName", userName);
		List<Friend> list = query.getResultList();
		return list;
	}
	
	public void accept(Friend friend){
		Query query = entityManager
				.createQuery("FROM Friend u WHERE u.friendUserName = :fuser and u.userName = :userName and u.status='Invited'");
		query.setParameter("fuser", friend.getFriendUserName());
		query.setParameter("userName", friend.getUserName());
		List<Friend> list = query.getResultList();
		if(list!=null) {
			for(Friend f:list){
				f.setStatus("accepted");
				entityManager.merge(f);
			}
		}
		
		query = entityManager
				.createQuery("FROM Friend u WHERE u.friendUserName = :fuser and u.userName = :userName and u.status='Invitee'");
		query.setParameter("fuser", friend.getUserName());
		query.setParameter("userName", friend.getFriendUserName());
		list = query.getResultList();
		if(list!=null) {
			for(Friend f:list){
				f.setStatus("accepted");
				entityManager.merge(f);
			}
		}
	}
	
	public Friend reject(Friend friend){
		Query query = entityManager
				.createQuery("FROM Friend u WHERE u.idfriend = :fuser and u.status='Invited'");
		query.setParameter("fuser", friend.getIdfriend());
		//query.setParameter("userName", friend.getUserName());
		List<Friend> list = query.getResultList();
		if(list!=null)
			for(Friend f:list){
				f.setStatus("rejected");
				friend = entityManager.merge(f);
			}
		return friend;
	}

	public List<Friend> getFriends(String userName) {
		Query query = entityManager
				.createQuery("FROM Friend u WHERE u.userName = :userName and u.status='accepted'");
		query.setParameter("userName", userName);
		List<Friend> list = query.getResultList();
		if(list==null) list = new ArrayList<Friend>();
		return list;
	}	
	
	public int getRejectedRequestCount(String userName) {
		Query query = entityManager
				.createQuery("FROM Friend u WHERE u.friendUserName = :userName and u.status='rejected'");
		query.setParameter("userName", userName);
		List<Friend> list = query.getResultList();
		if(list==null) list = new ArrayList<Friend>();
		return list.size();
	}

}
