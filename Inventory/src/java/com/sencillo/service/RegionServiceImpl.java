package com.sencillo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sencillo.dao.RegionDao;
import com.sencillo.model.Region;

@Service
@Transactional
public class RegionServiceImpl implements RegionService
{

	@Autowired
	private RegionDao regionDao;

	@Override
	public void save(Region region)
	{
		this.regionDao.save(region);

	}

	@Override
	public void edit(Region region)
	{
		this.regionDao.edit(region);

	}

	@Override
	public Region getRegion(Integer id)
	{
		return this.regionDao.getRegion(id);
	}

	@Override
	public List<Region> getRegionList()
	{
		return this.regionDao.getRegionList();
	}

}
