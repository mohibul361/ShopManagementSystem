package com.sencillo.classes;

import java.io.Serializable;

import com.sencillo.model.ItemType;
import com.sencillo.model.Vendor;

public class VendorItemCount implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private Vendor vendor;
	private ItemType itemType;
	private double balanceQuantityInBag;
	private double balanceQuantityInKg;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public Vendor getVendor()
	{
		return vendor;
	}

	public void setVendor(Vendor vendor)
	{
		this.vendor = vendor;
	}

	public ItemType getItemType()
	{
		return itemType;
	}

	public void setItemType(ItemType itemType)
	{
		this.itemType = itemType;
	}

	public double getBalanceQuantityInBag()
	{
		return balanceQuantityInBag;
	}

	public void setBalanceQuantityInBag(double balanceQuantityInBag)
	{
		this.balanceQuantityInBag = balanceQuantityInBag;
	}

	public double getBalanceQuantityInKg()
	{
		return balanceQuantityInKg;
	}

	public void setBalanceQuantityInKg(double balanceQuantityInKg)
	{
		this.balanceQuantityInKg = balanceQuantityInKg;
	}

}
