package com.sencillo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sencillo.model.Role;

@Repository
public class RoleDaoImpl implements RoleDao
{

	@Autowired
	private SessionFactory sessionFactory;

	private Session getCurrentSession()
	{
		return sessionFactory.getCurrentSession();
	}

	public Role getRole(int id)
	{
		Role role = (Role) getCurrentSession().load(Role.class, id);
		return role;
	}

	@Override
	public void Save(Role role)
	{
		this.getCurrentSession().save(role);
	}

	@Override
	public void Edit(Role role)
	{
		this.getCurrentSession().update(role);
	}

	@Override
	public List<Role> getRoleList()
	{
		@SuppressWarnings("unchecked")
		List<Role> list = sessionFactory.getCurrentSession().createQuery("from Role").list();
		return list;
	}

}