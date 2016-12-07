package org.crazyit.transaction.ui.handler;

/**
 * 选择用户后的处理接口
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public interface UserSelectHandler {

	/**
	 * 在用户选择界面点击确定后执行的方法
	 * @param userId
	 * @param realName
	 */
	void confirm(String userId, String realName);
}
