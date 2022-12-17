package com.sencillo.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sencillo.model.Recipient;
import com.sencillo.service.RecipientService;

@Component
public class RecipientEditor extends PropertyEditorSupport
{
	@Autowired
	private RecipientService recipientService;

	// Converts a String to a Customer (when submitting form)
	@Override
	public void setAsText(String text)
	{
		try
		{
			System.out.println("text = "+text);
			if(!text.isEmpty())
			{
				Recipient recipient = recipientService.getRecipient(Integer.parseInt(text));
				this.setValue(recipient);
			}
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
		Recipient c = (Recipient) this.getValue();
		if(c != null)
			return c.getId().toString();
		return null;
	}
}
