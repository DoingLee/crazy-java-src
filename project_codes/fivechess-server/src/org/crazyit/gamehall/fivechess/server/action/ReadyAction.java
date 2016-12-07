package org.crazyit.gamehall.fivechess.server.action;

import java.net.Socket;

import org.crazyit.gamehall.commons.Request;
import org.crazyit.gamehall.commons.Response;
import org.crazyit.gamehall.commons.ServerAction;
import org.crazyit.gamehall.fivechess.commons.Chess;
import org.crazyit.gamehall.fivechess.commons.ChessUser;
import org.crazyit.gamehall.fivechess.commons.Seat;
import org.crazyit.gamehall.fivechess.commons.Table;
import org.crazyit.gamehall.fivechess.server.ChessContext;
import org.crazyit.gamehall.util.XStreamUtil;

/**
 * 玩家准备游戏
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class ReadyAction implements ServerAction {
	
	public void execute(Request request, Response response, Socket socket) {
		//得到准备游戏的玩家
		String userId = (String)request.getParameter("userId");
		//得到桌子编号
		Integer tableNumber = (Integer)request.getParameter("tableNumber");
		//得到玩家
		ChessUser user = ChessContext.users.get(userId);
		user.setReady(true);
		//判断对方是否已经准备游戏
		//得到桌子对象
		Table table = Table.getTable(tableNumber, ChessContext.tables);
		Seat seat = table.getUserSeat(user);
		//得到对手
		ChessUser opponent = table.getAnotherSeat(seat).getUser();
		if (opponent != null) {
			//对面座位有人, 再判断对手是否已经准备好了
			if (opponent.isReady()) {
				//创建棋盘数组
				createChessArray(table);
				//向双方玩家发送响应, 游戏开始
				opponent.getPrintStream().println(XStreamUtil.toXML(response));
				user.getPrintStream().println(XStreamUtil.toXML(response));
			}
			//告诉对手, 自己准备好了, 使用接收准备的客户端处理类，该处理类由对手执行
			String opponentAction = (String)request.getParameter("opponentAction");
			response.setActionClass(opponentAction);
			response.setData("userId", userId);
			opponent.getPrintStream().println(XStreamUtil.toXML(response));
		}
	}
	
	private void createChessArray(Table table) {
		//对手已经准备好了, 在服务器中创建新数组
		Chess[][] newChess = new Chess[Chess.CHESS_BOARD_X_SIZE][Chess.CHESS_BOARD_Y_SYZE];
		for (int i = 0; i < newChess.length; i++) {
			for (int j = 0; j < newChess[i].length; j++) {
				Chess c = new Chess(i, j, null);
				newChess[i][j] = c;
			}
		}
		//将创建的棋盘数组放到服务器的上下文中
		ChessContext.tableChesses.put(table.getTableNumber(), newChess);
	}

}
