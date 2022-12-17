package com.sencillo.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sencillo.model.ItemTypeAttribute;
import com.sencillo.service.ItemTypeAttributeService;

@Component
public class ItemTypeAttributeEditor extends PropertyEditorSupport
{
	@Autowired
	private ItemTypeAttributeService itemTypeAttributeService;

	// Converts a String to a ItemType (when submitting form)
	@Override
	public void setAsText(String text)
	{
		try
		{
			System.out.println("text = " + text);
			ItemTypeAttribute itemTypeAttribute = itemTypeAttributeService.getItemTypeAttribute(Integer.parseInt(text));
			this.setValue(itemTypeAttribute);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			this.setValue(null);
		}

	}

	// Converts a ItemType to a String (when displaying form)
	/*
	 * @Override public String getAsText() { System.out.println("in getAsText"); ItemType i = (ItemType) this.getValue(); if(i != null) return i.getId().toString(); return null; }
	 */
}
