package com.sencillo.dao;

import java.util.List;

import com.sencillo.model.ProblemType;

public interface ProblemTypeDao
{
	public void save(ProblemType problemType);

	public void edit(ProblemType problemType);

	public ProblemType getProblemType(Integer id);

	public List<ProblemType> getProblemTypeList();
}
