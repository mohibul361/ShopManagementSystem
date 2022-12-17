package com.sencillo.enums;

public enum AttributeType
{
	COMBO, TEXT;

	@Override
	public String toString()
	{
		switch (this)
		{
			case COMBO:
				return "combo";
			case TEXT:
				return "Text";
			default:
				throw new AssertionError();
		}
	}
}
