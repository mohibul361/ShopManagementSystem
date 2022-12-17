package com.sencillo.service;

import java.util.List;
import java.util.Map;

import com.sencillo.model.ItemTypeAttribute;
import com.sencillo.model.ItemTypeAttributeComboData;

public interface ItemTypeAttributeService
{
	public void save(ItemTypeAttribute itemTypeAttribute);

	public void edit(ItemTypeAttribute itemTypeAttribute);

	public ItemTypeAttribute getItemTypeAttribute(Integer id);
	
	public List<ItemTypeAttribute> getItemTypeAttributeList(Integer itemTypeId);
	
	public Map<ItemTypeAttribute, List<ItemTypeAttributeComboData>> getItemTypeAttributeData(Integer itemTypeId); 
	
	public void delete(Integer itemTypeAttributeId);
}
