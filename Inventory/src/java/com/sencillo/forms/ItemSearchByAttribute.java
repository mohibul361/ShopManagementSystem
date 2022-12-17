package com.sencillo.forms;

import java.util.List;

import com.sencillo.model.ItemAttributeValue;
import com.sencillo.model.ItemType;

public class ItemSearchByAttribute
{
	private ItemType itemType;
	private List<ItemAttributeValue> itemAttributeValues;
	
	public ItemType getItemType()
	{
		return itemType;
	}

	public void setItemType(ItemType itemType)
	{
		this.itemType = itemType;
	}

	public List<ItemAttributeValue> getItemAttributeValues()
	{
		return itemAttributeValues;
	}

	public void setItemAttributeValues(List<ItemAttributeValue> itemAttributeValues)
	{
		this.itemAttributeValues = itemAttributeValues;
	}
	
}
