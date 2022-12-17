package com.sencillo.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sencillo.model.Role;
import com.sencillo.service.RoleService;

@Component
public class RoleEditor extends PropertyEditorSupport
{
	@Autowired
	private RoleService roleService;

	// Converts a String to a Role (when submitting form)
	@Override
	public void setAsText(String text)
	{
		try
		{
			System.out.println("text = "+text);
			Role role = null;
			if(!text.equals(""))
			{
				role = roleService.getRole(Integer.parseInt(text));
			}
			this.setValue(role);
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
		Role i = (Role) this.getValue();
		if(i != null)
			return i.getId().toString();
		return null;
	}
}
