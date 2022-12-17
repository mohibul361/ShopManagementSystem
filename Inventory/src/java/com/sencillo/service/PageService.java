package com.sencillo.service;

import java.util.List;
import java.util.Map;

import com.sencillo.model.Page;

public interface PageService
{
	public Page getPage(Integer id);

	public void save(Page page);

	public void edit(Page page);

	public List<Page> getPageList();
	
	public List<Page> getParentPageList();
	
	public List<Integer> getPageIdListByRole(Integer roleId);
	
	public Map<Page, List<Page>> getPageListMapByRole(Integer id);
	
	public Map<Page, List<Page>> getPageListMap();
	
	public void saveRolePage(Integer roleId, List<Integer> pageIds);
}
