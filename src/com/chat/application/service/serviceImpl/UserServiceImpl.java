package com.chat.application.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.chat.application.dao.UserDao;
import com.chat.application.dao.FriendDao;
import com.chat.application.domain.Friend;
import com.chat.application.domain.User;
import com.chat.application.service.UserService;
@Service("userService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private FriendDao friendDao;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addUser(User user) {
		userDao.addUser(user);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void editUser(User user) {
		userDao.editUser(user);
	}

	public List<User> listUser() {
		
		return userDao.listUser();
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void deleteUser(User prepareModel) {
		userDao.deleteUser(prepareModel);

	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public User getUser(Integer id) {
		
		return userDao.getUser(id);
	}
	
	public User getUser(String userName){
		return userDao.getUser(userName);
	}
	
	public boolean isUserExists(String userName){
		return userDao.isUserExists(userName);
	}
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void activateUser(User user) {
		userDao.activateUser(user);
		
	}
	
	public boolean checkUserAleadyinvited(Friend friend) {
		return friendDao.checkUserAleadyinvited(friend);
		
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void inviteUser(Friend friend) {
		friendDao.inviteUser(friend);
		
		
	}

	public List<Friend> getInviteeList(String userName){
		return friendDao.getInviteeList(userName);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void accept(Friend friend){
		friendDao.accept(friend);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Friend reject(Friend friend){
		return friendDao.reject(friend);
	}
	
	public List<Friend> friends(String userName){
		return friendDao.getFriends(userName);
	}
	public boolean isEmailExists(String email){
		return userDao.isEmailExists(email);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void blockUser(String user) {
		userDao.blockUser(user);
		
	}
	
	
	public int getRejectedRequestCount(String userName){
		return friendDao.getRejectedRequestCount(userName);
	}
}
