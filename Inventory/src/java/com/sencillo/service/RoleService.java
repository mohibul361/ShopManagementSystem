package com.sencillo.service;

import java.util.List;

import com.sencillo.model.Role;

public interface RoleService
{
	public Role getRole(int id);

	public void Save(Role role);

	public void Edit(Role role);

	public List<Role> getRoleList();
}
