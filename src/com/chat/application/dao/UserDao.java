package com.chat.application.dao;

import java.util.List;

import com.chat.application.domain.User;
import com.chat.application.form.LoginForm;

public interface UserDao {
	public User loginUser(LoginForm loginForm);
	public User getEmail(LoginForm loginForm);
	public User updatUser(LoginForm loginForm);
	public void addUser(User user);
	public void editUser(User user);
	public List<User> listUser();
	public void deleteUser(User user);
	public User getUser(Integer id);
	public User getUser(String userName);
	public boolean isUserExists(String userName);
	public void activateUser(User user);
	public boolean isEmailExists(String email);
	public void blockUser(String user);
}