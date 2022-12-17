package com.sencillo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sencillo.dao.RoleDao;
import com.sencillo.model.Role;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;

	public Role getRole(int id) {
		return roleDao.getRole(id);
	}

	@Override
	public void Save(Role role)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Edit(Role role)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Role> getRoleList()
	{
		return this.roleDao.getRoleList();
	}

}