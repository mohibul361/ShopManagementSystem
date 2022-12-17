package com.sencillo.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sencillo.model.Region;
import com.sencillo.service.RegionService;

@Component
public class RegionEditor extends PropertyEditorSupport
{
	@Autowired
	private RegionService regionService;

	// Converts a String to a Item (when submitting form)
	@Override
	public void setAsText(String text)
	{
		try
		{
			System.out.println("text = "+text);
			Region region = regionService.getRegion(Integer.parseInt(text));
			this.setValue(region);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			this.setValue(null);
		}
		
	}

	// Converts a Item to a String (when displaying form)
	@Override
	public String getAsText()
	{
		System.out.println("in getAsText");
		Region i = (Region) this.getValue();
		if(i != null)
			return i.getId().toString();
		return null;
	}
}
