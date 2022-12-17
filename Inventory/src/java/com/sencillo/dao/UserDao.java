package com.sencillo.dao;

import java.util.List;

import com.sencillo.model.User;

public interface UserDao
{
	public void save(User user);

	public void edit(User user);

	public User getUser(String login);
	
	public List<User> getUserList();
	
	public User getUser(Integer id);
	
	public void changePassword(Integer userId, String hashedPassword, String salt);
	
	public List<User> getApproverList();
	
	public boolean checkLicense();
}
