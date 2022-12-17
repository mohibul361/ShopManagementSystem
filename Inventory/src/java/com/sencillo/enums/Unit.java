package com.sencillo.enums;

public enum Unit
{
	BAG, KG;
	
	@Override
	public String toString()
	{
		switch (this)
		{
			case BAG:
				return "Bag";
			case KG:
				return "Kg";			
			default:
				throw new AssertionError();
		}
	}
}
