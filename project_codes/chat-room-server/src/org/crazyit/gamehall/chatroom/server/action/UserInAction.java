package org.crazyit.gamehall.chatroom.server.action;

import java.net.Socket;

import org.crazyit.gamehall.chatroom.server.ChatContext;
import org.crazyit.gamehall.commons.Request;
import org.crazyit.gamehall.commons.Response;
import org.crazyit.gamehall.commons.ServerAction;
import org.crazyit.gamehall.commons.User;
import org.crazyit.gamehall.util.XStreamUtil;

/**
 * 用户进入聊天室的服务器处理类
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class UserInAction implements ServerAction {

	public void execute(Request request, Response response, Socket socket) {
		//得到新进入聊天室的用户
		User user = (User)request.getParameter("user");
		//设置用户的Socet
		user.setSocket(socket);
		//放到上下文中
		ChatContext.users.put(user.getId(), user);
		//告诉原来的用户, 启动聊天室, 并发送所有的用户信息
		response.setData("users", ChatContext.users);
		user.getPrintStream().println(XStreamUtil.toXML(response));
		//告诉全部用户, 有新用户进入
		String receiveUserInAction = (String)request.getParameter("userInAction");
		//让客户端处理有新人进入聊天室的Action
		response.setActionClass(receiveUserInAction);
		response.setData("newUser", user);
		//向所有用户发送
		for (String key : ChatContext.users.keySet()) {
			User u = ChatContext.users.get(key);
			if (u.getId() != user.getId()) {
				u.getPrintStream().println(XStreamUtil.toXML(response));
			}
		}
	}

}
