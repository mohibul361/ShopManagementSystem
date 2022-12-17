package com.sencillo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sencillo.dao.UserDao;
import com.sencillo.model.User;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Override
	public void save(User user)
	{
		this.userDao.save(user);
	}

	@Override
	public void edit(User user)
	{
		this.userDao.edit(user);
	}

	public User getUser(String login) {
		return userDao.getUser(login);
	}

	@Override
	public List<User> getUserList()
	{
		return this.userDao.getUserList();
	}

	@Override
	public User getUser(Integer id)
	{
		return this.userDao.getUser(id);
	}

	@Override
	public void changePassword(Integer userId, String hashedPassword, String salt)
	{
		this.userDao.changePassword(userId, hashedPassword, salt);
	}

	@Override
	public List<User> getApproverList()
	{
		return this.userDao.getApproverList();
	}
	
	@Override
	public boolean checkLicense()
	{
		return this.userDao.checkLicense();
	}
	
}