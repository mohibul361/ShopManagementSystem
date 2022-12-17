package com.sencillo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sencillo.model.ProblemType;

@Repository
public class ProblemTypeDaoImpl implements ProblemTypeDao
{

	@Autowired
	private SessionFactory sessionFactory;

	private Session getCurrentSession()
	{
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void save(ProblemType problemType)
	{
		this.getCurrentSession().save(problemType);
		
	}

	@Override
	public void edit(ProblemType problemType)
	{
		this.getCurrentSession().update(problemType);
		
	}

	@Override
	public ProblemType getProblemType(Integer id)
	{
		return (ProblemType) this.getCurrentSession().get(ProblemType.class, id);
	}

	@Override
	public List<ProblemType> getProblemTypeList()
	{
		@SuppressWarnings("unchecked")
		List<ProblemType> list = sessionFactory.getCurrentSession().createQuery("from ProblemType").list();
		return list;
	}

}
