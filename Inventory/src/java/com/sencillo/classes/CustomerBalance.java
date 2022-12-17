package com.sencillo.classes;

import com.sencillo.model.Recipient;

/**
 * @author himu 
 * This class will be used to show customer balance Report
 */
public class CustomerBalance
{
	private Recipient customer;
	private Double balance;

	public CustomerBalance(Recipient customer, Double balance)
	{
		super();
		this.customer = customer;
		this.balance = balance;
	}

	public Recipient getCustomer()
	{
		return customer;
	}

	public void setCustomer(Recipient customer)
	{
		this.customer = customer;
	}

	public Double getBalance()
	{
		return balance;
	}

	public void setBalance(Double balance)
	{
		this.balance = balance;
	}

}
