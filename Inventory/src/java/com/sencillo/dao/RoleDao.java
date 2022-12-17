package com.sencillo.dao;

import java.util.List;

import com.sencillo.model.Role;

public interface RoleDao
{
	public Role getRole(int id);

	public void Save(Role role);

	public void Edit(Role role);

	public List<Role> getRoleList();
}
