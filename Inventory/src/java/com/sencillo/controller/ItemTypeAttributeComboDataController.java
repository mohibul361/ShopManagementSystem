package com.sencillo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sencillo.editors.ItemTypeAttributeEditor;
import com.sencillo.model.ItemTypeAttribute;
import com.sencillo.model.ItemTypeAttributeComboData;
import com.sencillo.service.ItemTypeAttributeComboDataService;
import com.sencillo.service.ItemTypeAttributeService;

@Controller
public class ItemTypeAttributeComboDataController
{
	@Autowired
	private ItemTypeAttributeComboDataService itemTypeAttributeComboDataService;

	@Autowired
	private ItemTypeAttributeService itemTypeAttributeService;

	@Autowired
	private ItemTypeAttributeEditor itemTypeAttributeEditor;

	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		binder.registerCustomEditor(ItemTypeAttribute.class, this.itemTypeAttributeEditor);
	}

	@RequestMapping(value = "/itemTypeAttributeComboData/attribute/{id}", method = RequestMethod.GET)
	public String getPage(@PathVariable("id") int itemTypeAttributeId, Model m)
	{
		ItemTypeAttribute itemTypeAttribute = this.itemTypeAttributeService.getItemTypeAttribute(itemTypeAttributeId);
		resetModelObject(m, itemTypeAttribute);
		return "itemTypeAttributeComboData";
	}

	private Model resetModelObject(Model m, ItemTypeAttribute itemTypeAttribute)
	{
		ItemTypeAttributeComboData itemTypeAttributeComboData = new ItemTypeAttributeComboData();
		itemTypeAttributeComboData.setItemTypeAttribute(itemTypeAttribute);
		m.addAttribute("itemTypeAttributeComboData", itemTypeAttributeComboData);
		m.addAttribute("itemTypeAttributeComboDataList", this.itemTypeAttributeComboDataService.getItemTypeAttributeComboDataList(itemTypeAttribute.getId()));
		return m;
	}

	@RequestMapping(value = "/itemTypeAttributeComboData", method = RequestMethod.POST)
	public String Save(@Valid ItemTypeAttributeComboData itemTypeAttributeComboData, BindingResult bindingResult, Model m)
	{
		List<FieldError> errors = bindingResult.getFieldErrors();
		for (FieldError error : errors)
		{
			System.out.println(error.getObjectName() + " - " + error.getDefaultMessage());
		}

		if (bindingResult.hasErrors())
		{
			m.addAttribute("itemTypeAttributeComboDataList",
					this.itemTypeAttributeComboDataService.getItemTypeAttributeComboDataList(itemTypeAttributeComboData.getItemTypeAttribute().getId()));
			return "itemTypeAttributeComboData";
		}
		if (itemTypeAttributeComboData.getId() == null)
		{
			this.itemTypeAttributeComboDataService.save(itemTypeAttributeComboData);
		}
		else
		{
			this.itemTypeAttributeComboDataService.edit(itemTypeAttributeComboData);
		}
		resetModelObject(m, itemTypeAttributeComboData.getItemTypeAttribute());
		return "itemTypeAttributeComboData";
	}

	/*
	 * private ItemTypeAttributeComboData resetPage(ItemTypeAttribute itemTypeAttribute) { ItemTypeAttributeComboData itemTypeAttributeComboData = new ItemTypeAttributeComboData();
	 * itemTypeAttributeComboData.setItemTypeAttribute(itemTypeAttribute); return itemTypeAttributeComboData; }
	 */

	@RequestMapping(value = "/itemTypeAttributeComboData/{id}", method = RequestMethod.GET)
	public String getItemTypeAttributeComboDataValue(@PathVariable("id") int id, Model m)
	{
		ItemTypeAttributeComboData itemTypeAttributeComboData = this.itemTypeAttributeComboDataService.getItemTypeAttributeComboData(id);
		m.addAttribute("itemTypeAttributeComboData", itemTypeAttributeComboData);
		m.addAttribute("itemTypeAttributeComboDataList",
				this.itemTypeAttributeComboDataService.getItemTypeAttributeComboDataList(itemTypeAttributeComboData.getItemTypeAttribute().getId()));
		return "itemTypeAttributeComboData";
	}

	@RequestMapping(value = "/delete/{attrValueId}", method = RequestMethod.GET)
	public String deleteItemTypeAttributeValue(@PathVariable("attrValueId") Integer itemTypeAttributeComboDataId, Model m)
	{
		ItemTypeAttribute itemTypeAttribute = null;
		itemTypeAttribute = this.itemTypeAttributeComboDataService.getItemTypeAttributeComboData(itemTypeAttributeComboDataId).getItemTypeAttribute();

		if (itemTypeAttributeComboDataId != null)
		{
			this.itemTypeAttributeComboDataService.remove(itemTypeAttributeComboDataId);
		}
		resetModelObject(m, itemTypeAttribute);
		return "redirect:/itemTypeAttributeComboData/attribute/"+itemTypeAttribute.getId();
	}

	
}
