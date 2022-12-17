package com.sencillo.forms;

import java.util.ArrayList;
import java.util.List;

import com.sencillo.model.Slip;


public class SlipApprovalForm {
	
	List<Slip> slips = new ArrayList<Slip>();

	public List<Slip> getSlips()
	{
		return slips;
	}

	public void setSlips(List<Slip> slips)
	{
		this.slips = slips;
	}

	
	
	
}
