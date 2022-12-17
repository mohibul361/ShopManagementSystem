package com.sencillo.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sencillo.model.User;
import com.sencillo.service.UserService;

@Component
public class UserEditor extends PropertyEditorSupport
{
	@Autowired
	private UserService userService;

	// Converts a String to a User (when submitting form)
	@Override
	public void setAsText(String text)
	{
		try
		{
			System.out.println("usereditor: text = "+text);
			User user = null;
			if(!text.equals(""))
			{
				user = userService.getUser(Integer.parseInt(text));
			}
			this.setValue(user);
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
		User i = (User) this.getValue();
		if(i != null)
			return i.getId().toString();
		return null;
	}
}
