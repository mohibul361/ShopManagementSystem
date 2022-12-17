package com.sencillo.dao;

import java.util.List;

import com.sencillo.model.ItemType;

public interface ItemTypeDao
{
	public void save(ItemType itemType);
	
	public void edit(ItemType itemType);

	public List<ItemType> getItemTypeList();

	public ItemType getItemType(int id);
	
}
