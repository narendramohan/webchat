package com.chat.application.dao.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.chat.application.dao.UserDao;
import com.chat.application.domain.User;
import com.chat.application.form.LoginForm;

@Service("userDao")
public class UserDaoImpl implements UserDao {

	@PersistenceContext
	private EntityManager entityManager;

	public User loginUser(LoginForm loginForm) {
		Query query = entityManager
				.createQuery("FROM User u WHERE u.loginId = :loginId and u.password = :password");
		query.setParameter("loginId", loginForm.getUserId());
		query.setParameter("password", loginForm.getPassword());
		List<User> users = query.getResultList();
		for (User user : users) {
			//user.setType(1);
			//entityManager.merge(user);
			return user;
		}
		return null;
	}
	
	public User getEmail(LoginForm loginForm) {
		Query query = entityManager
				.createQuery("FROM User u WHERE u.email = :emailid");
		query.setParameter("emailid", loginForm.getEmailId());
		List<User> users = query.getResultList();
		for (User user : users) {
			entityManager.merge(user);
			return user;
		}
		return null;
	}
	protected static Logger logger = Logger.getLogger("controller");
	@Transactional(propagation = Propagation.REQUIRED)
	public User updatUser(LoginForm loginForm) {
		User user = getEmail(loginForm);
		if(user!=null){ 
			logger.debug("User name:" + user.getName());
			user.setPassword(loginForm.getPassword());
			entityManager.merge(user);
			return user;
		} else {
			return null;
		}
	}


	public void addUser(User user) {
		entityManager.merge(user);		
	}


	public List<User> listUser() {	
		return entityManager.createQuery("FROM User").getResultList();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteUser(User user) {
		User user1 = entityManager.merge(user);
		entityManager.remove(user1);
	}


	public User getUser(Integer id) {
		Query query = entityManager
				.createQuery("FROM User u WHERE u.id = :id");
		query.setParameter("id", id);
		User user = (User) query.getSingleResult();
		return user;
	}	
	
	public User getUser(String userName){
		Query query = entityManager
				.createQuery("FROM User u WHERE u.loginId = :userName");
		query.setParameter("userName", userName);
		User user = (User) query.getSingleResult();
		return user;
	}
	
	public boolean isUserExists(String userName){
		Query query = entityManager
				.createQuery("FROM User u WHERE u.loginId = :userName");
		query.setParameter("userName", userName);
		List list = query.getResultList();
		if(list.size()>0)
			return true;
		else
			return false;
	}
	public boolean isEmailExists(String email){
		Query query = entityManager
				.createQuery("FROM User u WHERE u.email = :email");
		query.setParameter("email", email);
		List list = query.getResultList();
		if(list.size()>0)
			return true;
		else
			return false;
	}
	public void editUser(User user) {
		// TODO Auto-generated method stub
		
	}

	public void activateUser(User user) {
		user = getUser(user.getId());
		user.setStatus(0);
		entityManager.merge(user);
		
	}

	public void blockUser(String userName) {
		User user = getUser(userName);
		user.setStatus(1);
		entityManager.merge(user);
		
	}	
}