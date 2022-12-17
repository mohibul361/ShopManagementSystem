package com.sencillo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sencillo.model.ItemType;

@Repository
public class ItemTypeDaoImpl implements ItemTypeDao
{

	@Autowired
	private SessionFactory sessionFactory;

	private Session getCurrentSession()
	{
		return sessionFactory.getCurrentSession();
	}

	public void save(ItemType itemType)
	{
		this.getCurrentSession().save(itemType);
	}
	
	public void edit(ItemType itemType)
	{
		this.getCurrentSession().update(itemType);
	}

	@Override
	public List<ItemType> getItemTypeList()
	{
		@SuppressWarnings("unchecked")
		List<ItemType> list = sessionFactory.getCurrentSession().createQuery("from ItemType order by name").list();
		return list;
	}

	@Override
	public ItemType getItemType(int id)
	{	
		return (ItemType) sessionFactory.getCurrentSession().get(ItemType.class, id);
	}
}
