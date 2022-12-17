package com.sencillo.enums;

public enum ItemStatus
{
	ON_HAND, INSTALLED_AND_CHECKED, DELIVERED, RETURN_DEFECTIVE, QC_DEFECTIVE;
	
	@Override
	public String toString()
	{
		switch (this)
		{			
			case ON_HAND:
				return "On Hand";
			case INSTALLED_AND_CHECKED:
				return "Checked";
			case DELIVERED:
				return "Delivered";				
			case RETURN_DEFECTIVE:
				return "Return Defective";
			case QC_DEFECTIVE:
				return "QC Defective";		
			default:
				throw new AssertionError();
		}
	}
}
