package org.crazyit.book.dao;

import org.crazyit.book.vo.User;

public interface UserDao {

	User getUser(String name, String password);
}
