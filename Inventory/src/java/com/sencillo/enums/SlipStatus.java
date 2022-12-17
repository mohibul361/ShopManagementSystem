package com.sencillo.enums;

public enum SlipStatus
{
	SAVED, 
	/*PENDING,*/ 
	APPROVED;
	
	@Override
	public String toString()
	{
		switch (this)
		{
			case SAVED:
				return "Saved";
		/*	case PENDING:
				return "Pending";*/
			case APPROVED:
				return "Approved";			
			default:
				throw new AssertionError();
		}
	}
}
