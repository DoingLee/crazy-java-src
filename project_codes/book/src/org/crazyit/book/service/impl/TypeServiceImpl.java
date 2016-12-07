package org.crazyit.book.service.impl;

import java.util.Collection;

import org.crazyit.book.dao.TypeDao;
import org.crazyit.book.dao.impl.TypeDaoImpl;
import org.crazyit.book.service.TypeService;
import org.crazyit.book.vo.Type;

/**
 * 书本种类业务实现类
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class TypeServiceImpl implements TypeService {

	private TypeDao dao;
	
	public TypeServiceImpl(TypeDao dao) {
		this.dao = dao;
	}
	
	public Collection<Type> query(String name) {
		return dao.findByName(name);
	}

	public Collection<Type> getAll() {
		return dao.find();
	}

	public Type add(Type type) {
		String id = dao.add(type);
		return get(id);
	}

	public Type update(Type type) {
		// TODO Auto-generated method stub
		String id = dao.update(type);
		return get(id);
	}
	
	public Type get(String id) {
		return dao.find(id);
	}

}
