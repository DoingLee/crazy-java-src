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
 * 接收玩家拒绝求和的请求
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class RefuseDrawAction implements ServerAction {

	public void execute(Request request, Response response, Socket socket) {
		//得到发送拒绝信息的人
		String userId = (String)request.getParameter("userId");
		ChessUser user = ChessContext.users.get(userId);
		//得到桌子
		Integer tableNumber = (Integer)request.getParameter("tableNumber");
		Table table = Table.getTable(tableNumber, ChessContext.tables);
		ChessUser opponent = table.getAnotherUser(user);
		response.setData("userName", user.getName());
		opponent.getPrintStream().println(XStreamUtil.toXML(response));
	}

}
