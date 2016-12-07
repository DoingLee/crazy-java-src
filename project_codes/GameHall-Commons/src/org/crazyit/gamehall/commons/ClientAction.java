package org.crazyit.gamehall.commons;

import java.net.Socket;

/**
 * 客户端处理服务器响应的接口
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public interface ClientAction {

	void execute(Response response);
}
