package com.sencillo.forms;

import java.util.Date;
import java.util.List;

import com.sencillo.model.Item;
import com.sencillo.model.ItemAttributeValue;
import com.sencillo.model.ItemType;
import com.sencillo.model.Slip;

public class SlipForm
{
	private Slip slip;
	private List<Item> items;
	private List<ItemAttributeValue> itemAttributeValues;
	private ItemType itemType;
	private Date purchaseDate;
	private int warranty;
	
	public SlipForm()
	{
		slip = new Slip();
	}
	
	public Slip getSlip()
	{
		return slip;
	}

	public void setSlip(Slip slip)
	{
		this.slip = slip;
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

	public ItemType getItemType()
	{
		return itemType;
	}

	public void setItemType(ItemType itemType)
	{
		this.itemType = itemType;
	}

	public Date getPurchaseDate()
	{
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate)
	{
		this.purchaseDate = purchaseDate;
	}

	public int getWarranty()
	{
		return warranty;
	}

	public void setWarranty(int warranty)
	{
		this.warranty = warranty;
	}

	

	
}
