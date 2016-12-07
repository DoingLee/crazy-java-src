package org.crazyit.gamehall.chatroom.server.action;

import java.net.Socket;

import org.crazyit.gamehall.chatroom.server.ChatContext;
import org.crazyit.gamehall.commons.Request;
import org.crazyit.gamehall.commons.Response;
import org.crazyit.gamehall.commons.ServerAction;
import org.crazyit.gamehall.commons.User;
import org.crazyit.gamehall.util.XStreamUtil;

/**
 * 用户发送信息的Action
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class UserSendAction implements ServerAction {

	public void execute(Request request, Response response, Socket socket) {
		String content = (String)request.getParameter("content");
		String receiverId = (String)request.getParameter("receiverId");
		String senderId = (String)request.getParameter("senderId");
		//得到发送人
		User sender = ChatContext.users.get(senderId);
		if ("all".equals(receiverId)) {
			//向所有人发
			sendToAll(content, sender, response);
		} else {
			//向一个人发
			sendToOne(content, receiverId, sender, response);
		}
	}
	
	//向全部人发送
	private void sendToAll(String content, User sender, Response response) {
		response.setData("senderName", sender.getName());
		for (String key : ChatContext.users.keySet()) {
			User user = ChatContext.users.get(key);
			if (user.getId() != sender.getId()) {
				user.getPrintStream().println(XStreamUtil.toXML(response));
			}
		}
	}
	
	//向具体的一个人发送
	private void sendToOne(String content, String receiverId, User sender, 
			Response response) {
		User receiver = ChatContext.users.get(receiverId);
		response.setData("senderName", sender.getName());
		receiver.getPrintStream().println(XStreamUtil.toXML(response));
	}

}
