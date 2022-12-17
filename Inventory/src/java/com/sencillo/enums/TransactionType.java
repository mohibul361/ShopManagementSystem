package com.sencillo.enums;

public enum TransactionType {

	RECEIVE, PAYMENT, OPENINGBALNCE;
	
	@Override
	public String toString()
	{
		switch (this)
		{
			case RECEIVE:
				return "receive";
			case PAYMENT:
				return "payment";
			case OPENINGBALNCE:
				return "opening_balance";	
			default:
				throw new AssertionError();
		}
	}

}
