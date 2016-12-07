package org.crazyit.transaction.service;

import java.util.List;

import org.crazyit.transaction.model.Role;

public interface RoleService {

	/**
	 * 查找全部的角色
	 * @return
	 */
	List<Role> getRoles();
}
