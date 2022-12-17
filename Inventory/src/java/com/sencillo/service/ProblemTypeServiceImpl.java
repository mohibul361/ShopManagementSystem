package com.sencillo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sencillo.dao.ProblemTypeDao;
import com.sencillo.model.ProblemType;

@Service
@Transactional
public class ProblemTypeServiceImpl implements ProblemTypeService
{
	@Autowired
	private ProblemTypeDao problemTypeDao;

	@Override
	public void save(ProblemType problemType)
	{
		this.problemTypeDao.save(problemType);
	}

	@Override
	public void edit(ProblemType problemType)
	{
		this.problemTypeDao.edit(problemType);
	}

	@Override
	public ProblemType getProblemType(Integer id)
	{
		return this.problemTypeDao.getProblemType(id);
	}

	@Override
	public List<ProblemType> getProblemTypeList()
	{
		return this.problemTypeDao.getProblemTypeList();
	}

}
