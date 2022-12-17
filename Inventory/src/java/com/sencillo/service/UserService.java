package com.sencillo.service;

import java.util.List;

import com.sencillo.model.User;

public interface UserService
{
	public void save(User user);

	public void edit(User user);

	public User getUser(String login);
	
	public User getUser(Integer id);
	
	public List<User> getUserList();
	
	public void changePassword(Integer userId, String hashedPassword, String salt);
	
	public List<User> getApproverList();
	
	public boolean checkLicense();
}
