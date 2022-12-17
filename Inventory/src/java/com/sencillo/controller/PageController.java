package com.sencillo.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sencillo.editors.PageEditor;
import com.sencillo.model.Page;
import com.sencillo.service.PageService;
import com.sencillo.service.RoleService;

@Controller
public class PageController
{
	@Autowired
	private PageService pageService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private PageEditor pageEditor;

	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		binder.registerCustomEditor(Page.class, this.pageEditor);
	}

	@RequestMapping(value = "/page")
	public String loadPage(Model m)
	{
		m.addAttribute("page", new Page());
		loadDefault(m);
		return "page";
	}

	public void loadDefault(Model m)
	{
		m.addAttribute("pageList", this.pageService.getPageList());
		m.addAttribute("parentPageList", this.pageService.getParentPageList());
	}

	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public String save(@Valid Page page, BindingResult bindingResult, Model m)
	{
		List<FieldError> errors = bindingResult.getFieldErrors();
		for (FieldError error : errors)
		{
			System.out.println(error.getObjectName() + " - " + error.getDefaultMessage());
		}
		if (bindingResult.hasErrors())
		{
			return "page";
		}
		if (page.getId() == null)
		{
			this.pageService.save(page);
		}
		else
		{
			this.pageService.edit(page);
		}
		m.addAttribute("page", new Page());
		loadDefault(m);
		/*
		 * m.addAttribute("pageList", this.pageService.getPageList()); m.addAttribute("parentPageList", this.pageService.getParentPageList());
		 */
		return "page";
	}

	@RequestMapping(value = "/page/{id}", method = RequestMethod.GET)
	public String getPage(@PathVariable("id") Integer id, Model model)
	{
		model.addAttribute("page", this.pageService.getPage(id));
		loadDefault(model);
		return "page";
	}

	@RequestMapping(value = "/rolePage")
	public String getPageAssignment(Model m)
	{
		List<Page> pageList = this.pageService.getPageList();
		List<Integer> allPageIds = new ArrayList<Integer>();
		for (Page page : pageList)
		{
			if (page.getParentPage() != null)
			{
				allPageIds.add(page.getId());
			}
		}
		m.addAttribute("allPageIds", allPageIds);
		m.addAttribute("pageListMap", this.pageService.getPageListMap());
		m.addAttribute("roleList", this.roleService.getRoleList());
		return "rolePage";
	}

	@RequestMapping(value = "/pageIdsByRole/{roleId}")
	public @ResponseBody
	List<Integer> getPageIdsByRole(@PathVariable("roleId") Integer roleId)
	{
		return this.pageService.getPageIdListByRole(roleId);
	}

	@RequestMapping(value = "/savePagesByRole", method = RequestMethod.POST)
	public @ResponseBody
	String savePagesByRole(@RequestBody PagesByRole pagesByRole)
	{
		Integer roleId = pagesByRole.getRoleId();
		List<Integer> pageIds = pagesByRole.getPageIds();
		System.out.println("roleID: " + roleId);
		for (Integer pageId : pageIds)
		{
			System.out.println("pageId: " + pageId);
		}
		this.pageService.saveRolePage(roleId, pageIds);	
		return "Saved Successfully";
	}
}

class PagesByRole
{
	Integer roleId;
	List<Integer> pageIds;

	public Integer getRoleId()
	{
		return roleId;
	}

	public void setRoleId(Integer roleId)
	{
		this.roleId = roleId;
	}

	public List<Integer> getPageIds()
	{
		return pageIds;
	}

	public void setPageIds(List<Integer> pageIds)
	{
		this.pageIds = pageIds;
	}

}
