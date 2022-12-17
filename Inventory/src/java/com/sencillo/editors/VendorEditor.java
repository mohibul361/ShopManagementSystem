package com.sencillo.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sencillo.model.Vendor;
import com.sencillo.service.VendorService;

@Component
public class VendorEditor extends PropertyEditorSupport
{
	@Autowired
	private VendorService vendorService;

	// Converts a String to a Vendor (when submitting form)
	@Override
	public void setAsText(String text)
	{
		try
		{
			System.out.println("text = "+text);
			if(!text.isEmpty())
			{
				Vendor vendor = vendorService.getVendor(Integer.parseInt(text));
				this.setValue(vendor);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			this.setValue(null);
		}
		
	}

	// Converts a Vendor to a String (when displaying form)
	@Override
	public String getAsText()
	{
		System.out.println("in getAsText");
		Vendor c = (Vendor) this.getValue();
		if(c != null)
			return c.getId().toString();
		return null;
	}
}
