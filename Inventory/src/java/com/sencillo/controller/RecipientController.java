package com.sencillo.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sencillo.enums.RecipientType;
import com.sencillo.enums.SlipType;
import com.sencillo.model.Recipient;
import com.sencillo.model.Vendor;
import com.sencillo.service.RecipientService;

@Controller
public class RecipientController
{

	@Autowired
	private RecipientService recipientService;

	@RequestMapping(value = "/recipient")
	public String loadPage(Model m)
	{
		// m.addAttribute("recipientTypes", RecipientType.values());
		m.addAttribute("recipient", new Recipient());
		return "recipient";
	}

	@ModelAttribute(value = "recipientList")
	private void loadRecipientList(Model model)
	{
		model.addAttribute("recipientList", this.recipientService.getRecipientList());
	}

	@RequestMapping(value = "/recipient/list", method = RequestMethod.GET)
	private String getRecipientList(Model model)
	{
		return "recipientList";
	}
	
	@RequestMapping(value = "/recipient", method = RequestMethod.POST)
	public String save(@Valid Recipient recipient, BindingResult bindingResult, Model model)
	{
		List<FieldError> errors = bindingResult.getFieldErrors();
		for (FieldError error : errors)
		{
			System.out.println(error.getObjectName() + " - " + error.getDefaultMessage());
		}
		if (bindingResult.hasErrors())
		{
			// model.addAttribute("recipientTypes", RecipientType.values());
			return "recipient";
		}
		if (recipient.getId() == null)
		{
			this.recipientService.save(recipient);
		} else
		{
			this.recipientService.edit(recipient);
		}
		// model.addAttribute("recipientTypes", RecipientType.values());
		model.addAttribute("recipient", new Recipient());
		model.addAttribute("msg", "সফলভাবে সংরক্ষিত হয়েছে।");
		return "recipient";
	}

	@RequestMapping(value = "/recipient/{id}", method = RequestMethod.GET)
	public String getrecipient(@PathVariable("id") Integer id, Model model)
	{
		// model.addAttribute("recipientTypes", RecipientType.values());
		model.addAttribute("recipient", this.recipientService.getRecipient(id));
		return "recipient";
	}

	@RequestMapping(value = "/recipientListByType/{recipientType}/{recipientId}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public @ResponseBody
	String getRecipientListByType(@PathVariable("recipientType") RecipientType recipientType,
			@PathVariable("recipientId") Integer recipientId, Locale locale)
	{
		String returnText = "";

		recipientType = (recipientType.equals(RecipientType.WHOLESALER)) ? RecipientType.WHOLESALER
				: RecipientType.RETAILER;

		List<Recipient> recipientList = this.recipientService.getRecipientListByType(recipientType);
		if (locale.getLanguage().equals("bn"))
		{
			returnText += "<option value=0>--একটি নির্বাচন করুন--</option>";
		} else
		{
			returnText += "<option value=0>--Select One--</option>";
		}

		for (Recipient recipient : recipientList)
		{
			if (recipientId == recipient.getId())
			{
				returnText += String.format("<option value='%s' selected='selected'>%s</option>", recipient.getId(),
						recipient.getName());
			} else
			{
				returnText += String.format("<option value='%s'>%s</option>", recipient.getId(), recipient.getName());
			}

		}
		returnText += "</select>";
		return returnText;
	}

	@RequestMapping(value = "/recipient/delete/{recipientId}", method = RequestMethod.GET)
	public String deleteRecipient(@PathVariable("recipientId") Integer recipientId, Model m) throws Exception
	{
		if (recipientId != null)
		{
			this.recipientService.delete(recipientId);
		}

		return "redirect:/recipient";
	}

	/**
	 * THis method will be used to show recipient list in pop up
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/recipientList")
	public String getRecipients(Model model)
	{
		model.addAttribute("recipientList", this.recipientService.getRecipientList());
		return "recipient/list";
	}

	/**
	 * This method will be called from Recipient Search POP Up when a recipient will be selected
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/recipientByAjax/{id}")
	@ResponseBody
	public Recipient getRecipient(@PathVariable("id") Integer id)
	{		
		Recipient recipient = this.recipientService.getRecipient(id);
		return recipient;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/recipient/list", method = RequestMethod.POST)
	public void searchVendorData(HttpServletRequest request, HttpServletResponse response) throws ParseException
	{
		JSONObject jsonResult = new JSONObject();
		JSONArray dataArray = new JSONArray();

		Enumeration params = request.getParameterNames();
		while (params.hasMoreElements())
		{
			String paramName = (String) params.nextElement();
			System.out.println("Attribute Name - " + paramName + ", Value - " + request.getParameter(paramName));
		}

		Map paramMap = new HashMap();

		System.out.print("00: " + request.getParameterMap().get("columns[2][search][value]").toString());

		paramMap.put("globalSearchValue", request.getParameter("search[value]"));

		int beginIndex = Integer.parseInt(request.getParameter("start"));
		int pageSize = Integer.parseInt(request.getParameter("length"));
		int draw = Integer.parseInt(request.getParameter("draw"));

		List<Object> resultList = this.recipientService.getRecipientList(paramMap, beginIndex, pageSize);
		List<Recipient> slips = (List<Recipient>) resultList.get(0);
		int recordsTotal = (int) resultList.get(1);
		int recordsFiltered = (int) resultList.get(2);
		String link = "";

		for (Recipient s : slips)
		{
			JSONArray ja = new JSONArray();
			ja.put(s.getName());
			ja.put(s.getMobileNo1());
			ja.put(s.getMobileNo2());
			ja.put(s.getAddress());
			link = String.format("<a href=%s/recipient/%s>এডিট</a>", request.getContextPath(), s.getId())
					+ String.format("&nbsp;&nbsp;|&nbsp;&nbsp;<a href=# onclick=deleteRecipient(%s)>ডিলিট</a>",
							s.getId());
			ja.put(link);
			dataArray.put(ja);
		}

		try
		{
			jsonResult.put("recordsTotal", recordsTotal);
			jsonResult.put("recordsFiltered", recordsFiltered);
			jsonResult.put("data", dataArray);
			jsonResult.put("draw", draw);
		} catch (JSONException ex)
		{

		}

		response.setContentType("application/json");
		response.setHeader("Cache-Control", "no-store");
		PrintWriter out;
		try
		{
			out = response.getWriter();
			out.print(jsonResult);
		} catch (IOException ex)
		{

		}

	}
}
