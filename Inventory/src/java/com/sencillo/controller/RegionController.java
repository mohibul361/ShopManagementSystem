package com.sencillo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sencillo.model.Region;
import com.sencillo.service.RegionService;

@Controller
public class RegionController
{

	@Autowired 
	private RegionService regionService;
	
	@RequestMapping(value="/region")
	public String loadPage(Model m)
	{
		m.addAttribute("region", new Region());
		return "region";
	}
	
	@ModelAttribute(value = "regionList")
	private void loadRegionList(Model model)
	{
		model.addAttribute("regionList", this.regionService.getRegionList());	
	}

	@RequestMapping(value="/region", method=RequestMethod.POST)
	public String save(@Valid Region region, BindingResult bindingResult, Model model)
	{
		List<FieldError> errors = bindingResult.getFieldErrors();
		for (FieldError error : errors)
		{
			System.out.println(error.getObjectName() + " - " + error.getDefaultMessage());
		}
		if(bindingResult.hasErrors())
		{
			return "region";
		}
		if(region.getId() == null)
		{
			this.regionService.save(region);
		}
		else
		{
			this.regionService.edit(region);
		}
		model.addAttribute("region", new Region());
		return "region";
	}
	@RequestMapping(value = "/region/{id}", method = RequestMethod.GET)
	public String getRegion(@PathVariable("id") Integer id, Model model)
	{
		model.addAttribute("region", this.regionService.getRegion(id));		
		return "region";
	}
	
}
