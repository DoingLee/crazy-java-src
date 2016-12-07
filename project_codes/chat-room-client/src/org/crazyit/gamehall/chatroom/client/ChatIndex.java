package org.crazyit.gamehall.chatroom.client;

import org.crazyit.gamehall.commons.Game;
import org.crazyit.gamehall.commons.Request;
import org.crazyit.gamehall.commons.User;
import org.crazyit.gamehall.util.XStreamUtil;

public class ChatIndex implements Game {

	public void start(User user) {
		ClientContext.user = user;
		//进入聊天室, 告诉服务器用户有用户进入
		Request request = new Request("org.crazyit.gamehall.chatroom.server.action.UserInAction", 
				"org.crazyit.gamehall.chatroom.client.action.StartAction");
		request.setParameter("userInAction", 
				"org.crazyit.gamehall.chatroom.client.action.NewUserInAction");
		request.setParameter("user", user);
		user.getPrintStream().println(XStreamUtil.toXML(request));
	}
	
	public String toString() {
		return "测试聊天室";
	}

}
