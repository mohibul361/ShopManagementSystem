package com.sencillo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ErrorsController {

	@RequestMapping(value="/404", method = RequestMethod.GET)
	public String handle404Error(Model model)
	{
		System.out.println("ERROR!");	
		return "404";
	}
	@RequestMapping(value="/500", method = RequestMethod.GET)
	public String handle500Error(Model model)
	{
		System.out.println("ERROR 500!");	
		return "500";
	}
}
