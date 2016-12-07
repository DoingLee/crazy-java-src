package org.crazyit.gamehall.fivechess.server.action;

import java.net.Socket;

import org.crazyit.gamehall.commons.Request;
import org.crazyit.gamehall.commons.Response;
import org.crazyit.gamehall.commons.ServerAction;
import org.crazyit.gamehall.fivechess.commons.ChessUser;
import org.crazyit.gamehall.fivechess.commons.Table;
import org.crazyit.gamehall.fivechess.server.ChessContext;
import org.crazyit.gamehall.util.XStreamUtil;

/**
 * 在游戏中发送聊天信息, 服务器处理类
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class GameMessageAction implements ServerAction {

	public void execute(Request request, Response response, Socket socket) {
		String senderId = (String)request.getParameter("senderId");
		ChessUser sender = ChessContext.users.get(senderId);
		String content = (String)request.getParameter("content");
		//得到发送人所在的桌子
		Table table = ChessContext.getTable(senderId);
		if (table != null ) {
			//向对手发送
			ChessUser receiver = table.getAnotherUser(sender);
			if (receiver != null) {
				response.setData("content", sender.getName() + " 对你说：" + content);
				receiver.getPrintStream().println(XStreamUtil.toXML(response));
			}
		}
	}

}
