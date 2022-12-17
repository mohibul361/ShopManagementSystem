package com.sencillo.enums;

public enum PaymentMode {

	CASH, CREDIT;
	
	@Override
	public String toString()
	{
		switch (this)
		{
			case CASH:
				return "Cash";
			case CREDIT:
				return "Credit";			
			default:
				throw new AssertionError();
		}
	}

}
