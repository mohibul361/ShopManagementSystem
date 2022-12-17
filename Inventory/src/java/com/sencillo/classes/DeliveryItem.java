package com.sencillo.classes;

import java.util.Date;

/**
 * 
 * @author Philip
 * This class will be used to show item in excel report
 */
public class DeliveryItem
{
	private String itemTypeName;
	private String serial;
	private Date deliveryDate;
	private String recipientName;

	public DeliveryItem(String itemTypeName, String serial, Date deliveryDate, String recipientName)
	{
		super();
		this.itemTypeName = itemTypeName;
		this.serial = serial;
		this.deliveryDate = deliveryDate;
		this.recipientName = recipientName;
	}

	public String getItemTypeName()
	{
		return itemTypeName;
	}

	public void setItemTypeName(String itemTypeName)
	{
		this.itemTypeName = itemTypeName;
	}

	public String getSerial()
	{
		return serial;
	}

	public void setSerial(String serial)
	{
		this.serial = serial;
	}

	public Date getDeliveryDate()
	{
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate)
	{
		this.deliveryDate = deliveryDate;
	}

	public String getRecipientName()
	{
		return recipientName;
	}

	public void setRecipientName(String recipientName)
	{
		this.recipientName = recipientName;
	}

}
