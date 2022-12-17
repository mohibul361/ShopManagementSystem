package com.sencillo.dao;

import java.util.List;

import com.sencillo.model.Region;

public interface RegionDao
{
	public void save(Region region);

	public void edit(Region region);

	public Region getRegion(Integer id);

	public List<Region> getRegionList();
}
