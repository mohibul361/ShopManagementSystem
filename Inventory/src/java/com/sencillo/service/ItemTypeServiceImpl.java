package com.sencillo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sencillo.dao.ItemTypeDao;
import com.sencillo.model.ItemType;

@Service
@Transactional
public class ItemTypeServiceImpl implements ItemTypeService
{

	@Autowired
	private ItemTypeDao itemTypeDao;

	@Override
	public void save(ItemType itemType)
	{
		itemTypeDao.save(itemType);
	}
	
	@Override
	public void edit(ItemType itemType)
	{
		itemTypeDao.edit(itemType);
	}

	@Override
	public List<ItemType> getItemTypeList()
	{
		return itemTypeDao.getItemTypeList();
	}

	@Override
	public ItemType getItemType(Integer id)
	{
		return itemTypeDao.getItemType(id);
	}

		
}
