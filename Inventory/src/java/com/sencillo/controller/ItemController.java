package com.sencillo.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sencillo.classes.Attribute;
import com.sencillo.classes.CustomJsonObject;
import com.sencillo.classes.DeliveryItem;
import com.sencillo.classes.DeliverySlipInfo;
import com.sencillo.classes.Others;
import com.sencillo.editors.ItemTypeAttributeEditor;
import com.sencillo.editors.ItemTypeEditor;
import com.sencillo.enums.ItemStatus;
import com.sencillo.forms.ItemForm;
import com.sencillo.forms.ItemSearchByAttribute;
import com.sencillo.model.Item;
import com.sencillo.model.ItemAttributeValue;
import com.sencillo.model.ItemType;
import com.sencillo.model.ItemTypeAttribute;
import com.sencillo.model.Slip;
import com.sencillo.service.ItemService;
import com.sencillo.service.ItemTypeService;
import com.sencillo.service.RecipientService;
import com.sencillo.service.SlipService;

@Controller
public class ItemController
{
	@Autowired
	private ItemService itemService;

	@Autowired
	private ItemTypeService itemTypeService;

	@Autowired
	private ItemTypeEditor itemTypeEditor;

	@Autowired
	private ItemTypeAttributeEditor itemTypeAttributeEditor;

	@Autowired
	private RecipientService recipientService;

	@Autowired
	private SlipService slipService;

	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		binder.registerCustomEditor(ItemType.class, this.itemTypeEditor);
		binder.registerCustomEditor(ItemTypeAttribute.class, this.itemTypeAttributeEditor);
	}

	@RequestMapping(value = "/item")
	public String loadPage(Model m)
	{

		m.addAttribute("item", new Item());
		m.addAttribute("itemTypeList", this.itemTypeService.getItemTypeList());
		m.addAttribute("itemList", this.itemService.getItemList());
		return "item";
	}

	@RequestMapping(value = "/itemForm")
	public String loadItemForm(Model m)
	{
		/*
		 * ItemForm itemForm = new ItemForm(); List<Item> items = new ArrayList<>(); Item i1 = new Item(); i1.setSerial("1"); i1.setQuantity(3); i1.setName("item1"); items.add(i1);
		 * Item i2 = new Item(); i2.setSerial("2"); i2.setQuantity(2); i2.setName("item2"); items.add(i2);
		 * 
		 * itemForm.setItems(items);
		 */
		// m.addAttribute("itemForm", itemForm);
		m.addAttribute("itemForm", new ItemForm());
		m.addAttribute("itemTypeList", this.itemTypeService.getItemTypeList());
		return "itemForm";
	}

	@RequestMapping(value = "/generate/{count}", method = RequestMethod.GET, produces = "text/html")
	public @ResponseBody
	String generateItemList(@PathVariable("count") int count, Model m)
	{
		String returnText = "";
		List<Item> items = new ArrayList<>();
		for (int i = 0; i < count; i++)
		{
			int sl = i + 1;
			returnText += "<tr>";
			returnText += "<td align='center'>" + sl + "</td>";
			returnText += "<td><input type='text' name=items[" + i + "].serial value=''></td>";
			returnText += "<td><input type='text' name=items[" + i + "].quantity value='1'></td>";
			returnText += "<input type='hidden' name=itemAttributeValues[" + i + "].itemTypeAttribute value=2>";
			returnText += "<input type='hidden' name=itemAttributeValues[" + i + "].attributeValue value=10 MP>";
			returnText += "</tr>";
		}
		ItemForm itemForm = new ItemForm();
		itemForm.setItems(items);

		m.addAttribute("", "");
		System.out.println("return text " + returnText);
		return returnText;

	}

//	@RequestMapping(value = "/generate", method = RequestMethod.POST)
//	public @ResponseBody
//	String generateItemForSlipFormList(@RequestBody String str, Model m) throws JsonProcessingException, IOException
//	{
//		ObjectMapper mapper = new ObjectMapper();
//
//		CustomJsonObject customJsonObject = mapper.readValue(str, CustomJsonObject.class);
//		List<Attribute> attributes = customJsonObject.getAttributes();
//		Others others = customJsonObject.getOthers();
//		int count = others.getCount();
//		Integer itemTypeId = others.getItemTypeId();
//		int rowCount = others.getRowCount();
//		String purchaseDate = others.getPurchaseDate();
//		int warranty = others.getWarranty();
//
//		String itemName = null;
//		if (itemTypeId != null)
//		{
//			ItemType itemType = this.itemTypeService.getItemType(itemTypeId);
//			itemName = itemType.getName();
//		}
//
//		for (Attribute attribute : attributes)
//		{
//			itemName += " > " + attribute.getAttributeValue();
//		}
//
//		/************ serial generation code starts ***********/
//		String serial = others.getSerialStartFrom();// "H52151000094";
//		String serial_prefix = null, serial_postfix = null;
//		int number = 0;
//		if (!serial.isEmpty())
//		{
//			int length = serial.length();
//			int prefix_length = length - 4;
//			serial_prefix = serial.substring(0, prefix_length);
//			serial_postfix = serial.substring(prefix_length, length);
//			number = Integer.parseInt(serial_postfix);
//			System.out.println("prefix = " + serial_prefix);
//			System.out.println("postfix = " + serial_postfix);
//		}
//
//		/******************* serial generation code ends ***********/
//
//		String returnText = "";
//		int itemIndex = rowCount;
//		rowCount++;
//		for (int i = 0; i < count; i++)
//		{
//			/************ serial generation code starts ***********/
//			if (!serial.isEmpty())
//			{
//				int new_postfix = number + i;
//				int leading_zero = serial_postfix.length() - String.valueOf(new_postfix).length();
//				System.out.println("leading_zero " + leading_zero);
//				if (leading_zero == -1)
//				{
//
//				}
//				else if (leading_zero == 0)
//				{
//					serial = serial_prefix + String.valueOf(new_postfix);
//				}
//				else if (leading_zero == 1)
//				{
//					serial = serial_prefix + "0" + String.valueOf(new_postfix);
//				}
//				else if (leading_zero == 2)
//				{
//					serial = serial_prefix + "00" + String.valueOf(new_postfix);
//				}
//				else if (leading_zero == 3)
//				{
//					serial = serial_prefix + "000" + String.valueOf(new_postfix);
//				}
//				System.out.println("serial= " + serial);
//			}
//
//			/******************* serial generation code ends ***********/
//			returnText += "<tr>";
//			returnText += String.format("<td align='center'>%s</td>", rowCount);
//			returnText += String.format("<td>%s</td>", itemName);
//			returnText += String.format("<td><input type='text' class='serialField' name=items[%s].serial value='%s'></td>", itemIndex, serial);
//			returnText += String.format("<input type='hidden' name=items[%s].quantity value='1'>", itemIndex);
//			returnText += String.format("<input type='hidden' name=items[%s].name value='%s'>", itemIndex, itemName);
//			returnText += String.format("<input type='hidden' name=items[%s].itemType value=%s>", itemIndex, itemTypeId);
//			returnText += String.format("<input type='hidden' name=items[%s].purchaseDate value=%s>", itemIndex, purchaseDate);
//			returnText += String.format("<input type='hidden' name=items[%s].warranty value=%s>", itemIndex, warranty);
//			int j = 0;
//			for (Attribute attribute : attributes)
//			{
//				returnText += String.format("<input type='hidden' name=items[%s].itemAttributeValues[%s].itemTypeAttribute value=%s>", itemIndex, j, attribute.getAttributeId());
//				returnText += String.format("<input type='hidden' name=items[%s].itemAttributeValues[%s].attributeValue value='%s'>", itemIndex, j, attribute.getAttributeValue());
//				j++;
//			}
//			returnText += "</tr>";
//			rowCount++;
//			itemIndex++;
//		}
//
//		System.out.println("return text " + returnText);
//		return returnText;
//	}
	
	@RequestMapping(value = "/generate", method = RequestMethod.POST)
	public @ResponseBody
	String generateItemForSlipFormList(@RequestBody String str, Model m) throws JsonProcessingException, IOException
	{
		ObjectMapper mapper = new ObjectMapper();

		CustomJsonObject customJsonObject = mapper.readValue(str, CustomJsonObject.class);
//		List<Attribute> attributes = customJsonObject.getAttributes();
//		Others others = customJsonObject.getOthers();
//		int count = others.getCount();
//		Integer itemTypeId = others.getItemTypeId();
//		int rowCount = others.getRowCount();
//		String purchaseDate = others.getPurchaseDate();
//		int warranty = others.getWarranty();
//
//		String itemName = null;
//		if (itemTypeId != null)
//		{
//			ItemType itemType = this.itemTypeService.getItemType(itemTypeId);
//			itemName = itemType.getName();
//		}
//
//		for (Attribute attribute : attributes)
//		{
//			itemName += " > " + attribute.getAttributeValue();
//		}
//
//		/************ serial generation code starts ***********/
//		String serial = others.getSerialStartFrom();// "H52151000094";
//		String serial_prefix = null, serial_postfix = null;
//		int number = 0;
//		if (!serial.isEmpty())
//		{
//			int length = serial.length();
//			int prefix_length = length - 4;
//			serial_prefix = serial.substring(0, prefix_length);
//			serial_postfix = serial.substring(prefix_length, length);
//			number = Integer.parseInt(serial_postfix);
//			System.out.println("prefix = " + serial_prefix);
//			System.out.println("postfix = " + serial_postfix);
//		}
//
//		/******************* serial generation code ends ***********/
//
//		String returnText = "";
//		int itemIndex = rowCount;
//		rowCount++;
//		for (int i = 0; i < count; i++)
//		{
//			/************ serial generation code starts ***********/
//			if (!serial.isEmpty())
//			{
//				int new_postfix = number + i;
//				int leading_zero = serial_postfix.length() - String.valueOf(new_postfix).length();
//				System.out.println("leading_zero " + leading_zero);
//				if (leading_zero == -1)
//				{
//
//				}
//				else if (leading_zero == 0)
//				{
//					serial = serial_prefix + String.valueOf(new_postfix);
//				}
//				else if (leading_zero == 1)
//				{
//					serial = serial_prefix + "0" + String.valueOf(new_postfix);
//				}
//				else if (leading_zero == 2)
//				{
//					serial = serial_prefix + "00" + String.valueOf(new_postfix);
//				}
//				else if (leading_zero == 3)
//				{
//					serial = serial_prefix + "000" + String.valueOf(new_postfix);
//				}
//				System.out.println("serial= " + serial);
//			}
//
//			/******************* serial generation code ends ***********/
//			returnText += "<tr>";
//			returnText += String.format("<td align='center'>%s</td>", rowCount);
//			returnText += String.format("<td>%s</td>", itemName);
//			returnText += String.format("<td><input type='text' class='serialField' name=items[%s].serial value='%s'></td>", itemIndex, serial);
//			returnText += String.format("<input type='hidden' name=items[%s].quantity value='1'>", itemIndex);
//			returnText += String.format("<input type='hidden' name=items[%s].name value='%s'>", itemIndex, itemName);
//			returnText += String.format("<input type='hidden' name=items[%s].itemType value=%s>", itemIndex, itemTypeId);
//			returnText += String.format("<input type='hidden' name=items[%s].purchaseDate value=%s>", itemIndex, purchaseDate);
//			returnText += String.format("<input type='hidden' name=items[%s].warranty value=%s>", itemIndex, warranty);
//			int j = 0;
//			for (Attribute attribute : attributes)
//			{
//				returnText += String.format("<input type='hidden' name=items[%s].itemAttributeValues[%s].itemTypeAttribute value=%s>", itemIndex, j, attribute.getAttributeId());
//				returnText += String.format("<input type='hidden' name=items[%s].itemAttributeValues[%s].attributeValue value='%s'>", itemIndex, j, attribute.getAttributeValue());
//				j++;
//			}
//			returnText += "</tr>";
//			rowCount++;
//			itemIndex++;
//		}
//
//		System.out.println("return text " + returnText);
//		return returnText;
		String returnText = "";
		int j = 0;
		for (Attribute attribute : customJsonObject.getAttributes())
			{
				returnText += String.format("<input type='hidden' name=itemAttributeValues[%s].itemTypeAttribute value=%s>", j, attribute.getAttributeId());
				returnText += String.format("<input type='hidden' name=itemAttributeValues[%s].attributeValue value='%s'>", j, attribute.getAttributeValue());
				j++;
			}
		 returnText += String.format("<input type='hidden' name=purchaseDate value=%s>", customJsonObject.getOthers().getPurchaseDate());
		 returnText += String.format("<input type='hidden' name=itemType value=%s>", customJsonObject.getOthers().getItemTypeId());
		 returnText += String.format("<input type='hidden' name=warranty value=%s>", customJsonObject.getOthers().getWarranty());
		return returnText;
	}

	@RequestMapping(value = "/itemForm", method = RequestMethod.POST)
	public String saveItemForm(@Valid ItemForm itemForm, BindingResult result, HttpServletRequest request, Model m)
	{
		if (result.hasErrors())
		{
			return "itemForm";
		}
		/*
		 * Map<String, String[]> parameters = request.getParameterMap(); for (String parameter : parameters.keySet()) { System.out.println("Parameter: "+parameter); String[] values
		 * = parameters.get(parameter); System.out.println("VALUES: " + values); }
		 */// Integer itemTypeId = (Integer) request.getAttribute("itemType");

		ItemType itemType = itemForm.getItemType();
		System.out.println(">>> " + itemType.getName());

		int i = 1;
		for (Item item : itemForm.getItems())
		{
			item.setItemType(itemType);
			item.setName("" + i++);
			System.out.println("item >>> " + item.getSerial());
			item.getItemAttributeValues().clear();
			for (ItemAttributeValue itemAttributeValue : itemForm.getItemAttributeValues())
			{
				System.out.println("---" + itemAttributeValue);
				itemAttributeValue.setItem(item);
				itemAttributeValue.setId(null);
				item.getItemAttributeValues().add(itemAttributeValue);
			}

			this.itemService.save(item);
		}

		for (ItemAttributeValue itemAttributeValue : itemForm.getItemAttributeValues())
		{
			System.out.println("itemAttributeValue.getItemTypeAttribute().getId()>>> " + itemAttributeValue.getItemTypeAttribute().getId());
			System.out.println("itemAttributeValue.getAttributeValue() >>> " + itemAttributeValue.getAttributeValue());
		}

		/*
		 * for (ItemAttributeValue itemAttributeValue : item.getItemAttributeValues()) { itemAttributeValue.setItem(item); } if (item.getId() == null) {
		 * 
		 * this.itemService.save(item); } else { System.out.print("edit called"); this.itemService.edit(item); }
		 */
		m.addAttribute("item", new Item());
		return "item";
	}

	@RequestMapping(value = "/item", method = RequestMethod.POST)
	public String save(@Valid Item item, BindingResult result, HttpServletRequest request, Model m)
	{
		if (result.hasErrors())
		{
			return "item";
		}
		for (ItemAttributeValue itemAttributeValue : item.getItemAttributeValues())
		{
			itemAttributeValue.setItem(item);
		}
		if (item.getId() == null)
		{

			this.itemService.save(item);
		}
		else
		{
			System.out.print("edit called");
			this.itemService.edit(item);
		}

		m.addAttribute("item", new Item());
		return "item";
	}

	@RequestMapping(value = "/item/{id}")
	public String getItem(@PathVariable("id") Integer itemId, Model m)
	{
		m.addAttribute("item", this.itemService.getItem(itemId));
		m.addAttribute("itemTypeList", this.itemTypeService.getItemTypeList());
		m.addAttribute("itemList", this.itemService.getItemList());
		return "item";
	}

	@RequestMapping(value = "/itemDetails")
	public String loadItemDetailsPage(Model m)
	{
		return "itemDetails";
	}

	@RequestMapping(value = "/itemDetails", method = RequestMethod.POST)
	public String loadItemDetailsPageByItem(HttpServletRequest request, Model m)
	{
		String serialOrImei = (String) request.getParameter("serialOrImei");
		if (serialOrImei != null && !serialOrImei.isEmpty())
		{
			List<Object> objectList = this.itemService.getItemDetails(serialOrImei);
			m.addAttribute("item", objectList.get(0));
			m.addAttribute("slipList", objectList.get(1));
			m.addAttribute("serialOrImei", serialOrImei);
		}

		return "itemDetails";
	}

	@RequestMapping(value = "/itemCountByAttribute", method = RequestMethod.GET)
	public String loadItemCountByAttributePage(HttpServletRequest request, Model m)
	{

		m.addAttribute("itemSearchByAttribute", new ItemSearchByAttribute());
		m.addAttribute("itemTypeList", this.itemTypeService.getItemTypeList());
		return "itemCountByAttribute";
	}

	@RequestMapping(value = "/itemCountByAttribute/{itemTypeId}", method = RequestMethod.POST)
	public String getItemCountByAttributeTest(@RequestBody List<Attribute> attributeList, @PathVariable("itemTypeId") Integer itemTypeId, Model m)
	{
		for (Attribute attribute : attributeList)
		{
			System.out.println("itemTypeAttributeId = " + attribute.getAttributeId());
			System.out.println("itemAttributeValue = " + attribute.getAttributeValue());
		}

		Map<ItemStatus, Integer> itemStatusCountMap = this.itemService.getItemStatusCountByAttribute(attributeList, itemTypeId);
		m.addAttribute("itemStatusCountMap", itemStatusCountMap);
		return "item/itemCount";
	}

	@RequestMapping(value = "/itemList")
	public String getItemList()
	{
		return "itemList";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/itemList", method = RequestMethod.POST)
	public void searchData(HttpServletRequest request, HttpServletResponse response)
	{
		JSONObject jsonResult = new JSONObject();
		JSONArray dataArray = new JSONArray();
		Enumeration params = request.getParameterNames();
		while (params.hasMoreElements())
		{
			String paramName = (String) params.nextElement();
			System.out.println("Attribute Name - " + paramName + ", Value - " + request.getParameter(paramName));

		}
		String searchValue = request.getParameter("search[value]");
		System.out.println("searchValue = " + searchValue);

		int beginIndex = Integer.parseInt(request.getParameter("start"));
		int pageSize = Integer.parseInt(request.getParameter("length"));
		int draw = Integer.parseInt(request.getParameter("draw"));
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("globalSearchValue", searchValue);

		List<Object> resultList = this.itemService.getItemList(paramMap, beginIndex, pageSize);
		List<Item> items = (List<Item>) resultList.get(0);
		int recordsTotal = (int) resultList.get(1);
		System.out.println("recordstotal :" + recordsTotal);
		int recordsFiltered = (int) resultList.get(2);
		System.out.println("recordsfiltered: " + recordsFiltered);

		for (Item i : items)
		{
			JSONArray ja = new JSONArray();
			ja.put(i.getItemType().getName());
			ja.put(i.getSerial());
			ja.put(i.getItemStatus().toString());
			ja.put(i.getPurchaseDate());
			ja.put(i.getWarrantyDate());
			ja.put(i.getCustomerwarrantyDate());

			dataArray.put(ja);
		}

		try
		{
			jsonResult.put("recordsTotal", recordsTotal);
			jsonResult.put("recordsFiltered", recordsFiltered);
			jsonResult.put("data", dataArray);
			jsonResult.put("draw", draw);
		}
		catch (JSONException ex)
		{

		}

		response.setContentType("application/json");
		response.setHeader("Cache-Control", "no-store");
		PrintWriter out;
		try
		{
			out = response.getWriter();
			out.print(jsonResult);
		}
		catch (IOException ex)
		{

		}

	}



	@RequestMapping(value = "/export/deliveryReport", method = RequestMethod.GET)
	public ModelAndView getExcel(@RequestParam("itemTypeId") Integer itemTypeId, @RequestParam("recipientId") Integer recipientId)
	{
		/*
		 * Integer recipientId = new Integer(request.getParameter("recipient")); Integer itemTypeId = new Integer(request.getParameter("itemType"));
		 */
		List<DeliveryItem> deliveryItemList = this.itemService.getDeliveryReport(recipientId, itemTypeId);
		return new ModelAndView("DeliveryItemListExcel", "deliveryItemList", deliveryItemList);
	}

	/*@RequestMapping(value = "/show/deliveryReport", method = RequestMethod.POST)
	public String showDeliverySlipInfoReport(HttpServletRequest request, Model m)
	{
		Integer itemTypeId = new Integer(request.getParameter("itemType"));
		Integer recipientId = new Integer(request.getParameter("recipient"));
		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");
		Map<Object, Object> paramMap = new HashMap<>();
		paramMap.put("itemTypeId", itemTypeId);
		paramMap.put("recipientId", recipientId);
		paramMap.put("fromDate", fromDate);
		paramMap.put("toDate", toDate);
		List<Slip> deliverySlipInfoList = this.slipService.getDeliverySlips(paramMap);
		m.addAttribute("deliverySlipInfoList", deliverySlipInfoList);
		m.addAttribute("itemTypeId", itemTypeId);
		m.addAttribute("recipientId", recipientId);
		return getDeliveryReport(m);
	}*/

	@RequestMapping(value = "/addPerson", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	@ResponseBody
	public JsonResponse addPerson(@RequestBody Person person)
	{
		System.out.println("ajax post worked");
		System.out.println("1 " + person.getAge());
		System.out.println("2 " + person.getName());

		return new JsonResponse("OK", "");
	}
}

class Person
{
	private String name;
	private int age;
	private String city;

	// getters & setters ...
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getAge()
	{
		return age;
	}

	public void setAge(int age)
	{
		this.age = age;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}
}

class JsonResponse
{

	private String status = "";
	private String errorMessage = "";

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getErrorMessage()
	{
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}

	public JsonResponse(String status, String errorMessage)
	{
		this.status = status;
		this.errorMessage = errorMessage;
	}
}