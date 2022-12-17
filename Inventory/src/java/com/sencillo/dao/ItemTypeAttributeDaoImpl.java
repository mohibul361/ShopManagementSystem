package com.sencillo.dao;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sencillo.model.ItemType;
import com.sencillo.model.ItemTypeAttribute;
import com.sencillo.model.ItemTypeAttributeComboData;

@Repository
public class ItemTypeAttributeDaoImpl implements ItemTypeAttributeDao
{

	@Autowired
	private SessionFactory sessionFactory;

	private Session getCurrentSession()
	{
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public void save(ItemTypeAttribute itemTypeAttribute)
	{
		this.getCurrentSession().save(itemTypeAttribute);
	}
	
	
	@Override
	public void edit(ItemTypeAttribute itemTypeAttribute)
	{
		this.getCurrentSession().update(itemTypeAttribute);		
	}

	@Override
	public ItemTypeAttribute getItemTypeAttribute(Integer id)
	{	
		return (ItemTypeAttribute) this.getCurrentSession().get(ItemTypeAttribute.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ItemTypeAttribute> getItemTypeAttributeList(Integer itemTypeId)
	{		
		List<ItemTypeAttribute> list = null;
		if(itemTypeId == null)
		{
			 list = sessionFactory.getCurrentSession().createQuery("from ItemTypeAttribute order by itemType.name").list();
		}
		return list;
	}

	@Override
	public Map<ItemTypeAttribute, List<ItemTypeAttributeComboData>> getItemTypeAttributeData(Integer itemTypeId)
	{
		ItemType itemType = (ItemType) this.getCurrentSession().get(ItemType.class, itemTypeId);
		System.out.println("--"+itemType);
		Map<ItemTypeAttribute, List<ItemTypeAttributeComboData>> attributeWithValue = new LinkedHashMap<ItemTypeAttribute, List<ItemTypeAttributeComboData>>();
		for(ItemTypeAttribute itemTypeAttribute :itemType.getItemTypeAttributes())
		{
			System.out.println(" attr name "+itemTypeAttribute.getName());
			System.out.println(" type "+itemTypeAttribute.getAttributeType());
			System.out.println(" combodata "+itemTypeAttribute.getItemTypeAttributeComboDatas());
			attributeWithValue.put(itemTypeAttribute, itemTypeAttribute.getItemTypeAttributeComboDatas());
			if(itemTypeAttribute.getItemTypeAttributeComboDatas() !=null || !itemTypeAttribute.getItemTypeAttributeComboDatas().isEmpty())
			{
				for(ItemTypeAttributeComboData item: itemTypeAttribute.getItemTypeAttributeComboDatas())
				{
					System.out.println(" ___  "+item.getValue() );
				}
			}
		}
		
		return attributeWithValue;
	}

	@Override
	public void delete(Integer itemTypeAttributeId)
	{
		ItemTypeAttribute itemTypeAttribute = (ItemTypeAttribute) sessionFactory.getCurrentSession().load(ItemTypeAttribute.class,
				itemTypeAttributeId);
		if (itemTypeAttribute != null)
		{
			this.getCurrentSession().delete(itemTypeAttribute);
		}
	}
}
