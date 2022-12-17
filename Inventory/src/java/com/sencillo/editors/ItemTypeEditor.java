package com.sencillo.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sencillo.model.ItemType;
import com.sencillo.service.ItemTypeService;

@Component
public class ItemTypeEditor extends PropertyEditorSupport
{
	@Autowired
	private ItemTypeService itemTypeService;

	// Converts a String to a ItemType (when submitting form)
	@Override
	public void setAsText(String text)
	{
		try
		{
			System.out.println("text = "+text);
			ItemType itemType = null;
			if(!text.equals(""))
			{
				itemType = itemTypeService.getItemType(Integer.parseInt(text));	
			}			
			this.setValue(itemType);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			this.setValue(null);
		}
		
	}

	// Converts a ItemType to a String (when displaying form)
	@Override
	public String getAsText()
	{
		System.out.println("in getAsText");
		ItemType i = (ItemType) this.getValue();
		if(i != null)
			return i.getId().toString();
		return null;
	}
}
