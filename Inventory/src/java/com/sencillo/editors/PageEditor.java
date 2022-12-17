package com.sencillo.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sencillo.model.Page;
import com.sencillo.service.PageService;

@Component
public class PageEditor extends PropertyEditorSupport
{
	@Autowired
	private PageService pageService;

	// Converts a String to a ItemType (when submitting form)
	@Override
	public void setAsText(String text)
	{
		try
		{
			System.out.println("text = "+text);
			Page page = null;
			if(!text.equals(""))
			{
				page = pageService.getPage(Integer.parseInt(text));
			}
			this.setValue(page);
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
		Page i = (Page) this.getValue();
		if(i != null)
			return i.getId().toString();
		return null;
	}
}
