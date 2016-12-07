package org.crazyit.gamehall.fivechess.server.action;

import java.net.Socket;

import org.crazyit.gamehall.commons.Request;
import org.crazyit.gamehall.commons.Response;
import org.crazyit.gamehall.commons.ServerAction;
import org.crazyit.gamehall.fivechess.commons.ChessUser;
import org.crazyit.gamehall.fivechess.server.ChessContext;
import org.crazyit.gamehall.util.XStreamUtil;

/**
 * 第二个玩家进入游戏, 向第一个玩家发送信息
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class OpponentEnterAction implements ServerAction {

	public void execute(Request request, Response response, Socket socket) {
		//得到第一个玩家的对象(发送请求的人的对手)
		String firstUserId = (String)request.getParameter("firstUserId");
		ChessUser firstUser = ChessContext.users.get(firstUserId);
		//得到第二个玩家的对象
		String secondUserId = (String)request.getParameter("secondUserId");
		response.setData("opponentId", secondUserId);
		//告诉第一个进入游戏的玩家, 有对手进入
		firstUser.getPrintStream().println(XStreamUtil.toXML(response));
	}

}
