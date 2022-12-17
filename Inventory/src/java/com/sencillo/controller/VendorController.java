package com.sencillo.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
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

import com.sencillo.enums.SlipType;
import com.sencillo.model.Slip;
import com.sencillo.model.Vendor;
import com.sencillo.service.VendorService;

@Controller
public class VendorController {

	@Autowired
	private VendorService vendorService;
	
	@RequestMapping(value="/vendor")
	public String loadPage(Model m)
	{
		m.addAttribute("vendor", new Vendor());
		return "vendor";
	}
	
	@ModelAttribute(value = "vendorList")
	private void loadVendorList(Model model)
	{
		model.addAttribute("vendorList", this.vendorService.getVendorList());	
	}
	
	@RequestMapping(value="/vendor/list", method=RequestMethod.GET)
	public String getVendorList(Model model)
	{		
		return "vendorList";
	}

	@RequestMapping(value="/vendor", method=RequestMethod.POST)
	public String save(@Valid Vendor vendor, BindingResult bindingResult, Model model)
	{
		List<FieldError> errors = bindingResult.getFieldErrors();
		for (FieldError error : errors)
		{
			System.out.println(error.getObjectName() + " - " + error.getDefaultMessage());
		}
		if(bindingResult.hasErrors())
		{
			return "vendor";
		}
		if(vendor.getId() == null)
		{
			this.vendorService.save(vendor);
		}
		else
		{
			this.vendorService.edit(vendor);
		}
		model.addAttribute("vendor", new Vendor());
		model.addAttribute("msg", "সফলভাবে সংরক্ষিত হয়েছে।");
		return "vendor";
	}
	@RequestMapping(value = "/vendor/{id}", method = RequestMethod.GET)
	public String getVendor(@PathVariable("id") Integer id, Model model)
	{
		model.addAttribute("vendor", this.vendorService.getVendor(id));		
		return "vendor";
	}
	
	@RequestMapping(value = "/vendor/delete/{vendorId}", method = RequestMethod.GET)
	public String deleteVendor(@PathVariable("vendorId") Integer vendorId, Model m) throws Exception
	{
		if (vendorId != null)
		{
			this.vendorService.delete(vendorId);
		}

		return "redirect:/vendor";
	}
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/vendor/list", method = RequestMethod.POST)
	public void searchVendorData(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		JSONObject jsonResult = new JSONObject();
		JSONArray dataArray = new JSONArray();

		Enumeration params = request.getParameterNames();
		while (params.hasMoreElements()) {
			String paramName = (String) params.nextElement();
			System.out.println("Attribute Name - " + paramName + ", Value - " + request.getParameter(paramName));
		}

		Map paramMap = new HashMap();

		System.out.print("00: " + request.getParameterMap().get("columns[2][search][value]").toString());

		paramMap.put("globalSearchValue", request.getParameter("search[value]"));


		int beginIndex = Integer.parseInt(request.getParameter("start"));
		int pageSize = Integer.parseInt(request.getParameter("length"));
		int draw = Integer.parseInt(request.getParameter("draw"));

		List<Object> resultList = this.vendorService.getVendorList(paramMap, beginIndex, pageSize);
		List<Vendor> slips = (List<Vendor>) resultList.get(0);
		int recordsTotal = (int) resultList.get(1);
		int recordsFiltered = (int) resultList.get(2);
		String link = "";
		
		for (Vendor s : slips) {
			JSONArray ja = new JSONArray();
			ja.put(s.getName());
			ja.put(s.getMobileNo1());
			ja.put(s.getMobileNo2());			
			ja.put(s.getAddress());
			link = String.format("<a href=%s/vendor/%s>এডিট</a>", request.getContextPath(), s.getId()) + String.format("&nbsp;&nbsp;|&nbsp;&nbsp;<a href=# onclick=deleteVendor(%s)>ডিলিট</a>", s.getId());
			ja.put(link);
			dataArray.put(ja);
		}

		try {
			jsonResult.put("recordsTotal", recordsTotal);
			jsonResult.put("recordsFiltered", recordsFiltered);
			jsonResult.put("data", dataArray);
			jsonResult.put("draw", draw);
		} catch (JSONException ex) {

		}

		response.setContentType("application/json");
		response.setHeader("Cache-Control", "no-store");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(jsonResult);
		} catch (IOException ex) {

		}

	}
}
