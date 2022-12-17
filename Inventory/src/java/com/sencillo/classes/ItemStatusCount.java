package com.sencillo.classes;

import java.util.List;

import com.sencillo.enums.ItemStatus;

/**
 * 
 * @author Philip This class is used to show summary in index page
 */

public class ItemStatusCount implements Comparable<ItemStatusCount>
{
	private ItemStatus itemStatus;
	private int count;
	private int displayOrder;
	private List<RecipientCount> recipientCountList;

	public ItemStatusCount(ItemStatus itemStatus, int displayOrder, int count, List<RecipientCount> recipientCountList)
	{
		super();
		this.itemStatus = itemStatus;
		this.displayOrder = displayOrder;
		this.count = count;
		this.recipientCountList = recipientCountList;
	}

	public ItemStatus getItemStatus()
	{
		return itemStatus;
	}

	public void setItemStatus(ItemStatus itemStatus)
	{
		this.itemStatus = itemStatus;
	}

	public int getCount()
	{
		return count;
	}

	public void setCount(int count)
	{
		this.count = count;
	}

	public List<RecipientCount> getRecipientCountList()
	{
		return recipientCountList;
	}

	public void setRecipientCountList(List<RecipientCount> recipientCountList)
	{
		this.recipientCountList = recipientCountList;
	}

	public int getDisplayOrder()
	{
		return displayOrder;
	}

	public void setDisplayOrder(int displayOrder)
	{
		this.displayOrder = displayOrder;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((itemStatus == null) ? 0 : itemStatus.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemStatusCount other = (ItemStatusCount) obj;
		if (itemStatus != other.itemStatus)
			return false;
		return true;
	}

	@Override
	public int compareTo(ItemStatusCount o)
	{
		int compareDisplay = ((ItemStatusCount) o).getDisplayOrder();

		// ascending order
		return this.displayOrder - compareDisplay;

	}

}
