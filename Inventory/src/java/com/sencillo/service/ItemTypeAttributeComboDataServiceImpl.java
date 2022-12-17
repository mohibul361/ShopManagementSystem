package com.sencillo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sencillo.dao.ItemTypeAttributeComboDataDao;
import com.sencillo.model.ItemTypeAttributeComboData;

@Service
@Transactional
public class ItemTypeAttributeComboDataServiceImpl implements ItemTypeAttributeComboDataService
{
	@Autowired
	private ItemTypeAttributeComboDataDao itemTypeAttributeComboDataDao;

	@Override
	public void save(ItemTypeAttributeComboData itemTypeAttributeComboData)
	{
		this.itemTypeAttributeComboDataDao.save(itemTypeAttributeComboData);
	}

	@Override
	public void edit(ItemTypeAttributeComboData itemTypeAttributeComboData)
	{
		this.itemTypeAttributeComboDataDao.edit(itemTypeAttributeComboData);
	}

	@Override
	public ItemTypeAttributeComboData getItemTypeAttributeComboData(Integer id)
	{
		return this.itemTypeAttributeComboDataDao.getItemTypeAttributeComboData(id);
	}

	@Override
	public List<ItemTypeAttributeComboData> getItemTypeAttributeComboDataList(Integer itemTypeAttributeId)
	{
		return this.itemTypeAttributeComboDataDao.getItemTypeAttributeComboDataList(itemTypeAttributeId);
	}

	@Override
	public void remove(Integer itemTypeAttributeComboDataId)
	{
		this.itemTypeAttributeComboDataDao.remove(itemTypeAttributeComboDataId); 
	}
}
