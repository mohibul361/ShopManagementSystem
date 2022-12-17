package com.sencillo.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sencillo.editors.ItemTypeEditor;
import com.sencillo.enums.AttributeType;
import com.sencillo.model.Item;
import com.sencillo.model.ItemAttributeValue;
import com.sencillo.model.ItemType;
import com.sencillo.model.ItemTypeAttribute;
import com.sencillo.model.ItemTypeAttributeComboData;
import com.sencillo.service.ItemService;
import com.sencillo.service.ItemTypeAttributeService;
import com.sencillo.service.ItemTypeService;

@Controller
public class ItemTypeAttributeController
{
	@Autowired
	private ItemTypeAttributeService itemTypeAttributeService;

	@Autowired
	private ItemTypeService itemTypeService;

	@Autowired
	private ItemService itemService;

	@Autowired
	private ItemTypeEditor itemTypeEditor;

	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		binder.registerCustomEditor(ItemType.class, this.itemTypeEditor);
	}

	@ModelAttribute(value = "itemTypeList")
	private List<ItemType> populateItemTypeList()
	{
		List<ItemType> itemTypeList = this.itemTypeService.getItemTypeList();
		return itemTypeList;
		/*
		 * List<ItemObject> itemObjects = new ArrayList<>(); for (ItemType itemType : itemTypeList) { itemObjects.add(new ItemObject(itemType.getId(), itemType.getName())); }
		 * return itemObjects;
		 */
	}

	@ModelAttribute
	private void loadItemTypeList(Model model)
	{
		model.addAttribute("itemTypeAttributeList", this.itemTypeAttributeService.getItemTypeAttributeList(null));
	}

	@RequestMapping(value = "/itemTypeAttribute", method = RequestMethod.GET)
	public String getPage(Model m)
	{
		m.addAttribute("itemTypeAttribute", new ItemTypeAttribute());
		return "itemTypeAttribute";
	}

	@RequestMapping(value = "/itemTypeAttribute", method = RequestMethod.POST)
	public String Save(@Valid ItemTypeAttribute itemTypeAttribute, BindingResult bindingResult, Model m)
	{
		List<FieldError> errors = bindingResult.getFieldErrors();
		for (FieldError error : errors)
		{
			System.out.println(error.getObjectName() + " - " + error.getDefaultMessage());
		}

		if (bindingResult.hasErrors())
		{
			return "itemTypeAttribute";
		}
		if (itemTypeAttribute.getId() == null)
		{
			this.itemTypeAttributeService.save(itemTypeAttribute);
		}
		else
		{
			this.itemTypeAttributeService.edit(itemTypeAttribute);
		}
		m.addAttribute("itemTypeAttribute", new ItemTypeAttribute());
		m.addAttribute("itemTypeAttributeList", this.itemTypeAttributeService.getItemTypeAttributeList(null));

		return "itemTypeAttribute";
	}

	@RequestMapping(value = "/itemTypeAttribute/{id}", method = RequestMethod.GET)
	public String getItemTypeAttribute(@PathVariable("id") int id, Model model)
	{
		model.addAttribute("itemTypeAttribute", this.itemTypeAttributeService.getItemTypeAttribute(id));
		return "itemTypeAttribute";
	}

	@RequestMapping(value = "/itemTypeAttribute/delete/{attrId}", method = RequestMethod.GET)
	public String deleteItemTypeAttribute(@PathVariable("attrId") Integer itemTypeAttributeId, Model m)
	{
		if (itemTypeAttributeId != null)
		{
			this.itemTypeAttributeService.delete(itemTypeAttributeId);
		}

		return "redirect:/itemTypeAttribute";
	}

/*	*//**
	 * This method is used in incoming challan page
	 * @param itemTypeId
	 * @return
	 *//*
	@RequestMapping(value = "/attributeDataForSlipForm/{itemTypeId}", method = RequestMethod.GET, produces = "text/html")
	public @ResponseBody
	String getAttributeDataForSlipFormByItemType(@PathVariable("itemTypeId") Integer itemTypeId)
	{
		Map<ItemTypeAttribute, List<ItemTypeAttributeComboData>> map = this.itemTypeAttributeService.getItemTypeAttributeData(itemTypeId);

		String returnText = "<fieldset><legend>Attributes</legend>";
		int i = 0;
		for (Map.Entry<ItemTypeAttribute, List<ItemTypeAttributeComboData>> entry : map.entrySet())
		{
			System.out.println(entry.getKey() + "/" + entry.getValue());
			ItemTypeAttribute itemTypeAttribute = entry.getKey();
			returnText += String.format("<input type='hidden' id=%sitemTypeAttribute value=%s>", i, itemTypeAttribute.getId());
			if (itemTypeAttribute.getAttributeType() == AttributeType.COMBO)
			{

				if (itemTypeAttribute.getItemTypeAttributeComboDatas() != null)
				{
					if (!itemTypeAttribute.getItemTypeAttributeComboDatas().isEmpty())
					{
						returnText += String.format("<div class='attributeDiv'><label for=%sattributeValue>%s: </label>", i, itemTypeAttribute.getName());
						returnText += String.format("<select class='attribute' id=%sattributeValue>", i);
						returnText += "<option value=0>--Select one--</option>";
						for (ItemTypeAttributeComboData itemTypeAttributeComboData : itemTypeAttribute.getItemTypeAttributeComboDatas())
						{
							returnText += String.format("<option value='%s'>%s</option>", itemTypeAttributeComboData.getValue(), itemTypeAttributeComboData.getValue());
						}
						returnText += "</select>";
						returnText += "</div>";
					}
				}
			}
			else
			{
				returnText += String.format("<div><label>'%s': </label>", itemTypeAttribute.getName());
				returnText += String.format("<input type='text' class='attribute' name=itemAttributeValues[%s].attributeValue value=''>", i);
				returnText += "</div>";
			}
			i++;
		}
		returnText += "</fieldset>";
		return returnText;
	}*/
	/**
	 * This method is used in incoming challan page
	 * @param itemTypeId
	 * @return
	 */
	@RequestMapping(value = "/attributeDataForSlipForm/{itemTypeId}", method = RequestMethod.GET, produces = "text/html")
	public @ResponseBody
	String getAttributeDataForSlipFormByItemType(@PathVariable("itemTypeId") Integer itemTypeId)
	{
		Map<ItemTypeAttribute, List<ItemTypeAttributeComboData>> map = this.itemTypeAttributeService.getItemTypeAttributeData(itemTypeId);

		String returnText = "<fieldset><legend>Attributes</legend>";
		int i = 0;
		for (Map.Entry<ItemTypeAttribute, List<ItemTypeAttributeComboData>> entry : map.entrySet())
		{
			System.out.println(entry.getKey() + "/" + entry.getValue());
			ItemTypeAttribute itemTypeAttribute = entry.getKey();
			returnText += String.format("<input type='hidden' id=%sitemTypeAttribute value=%s>", i, itemTypeAttribute.getId());
			if (itemTypeAttribute.getAttributeType() == AttributeType.COMBO)
			{

				if (itemTypeAttribute.getItemTypeAttributeComboDatas() != null)
				{
					if (!itemTypeAttribute.getItemTypeAttributeComboDatas().isEmpty())
					{
						returnText += String.format("<div class='attributeDiv'><label for=%sattributeValue>%s: </label>", i, itemTypeAttribute.getName());
						returnText += String.format("<select class='attribute' id=%sattributeValue>", i);
						returnText += "<option value=0>--Select one--</option>";
						for (ItemTypeAttributeComboData itemTypeAttributeComboData : itemTypeAttribute.getItemTypeAttributeComboDatas())
						{
							returnText += String.format("<option value='%s'>%s</option>", itemTypeAttributeComboData.getValue(), itemTypeAttributeComboData.getValue());
						}
						returnText += "</select>";
						returnText += "</div>";
					}
				}
			}
			else
			{
				returnText += String.format("<div><label>'%s': </label>", itemTypeAttribute.getName());
				returnText += String.format("<input type='text' class='attribute' name=itemAttributeValues[%s].attributeValue value=''>", i);
				returnText += "</div>";
			}
			i++;
		}
		returnText += "</fieldset>";
		return returnText;
	}

	/**
	 * This method is used for itemForm page
	 * This page is not used in production, it was developed in test basis
	 * @param itemTypeId
	 * @return
	 */
	@RequestMapping(value = "/attributeData/{itemTypeId}", method = RequestMethod.GET, produces = "text/html")
	public @ResponseBody
	String getAttributeDataByItemType(@PathVariable("itemTypeId") Integer itemTypeId)
	{
		Map<ItemTypeAttribute, List<ItemTypeAttributeComboData>> map = this.itemTypeAttributeService.getItemTypeAttributeData(itemTypeId);

		String returnText = "<fieldset><legend>Attributes</legend>";
		int i = 0;
		for (Map.Entry<ItemTypeAttribute, List<ItemTypeAttributeComboData>> entry : map.entrySet())
		{
			System.out.println(entry.getKey() + "/" + entry.getValue());
			ItemTypeAttribute itemTypeAttribute = entry.getKey();
			returnText += "<input type='hidden' name=itemAttributeValues[" + i + "].itemTypeAttribute value=" + itemTypeAttribute.getId() + ">";
			if (itemTypeAttribute.getAttributeType() == AttributeType.COMBO)
			{

				if (itemTypeAttribute.getItemTypeAttributeComboDatas() != null)
				{
					if (!itemTypeAttribute.getItemTypeAttributeComboDatas().isEmpty())
					{
						returnText += "<div style='margin-bottom:10px'><label>" + itemTypeAttribute.getName() + ": </label>";
						returnText += "<select name=itemAttributeValues[" + i + "].attributeValue>";
						returnText += "<option value=0>--Select one--</option>";
						for (ItemTypeAttributeComboData itemTypeAttributeComboData : itemTypeAttribute.getItemTypeAttributeComboDatas())
						{
							returnText += String.format("<option value='%s'>%s</option>", itemTypeAttributeComboData.getValue(), itemTypeAttributeComboData.getValue());
						}
						returnText += "</select>";
						returnText += "</div>";
					}
				}
			}
			else
			{
				returnText += "<div><label>" + itemTypeAttribute.getName() + ": </label>";
				returnText += "<input type='text' name=itemAttributeValues[" + i + "].attributeValue value=''>";
				returnText += "</div>";
			}
			i++;
		}
		returnText += "</fieldset>";
		return returnText;
	}
	
	/**
	 * This method is used for itemForm page
	 * This page is not used in production, it was developed in test basis
	 */

	@RequestMapping(value = "/attributeData/{itemTypeId}/item/{itemId}", method = RequestMethod.GET, produces = "text/html")
	public @ResponseBody
	String getAttributeDataByItem(@PathVariable("itemTypeId") Integer itemTypeId, @PathVariable("itemId") Integer itemId)
	{
		Map<ItemTypeAttribute, List<ItemTypeAttributeComboData>> map = this.itemTypeAttributeService.getItemTypeAttributeData(itemTypeId);

		Item item = this.itemService.getItem(itemId);

		String returnText = "<fieldset><legend>Attributes</legend>";
		int i = 0;
		for (Map.Entry<ItemTypeAttribute, List<ItemTypeAttributeComboData>> entry : map.entrySet())
		{
			System.out.println(entry.getKey() + "/" + entry.getValue());
			ItemTypeAttribute itemTypeAttribute = entry.getKey();

			String value = "";
			for (ItemAttributeValue itemAttributeValue : item.getItemAttributeValues())
			{
				if (itemAttributeValue.getItemTypeAttribute().getId().equals(itemTypeAttribute.getId()))
				{
					value = itemAttributeValue.getAttributeValue();
				}
			}

			returnText += "<input type='hidden' name=itemAttributeValues[" + i + "].itemTypeAttribute value=" + itemTypeAttribute.getId() + ">";
			if (itemTypeAttribute.getAttributeType() == AttributeType.COMBO)
			{

				if (itemTypeAttribute.getItemTypeAttributeComboDatas() != null)
				{
					if (!itemTypeAttribute.getItemTypeAttributeComboDatas().isEmpty())
					{
						returnText += "<div><label>" + itemTypeAttribute.getName() + ": </label>";
						returnText += "<select name=itemAttributeValues[" + i + "].attributeValue>";
						returnText += "<option value=0>--Select one--</option>";
						for (ItemTypeAttributeComboData itemTypeAttributeComboData : itemTypeAttribute.getItemTypeAttributeComboDatas())
						{
							if (value.equals(itemTypeAttributeComboData.getValue()))
							{
								returnText += String.format("<option selected='selected' value='%s'>%s</option>", itemTypeAttributeComboData.getValue(),
										itemTypeAttributeComboData.getValue());
							}
							else
							{
								returnText += String.format("<option value='%s'>%s</option>", itemTypeAttributeComboData.getValue(), itemTypeAttributeComboData.getValue());
							}

						}
						returnText += "</select>";
						returnText += "</div>";
					}
				}
			}
			else
			{
				returnText += "<div><label>" + itemTypeAttribute.getName() + ": </label>";
				returnText += "<input type='text' name=itemAttributeValues[" + i + "].attributeValue value=" + value + ">";
				returnText += "</div>";
			}
			i++;
		}
		returnText += "</fieldset>";
		return returnText;
	}

	/**
	 * This method is used in item count Search page
	 */
	@RequestMapping(value = "/attributeDataForItemSearch/{itemTypeId}", method = RequestMethod.GET, produces = "text/html")
	public @ResponseBody
	String getAttributeData(@PathVariable("itemTypeId") Integer itemTypeId)
	{
		Map<ItemTypeAttribute, List<ItemTypeAttributeComboData>> map = this.itemTypeAttributeService.getItemTypeAttributeData(itemTypeId);

		String returnText = "<fieldset><legend>Attributes</legend>";
		int i = 0;
		for (Map.Entry<ItemTypeAttribute, List<ItemTypeAttributeComboData>> entry : map.entrySet())
		{
			System.out.println(entry.getKey() + "/" + entry.getValue());
			ItemTypeAttribute itemTypeAttribute = entry.getKey();
			returnText += String.format("<input type='hidden' name=itemAttributeValues[" + i + "].itemTypeAttribute id=%sitemTypeAttribute value=%s>", i, itemTypeAttribute.getId());
			/*returnText += "<input type='hidden' name=itemAttributeValues[" + i + "].itemTypeAttribute value=" + itemTypeAttribute.getId() + ">";*/
			if (itemTypeAttribute.getAttributeType() == AttributeType.COMBO)
			{

				if (itemTypeAttribute.getItemTypeAttributeComboDatas() != null)
				{
					if (!itemTypeAttribute.getItemTypeAttributeComboDatas().isEmpty())
					{
						returnText += "<div style='margin-bottom:10px'><label>" + itemTypeAttribute.getName() + ": </label>";
						returnText += String.format("<select class='attribute' name=itemAttributeValues[" + i + "].attributeValue id=%sattributeValue>", i);
						/*returnText += "<select class='attribute' name=itemAttributeValues[" + i + "].attributeValue>";*/
						returnText += "<option value=0>--Select one--</option>";
						for (ItemTypeAttributeComboData itemTypeAttributeComboData : itemTypeAttribute.getItemTypeAttributeComboDatas())
						{
							returnText += String.format("<option value='%s'>%s</option>", itemTypeAttributeComboData.getValue(), itemTypeAttributeComboData.getValue());
						}
						returnText += "</select>";
						returnText += "</div>";
					}
				}
			}
			else
			{
				returnText += "<div><label>" + itemTypeAttribute.getName() + ": </label>";
				returnText += "<input type='text' name=itemAttributeValues[" + i + "].attributeValue value=''>";
				returnText += "</div>";
			}
			i++;
		}
		returnText += "</fieldset>";
		return returnText;
	}
	
}