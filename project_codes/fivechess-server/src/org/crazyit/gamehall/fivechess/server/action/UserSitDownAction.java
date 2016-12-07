package org.crazyit.gamehall.fivechess.server.action;

import java.net.Socket;

import org.crazyit.gamehall.commons.Request;
import org.crazyit.gamehall.commons.Response;
import org.crazyit.gamehall.commons.ServerAction;
import org.crazyit.gamehall.fivechess.commons.ChessUser;
import org.crazyit.gamehall.fivechess.commons.Seat;
import org.crazyit.gamehall.fivechess.commons.Table;
import org.crazyit.gamehall.fivechess.commons.util.FiveChessErrorCode;
import org.crazyit.gamehall.fivechess.server.ChessContext;
import org.crazyit.gamehall.util.XStreamUtil;

/**
 * 玩家坐下的Action
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class UserSitDownAction implements ServerAction {
	
	public void execute(Request request, Response response, Socket socket) {
		//得到桌子编号
		Integer tableNumber = (Integer)request.getParameter("tableNumber");
		String side = (String)request.getParameter("side");
		Table table = Table.getTable(tableNumber, ChessContext.tables);
		//得到刚坐下的玩家
		String userId = (String)request.getParameter("userId");
		ChessUser user = ChessContext.users.get(userId);
		//如果玩家已经在座位上, 则返回错误信息
		if (user.hasSitDown(ChessContext.tables)) {
			response.setErrorCode(FiveChessErrorCode.HAS_SIT_DOWN);
			user.getPrintStream().println(XStreamUtil.toXML(response));
			return;
		}
		//得到座位
		Seat seat = table.getSeat(side);
		//判断座位上是否有人
		if (seat.getUser() != null) {
			//座位上有人, 发送错误信息
			response.setErrorCode(FiveChessErrorCode.SEAT_HAS_USER);
			user.getPrintStream().println(XStreamUtil.toXML(response));
		} else {
			seat.setUser(user);
			//告诉所有的客户端, 刚坐下的玩家在哪张桌子哪个位置坐下了
			response.setData("tableNumber", table.getTableNumber());
			response.setData("side", side);
			response.setData("user", user.getId());
			//向所有玩家发送信息, 有新玩家坐下
			printResponse(user, response);
			//得到启动游戏的客户端类
			String beginClass = (String)request.getParameter("beginClass");
			response.setActionClass(beginClass);
			//告诉客户端, 需要启动游戏界面
			user.getPrintStream().println(XStreamUtil.toXML(response));
		}
	}
	
	/**
	 * 向所有玩家发送信息, 参数的user坐下了
	 * @param user
	 */
	private void printResponse(ChessUser user, Response response) {
		for (String id : ChessContext.users.keySet()) {
			ChessUser u = ChessContext.users.get(id);
			if (u.getId().equals(user.getId())) continue;
			u.getPrintStream().println(XStreamUtil.toXML(response));
		}
	}
	

}
