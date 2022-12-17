package com.sencillo.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sencillo.model.ProblemType;
import com.sencillo.service.ProblemTypeService;

@Component
public class ProblemTypeEditor extends PropertyEditorSupport
{
	@Autowired
	private ProblemTypeService problemTypeService;
	ProblemType item = null;
	// Converts a String to a Item (when submitting form)
	@Override
	public void setAsText(String text)
	{
		try
		{
			System.out.println("text = "+text);
			if(!text.isEmpty())
			{
				item = problemTypeService.getProblemType(Integer.parseInt(text));
				this.setValue(item);
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
		ProblemType i = (ProblemType) this.getValue();
		if(i != null)
			return i.getId().toString();
		return null;
	}
}
