package com.sencillo.service;

import java.util.List;

import com.sencillo.model.Region;

public interface RegionService
{
	public void save(Region region);
	
	public void edit(Region region);

	public Region getRegion(Integer id);
	
	public List<Region> getRegionList();
		
}
