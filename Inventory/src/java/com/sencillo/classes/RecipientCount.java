package com.sencillo.classes;

import com.sencillo.model.Recipient;

/**
 * 
 * @author Philip
 * This class is used as a property of ItemStatusCount
 */

public class RecipientCount
{
	private Recipient recipient;
	private String company;
	private int count;
	
	public RecipientCount(Recipient recipient, String company, int count)
	{
		super();
		this.recipient = recipient;
		this.company = company;
		this.count = count;
	}
	public Recipient getRecipient()
	{
		return recipient;
	}
	public void setRecipient(Recipient recipient)
	{
		this.recipient = recipient;
	}	
	public String getCompany()
	{
		return company;
	}
	public void setCompany(String company)
	{
		this.company = company;
	}
	public int getCount()
	{
		return count;
	}
	public void setCount(int count)
	{
		this.count = count;
	}
	
	
}
