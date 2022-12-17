package com.sencillo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sencillo.model.Region;

@Repository
public class RegionDaoImpl implements RegionDao
{

	@Autowired
	private SessionFactory sessionFactory;

	private Session getCurrentSession()
	{
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void save(Region region)
	{
		this.getCurrentSession().save(region);
		
	}

	@Override
	public void edit(Region region)
	{
		this.getCurrentSession().update(region);
		
	}

	@Override
	public Region getRegion(Integer id)
	{
		return (Region) this.getCurrentSession().get(Region.class, id);
	}

	@Override
	public List<Region> getRegionList()
	{
		@SuppressWarnings("unchecked")
		List<Region> list = sessionFactory.getCurrentSession().createQuery("from Region").list();
		return list;
	}

}
