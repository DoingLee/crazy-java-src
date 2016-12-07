package org.crazyit.gamehall.fivechess.server.action;

import java.net.Socket;

import org.crazyit.gamehall.commons.Request;
import org.crazyit.gamehall.commons.Response;
import org.crazyit.gamehall.commons.ServerAction;
import org.crazyit.gamehall.fivechess.commons.ChessUser;
import org.crazyit.gamehall.fivechess.commons.Seat;
import org.crazyit.gamehall.fivechess.commons.Table;
import org.crazyit.gamehall.fivechess.server.ChessContext;
import org.crazyit.gamehall.util.XStreamUtil;

/**
 * 玩家离开游戏时服务器执行的Action
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class LeaveGameAction implements ServerAction {

	public void execute(Request request, Response response, Socket socket) {
		Integer tableNumber = (Integer)request.getParameter("tableNumber");
		String userId = (String)request.getParameter("userId");
		//得到离开的玩家
		ChessUser user = ChessContext.users.get(userId);
		user.setReady(false);
		//得到桌子
		Table table = Table.getTable(tableNumber, ChessContext.tables);
		//得到玩家的座位
		Seat seat = table.getUserSeat(user);
		//设置座位的玩家为空
		seat.setUser(null);
		response.setData("side", seat.getSide());
		//发送服务器响应, 告诉所有玩家, 该玩家退出
		printResponse(response);
		
		//告诉退出玩家的对手, 他退出了
		Seat otherSeat = table.getAnotherSeat(seat);
		if (otherSeat.getUser() != null) {
			String exitAction = (String)request.getParameter("exitAction");
			response.setActionClass(exitAction);
			otherSeat.getUser().getPrintStream().println(XStreamUtil.toXML(response));
		}
	}
	
	private void printResponse(Response response) {
		for (String id : ChessContext.users.keySet()) {
			ChessUser u = ChessContext.users.get(id);
			u.getPrintStream().println(XStreamUtil.toXML(response));
		}
	}

}
