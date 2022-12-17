package com.sencillo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sencillo.dao.PageDao;
import com.sencillo.model.Page;

@Service
@Transactional
public class PageServiceImpl implements PageService
{
	@Autowired
	private PageDao pageDao;

	@Override
	public Page getPage(Integer id)
	{
		return this.pageDao.getPage(id);
	}

	@Override
	public void save(Page page)
	{
		this.pageDao.Save(page);
	}

	@Override
	public void edit(Page page)
	{
		this.pageDao.Edit(page);
	}

	@Override
	public List<Page> getPageList()
	{
		return this.pageDao.getPageList();
	}
	
	@Override
	public List<Page> getParentPageList()
	{
		return this.pageDao.getParentPageList();
	}
	
	@Override
	public List<Integer> getPageIdListByRole(Integer roleId)
	{
		return this.pageDao.getPageIdListByRole(roleId);
	}
	
	@Override
	public Map<Page, List<Page>> getPageListMapByRole(Integer id)
	{
		return this.pageDao.getPageListMapByRole(id);
	}

	@Override
	public Map<Page, List<Page>> getPageListMap()
	{
		return this.pageDao.getPageListMap();
	}

	@Override
	public void saveRolePage(Integer roleId, List<Integer> pageIds)
	{
		this.pageDao.saveRolePage(roleId, pageIds);		
	}
}
