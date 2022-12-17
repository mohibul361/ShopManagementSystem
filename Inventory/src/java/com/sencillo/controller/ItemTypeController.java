package com.sencillo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sencillo.model.ItemType;
import com.sencillo.service.ItemTypeService;

@Controller
public class ItemTypeController
{

	@Autowired
	private ItemTypeService itemTypeService;

//	@ModelAttribute(value = "itemTypeList")
//	private void loadItemTypeList(Model model)
//	{
//		model.addAttribute("itemTypeList", this.itemTypeService.getItemTypeList());
//	}

	@RequestMapping(value = "/itemType", method = RequestMethod.GET)
	public String getPage(Model m)
	{
		m.addAttribute("itemType", new ItemType());
		return "itemType";
	}

	@RequestMapping(value = "/itemType/list", method = RequestMethod.GET)
	public String getItemTypeList(Model m)
	{
		m.addAttribute("itemTypeList", this.itemTypeService.getItemTypeList());
		return "itemTypeList";
	}
	
	@RequestMapping(value = "/itemType", method = RequestMethod.POST)
	public String save(@Valid ItemType itemType, BindingResult result, HttpServletRequest request, Model m)
	{
		if (result.hasErrors())
		{
			return "itemType";
		}
		if (itemType.getId() == null)
		{
			this.itemTypeService.save(itemType);
		}
		else
		{
			System.out.print("edit called");
			this.itemTypeService.edit(itemType);
		}

		m.addAttribute("itemType", new ItemType());		
		return "itemType";
	}

	@RequestMapping(value = "/itemType/{id}", method = RequestMethod.GET)
	public String getItemType(@PathVariable("id") int id, Model model)
	{
		model.addAttribute("itemType", this.itemTypeService.getItemType(id));
		return "itemType";
	}
	
}
