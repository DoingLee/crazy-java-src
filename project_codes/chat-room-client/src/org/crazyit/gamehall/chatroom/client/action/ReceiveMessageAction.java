package org.crazyit.gamehall.chatroom.client.action;

import org.crazyit.gamehall.chatroom.client.ClientContext;
import org.crazyit.gamehall.chatroom.client.ui.MainFrame;
import org.crazyit.gamehall.commons.ClientAction;
import org.crazyit.gamehall.commons.Response;

/**
 * 接收聊天内容的Action
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class ReceiveMessageAction implements ClientAction {

	public void execute(Response response) {
		String content = (String)response.getData("content");
		String senderName = (String)response.getData("senderName");
		MainFrame mainFrame = ClientContext.mainFrame;
		mainFrame.appendContent(senderName + " 对你说: " + content);
	}

}
