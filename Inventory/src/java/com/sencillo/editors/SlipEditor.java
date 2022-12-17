package com.sencillo.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sencillo.model.Slip;
import com.sencillo.service.SlipService;

@Component
public class SlipEditor extends PropertyEditorSupport
{
	@Autowired
	private SlipService slipService;

	// Converts a String to a User (when submitting form)
	@Override
	public void setAsText(String text)
	{
		try
		{
			System.out.println("slipeditor: text = "+text);
			Slip slip = null;
			if(!text.equals(""))
			{
				slip = slipService.getSlip(Integer.parseInt(text));
			}
			this.setValue(slip);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			this.setValue(null);
		}
		
	}

	// Converts a User to a String (when displaying form)
	@Override
	public String getAsText()
	{
		System.out.println("in getAsText");
		Slip i = (Slip) this.getValue();
		if(i != null)
			return i.getId().toString();
		return null;
	}
}
