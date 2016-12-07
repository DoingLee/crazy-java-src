package org.crazyit.transaction.service.impl;

import java.util.List;

import org.crazyit.transaction.dao.RoleDao;
import org.crazyit.transaction.model.Role;
import org.crazyit.transaction.service.RoleService;

public class RoleServiceImpl implements RoleService {

	private RoleDao roleDao;
	
	public RoleServiceImpl(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public List<Role> getRoles() {
		return this.roleDao.findRoles();
	}
	
	
}
