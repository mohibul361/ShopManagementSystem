package com.sencillo.classes;

public class ItemAvailableStatus
{

	private Integer itemTypeId;
	private String itemTypeName;
	private double totalQtyinBagAvailable;
	private double totalQtyinKgAvailable;

	
	public ItemAvailableStatus(Integer itemTypeId, String itemTypeName, double totalQtyinBagAvailable,
			double totalQtyinKgAvailable)
	{
		super();
		this.itemTypeId = itemTypeId;
		this.itemTypeName = itemTypeName;
		this.totalQtyinBagAvailable = totalQtyinBagAvailable;
		this.totalQtyinKgAvailable = totalQtyinKgAvailable;
	}

	public Integer getItemTypeId()
	{
		return itemTypeId;
	}

	public void setItemTypeId(Integer itemTypeId)
	{
		this.itemTypeId = itemTypeId;
	}

	public String getItemTypeName()
	{
		return itemTypeName;
	}

	public void setItemTypeName(String itemTypeName)
	{
		this.itemTypeName = itemTypeName;
	}

	public double getTotalQtyinBagAvailable()
	{
		return totalQtyinBagAvailable;
	}

	public void setTotalQtyinBagAvailable(double totalQtyinBagAvailable)
	{
		this.totalQtyinBagAvailable = totalQtyinBagAvailable;
	}

	public double getTotalQtyinKgAvailable()
	{
		return totalQtyinKgAvailable;
	}

	public void setTotalQtyinKgAvailable(double totalQtyinKgAvailable)
	{
		this.totalQtyinKgAvailable = totalQtyinKgAvailable;
	}

}
