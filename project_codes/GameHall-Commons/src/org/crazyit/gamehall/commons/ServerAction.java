package org.crazyit.gamehall.commons;

import java.net.Socket;

/**
 * 服务器处理请求的接口
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public interface ServerAction {

	void execute(Request request, Response response, Socket socket);
}
