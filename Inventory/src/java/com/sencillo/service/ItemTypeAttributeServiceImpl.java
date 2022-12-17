package com.sencillo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sencillo.dao.ItemTypeAttributeDao;
import com.sencillo.model.ItemTypeAttribute;
import com.sencillo.model.ItemTypeAttributeComboData;

@Service
@Transactional
public class ItemTypeAttributeServiceImpl implements ItemTypeAttributeService
{
	@Autowired
	private ItemTypeAttributeDao itemTypeAttributeDao;

	@Override
	public void save(ItemTypeAttribute itemTypeAttribute)
	{
		this.itemTypeAttributeDao.save(itemTypeAttribute);
	}

	@Override
	public void edit(ItemTypeAttribute itemTypeAttribute)
	{
		this.itemTypeAttributeDao.edit(itemTypeAttribute);
	}

	@Override
	public ItemTypeAttribute getItemTypeAttribute(Integer id)
	{
		return this.itemTypeAttributeDao.getItemTypeAttribute(id);
	}

	@Override
	public List<ItemTypeAttribute> getItemTypeAttributeList(Integer itemTypeId)
	{
		return this.itemTypeAttributeDao.getItemTypeAttributeList(itemTypeId);
	}

	@Override
	public Map<ItemTypeAttribute, List<ItemTypeAttributeComboData>> getItemTypeAttributeData(Integer itemTypeId)
	{
		return this.itemTypeAttributeDao.getItemTypeAttributeData(itemTypeId);
	}

	@Override
	public void delete(Integer itemTypeAttributeId)
	{
		this.itemTypeAttributeDao.delete(itemTypeAttributeId);
		
	}

}
