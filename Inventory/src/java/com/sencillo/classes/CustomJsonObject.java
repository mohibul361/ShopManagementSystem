package com.sencillo.classes;

import java.util.List;

/**
 * 
 * @author Philip
 * This class is used in incoming slip/slipForm
 * Caught json data
 */
		
public class CustomJsonObject
{
	private List<Attribute> attributes;
	private Others others;
	
	public List<Attribute> getAttributes()
	{
		return attributes;
	}
	public void setAttributes(List<Attribute> attributes)
	{
		this.attributes = attributes;
	}
	public Others getOthers()
	{
		return others;
	}
	public void setOthers(Others others)
	{
		this.others = others;
	}
	
	
}