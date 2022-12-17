package com.sencillo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sencillo.model.Company;
import com.sencillo.service.CompanyService;

@Controller
public class CompanyController
{

	@Autowired
	private CompanyService companyService;
	
	@RequestMapping(value="/company")
	public String loadCompanyPage(Model model, boolean loadNotRequired)
	{	
		Company company = null;
		if(!loadNotRequired)
		{
			company = this.companyService.getCompany(new Integer("1"));
		}		
		model.addAttribute("company", company);
		return "company";		
	}
	
	@RequestMapping(value="/company", method=RequestMethod.POST)
	public String saveCompany(@Valid Company company, BindingResult bindingResult, Model model)
	{		
		if(bindingResult.hasErrors())
		{
			return loadCompanyPage(model, true);
		}
		this.companyService.save(company);
		
		return "company";		
	}
}
