package com.sencillo.service;

import java.util.List;

import com.sencillo.model.ItemType;

public interface ItemTypeService
{
	public void save(ItemType itemType);
	
	public void edit(ItemType itemType);

	public List<ItemType> getItemTypeList();

	public ItemType getItemType(Integer id);
}
