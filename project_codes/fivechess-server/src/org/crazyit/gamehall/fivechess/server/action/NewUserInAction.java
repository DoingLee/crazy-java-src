package org.crazyit.gamehall.fivechess.server.action;

import java.net.Socket;

import org.crazyit.gamehall.commons.Request;
import org.crazyit.gamehall.commons.Response;
import org.crazyit.gamehall.commons.ServerAction;
import org.crazyit.gamehall.fivechess.commons.ChessUser;
import org.crazyit.gamehall.fivechess.server.ChessContext;
import org.crazyit.gamehall.util.XStreamUtil;

/**
 * 接收玩家进入时发送的请求, 该Action向所有玩家发送信息, 有新玩家进入
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class NewUserInAction implements ServerAction {
	
	public void execute(Request request, Response response, Socket socket) {
		//得到新登录的玩家
		String userId = (String)request.getParameter("userId");
		ChessUser user = ChessContext.users.get(userId);
		//将新玩家信息放到响应中
		response.setData("newUser", user);
		//向所有玩家发送信息
		for (String id : ChessContext.users.keySet()) {
			ChessUser hasLogin = ChessContext.users.get(id);
			//不必发送给自己
			if (id.equals(user.getId())) continue;
			hasLogin.getPrintStream().println(XStreamUtil.toXML(response));
		}
	}

}
