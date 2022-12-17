package com.sencillo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sencillo.model.ItemTypeAttributeComboData;

@Repository
public class ItemTypeAttributeComboDataDaoImpl implements ItemTypeAttributeComboDataDao
{

	@Autowired
	private SessionFactory sessionFactory;

	private Session getCurrentSession()
	{
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void save(ItemTypeAttributeComboData itemTypeAttributeComboData)
	{
		this.getCurrentSession().save(itemTypeAttributeComboData);
	}

	@Override
	public void edit(ItemTypeAttributeComboData itemTypeAttributeComboData)
	{
		this.getCurrentSession().update(itemTypeAttributeComboData);
	}

	@Override
	public ItemTypeAttributeComboData getItemTypeAttributeComboData(Integer id)
	{
		return (ItemTypeAttributeComboData) this.getCurrentSession().get(ItemTypeAttributeComboData.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ItemTypeAttributeComboData> getItemTypeAttributeComboDataList(Integer itemTypeAttributeId)
	{
		List<ItemTypeAttributeComboData> list = null;
		if (itemTypeAttributeId != null)
		{
			list = sessionFactory.getCurrentSession().createQuery("from ItemTypeAttributeComboData where itemTypeAttribute.id = :itemTypeAttributeId")
					.setParameter("itemTypeAttributeId", itemTypeAttributeId).list();
		}
		return list;
	}

	@Override
	public void remove(Integer itemTypeAttributeComboDataId)
	{
		ItemTypeAttributeComboData itemTypeAttributeComboData = (ItemTypeAttributeComboData) sessionFactory.getCurrentSession().load(ItemTypeAttributeComboData.class,
				itemTypeAttributeComboDataId);
		if (itemTypeAttributeComboData != null)
		{
			this.getCurrentSession().delete(itemTypeAttributeComboData);
		}

	}

}
