package com.sencillo.dao;

import java.util.List;

import com.sencillo.model.ItemTypeAttributeComboData;

public interface ItemTypeAttributeComboDataDao
{
	public void save(ItemTypeAttributeComboData itemTypeAttributeComboData);

	public void edit(ItemTypeAttributeComboData itemTypeAttributeComboData);
	
	public void remove(Integer itemTypeAttributeComboDataId);

	public ItemTypeAttributeComboData getItemTypeAttributeComboData(Integer id);

	public List<ItemTypeAttributeComboData> getItemTypeAttributeComboDataList(Integer itemTypeAttributeId);
}
