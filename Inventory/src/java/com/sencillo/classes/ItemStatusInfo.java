package com.sencillo.classes;

import com.sencillo.model.ItemType;

public class ItemStatusInfo
{
	private ItemType itemType;
	private int receivedTotal;
	private int qcFailed;
	private int qcPassed;
	private int delivered;
	private int returnFaulty;
	private int returnNormal;
	private int replacement;
	private int available;

	public ItemStatusInfo(ItemType itemType, int receivedTotal, int qcFailed, int qcPassed, int delivered, int returnFaulty, int retrunNormal, int replacement, int available)
	{
		super();
		this.itemType = itemType;
		this.receivedTotal = receivedTotal;
		this.qcFailed = qcFailed;
		this.qcPassed = qcPassed;
		this.delivered = delivered;
		this.returnFaulty = returnFaulty;
		this.returnNormal = retrunNormal;
		this.replacement = replacement;
		this.available = available;
	}

	
	public ItemType getItemType()
	{
		return itemType;
	}

	public void setItemType(ItemType itemType)
	{
		this.itemType = itemType;
	}

	public int getReceivedTotal()
	{
		return receivedTotal;
	}

	public void setReceivedTotal(int receivedTotal)
	{
		this.receivedTotal = receivedTotal;
	}

	public int getQcFailed()
	{
		return qcFailed;
	}

	public void setQcFailed(int qcFailed)
	{
		this.qcFailed = qcFailed;
	}

	public int getQcPassed()
	{
		return qcPassed;
	}

	public void setQcPassed(int qcPassed)
	{
		this.qcPassed = qcPassed;
	}

	public int getDelivered()
	{
		return delivered;
	}

	public void setDelivered(int delivered)
	{
		this.delivered = delivered;
	}

	public int getReturnFaulty()
	{
		return returnFaulty;
	}

	public void setReturnFaulty(int returnFaulty)
	{
		this.returnFaulty = returnFaulty;
	}

	public int getReturnNormal()
	{
		return returnNormal;
	}

	public void setReturnNormal(int returnNormal)
	{
		this.returnNormal = returnNormal;
	}

	public int getReplacement()
	{
		return replacement;
	}

	public void setReplacement(int replacement)
	{
		this.replacement = replacement;
	}

	public int getAvailable()
	{
		return available;
	}

	public void setAvailable(int available)
	{
		this.available = available;
	}

}
