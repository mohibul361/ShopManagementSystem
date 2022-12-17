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

import com.sencillo.model.ProblemType;
import com.sencillo.service.ProblemTypeService;

@Controller
public class ProblemTypeController
{

	@Autowired 
	private ProblemTypeService problemTypeService;
	
	@RequestMapping(value="/problemType")
	public String loadPage(Model m)
	{
		m.addAttribute("problemType", new ProblemType());
		return "problemType";
	}
	
	@ModelAttribute(value = "problemTypeList")
	private void loadProblemTypeList(Model model)
	{
		model.addAttribute("problemTypeList", this.problemTypeService.getProblemTypeList());	
	}

	@RequestMapping(value="/problemType", method=RequestMethod.POST)
	public String save(@Valid ProblemType problemType, BindingResult bindingResult, Model model)
	{
		List<FieldError> errors = bindingResult.getFieldErrors();
		for (FieldError error : errors)
		{
			System.out.println(error.getObjectName() + " - " + error.getDefaultMessage());
		}
		if(bindingResult.hasErrors())
		{
			return "problemType";
		}
		if(problemType.getId() == null)
		{
			this.problemTypeService.save(problemType);
		}
		else
		{
			this.problemTypeService.edit(problemType);
		}
		model.addAttribute("problemType", new ProblemType());
		return "problemType";
	}
	@RequestMapping(value = "/problemType/{id}", method = RequestMethod.GET)
	public String getProblemType(@PathVariable("id") Integer id, Model model)
	{
		model.addAttribute("problemType", this.problemTypeService.getProblemType(id));		
		return "problemType";
	}
	
}
