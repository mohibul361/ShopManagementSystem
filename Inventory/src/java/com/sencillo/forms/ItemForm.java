package com.sencillo.forms;

import java.util.List;

import com.sencillo.model.Item;
import com.sencillo.model.ItemAttributeValue;
import com.sencillo.model.ItemType;

public class ItemForm
{
	private ItemType itemType;
	private List<Item> items;
	private List<ItemAttributeValue> itemAttributeValues;

	public ItemType getItemType()
	{
		return itemType;
	}

	public void setItemType(ItemType itemType)
	{
		this.itemType = itemType;
	}

	public List<Item> getItems()
	{
		return items;
	}

	public void setItems(List<Item> items)
	{
		this.items = items;
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
