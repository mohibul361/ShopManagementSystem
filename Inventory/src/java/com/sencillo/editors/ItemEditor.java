package com.sencillo.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sencillo.model.Item;
import com.sencillo.service.ItemService;

@Component
public class ItemEditor extends PropertyEditorSupport
{
	@Autowired
	private ItemService itemService;

	// Converts a String to a Item (when submitting form)
	@Override
	public void setAsText(String text)
	{
		try
		{
			System.out.println("text = "+text);
			Item item = itemService.getItem(Integer.parseInt(text));
			this.setValue(item);
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
		Item i = (Item) this.getValue();
		if(i != null)
			return i.getId().toString();
		return null;
	}
}
