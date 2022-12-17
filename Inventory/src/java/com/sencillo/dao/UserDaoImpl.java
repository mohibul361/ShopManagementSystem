package com.sencillo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sencillo.model.Recipient;
import com.sencillo.model.Slip;
import com.sencillo.model.User;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;

	private Session openSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void save(User user)
	{
		this.sessionFactory.getCurrentSession().save(user);		
	}

	@Override
	public void edit(User user)
	{
		this.sessionFactory.getCurrentSession().update(user);
	}
	
	@SuppressWarnings("unchecked")
	public User getUser(String login) {	
		
		System.out.println("In login dao impl");
		List<User> list = openSession().createQuery("from User u where u.userName = :login")
						.setParameter("login", login)
						.list();
		
		return list.size() > 0 ?(User)list.get(0): null;
	}

	@Override
	public List<User> getUserList()
	{
		@SuppressWarnings("unchecked")
		List<User> list = sessionFactory.getCurrentSession().createQuery("from User").list();
		return list;
	}

	@Override
	public User getUser(Integer id)
	{
		return (User) this.openSession().get(User.class, id);
	}
	
	@Override
	public void changePassword(Integer userId, String hashedPassword, String salt)
	{
		User dbUser = getUser(userId);
		dbUser.setSalt(salt);
		dbUser.setPassword(hashedPassword);
	}

	@Override
	public List<User> getApproverList()
	{
		@SuppressWarnings("unchecked")
		List<User> list = sessionFactory.getCurrentSession().createQuery("from User").list();
		return list;
	}
	public boolean checkLicense()
	{
		List<Slip> list = sessionFactory.getCurrentSession().createQuery("from Slip").list();
		if(list.size()>=100)
		{
			return false;
		}
		else
		{
			return true;
		}
		
	}
}