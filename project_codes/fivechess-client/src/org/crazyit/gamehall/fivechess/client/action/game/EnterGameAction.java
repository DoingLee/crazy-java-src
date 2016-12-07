package org.crazyit.gamehall.fivechess.client.action.game;

import org.crazyit.gamehall.commons.ClientAction;
import org.crazyit.gamehall.commons.Request;
import org.crazyit.gamehall.commons.Response;
import org.crazyit.gamehall.fivechess.client.ChessClientContext;
import org.crazyit.gamehall.fivechess.client.ui.HallPanel;
import org.crazyit.gamehall.fivechess.client.ui.UIContext;
import org.crazyit.gamehall.fivechess.client.ui.game.ChessFrame;
import org.crazyit.gamehall.fivechess.commons.Seat;
import org.crazyit.gamehall.fivechess.commons.Table;
import org.crazyit.gamehall.util.XStreamUtil;

/**
 * 进入五子棋游戏的Action
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class EnterGameAction implements ClientAction {

	
	public void execute(Response response) {
		HallPanel hallPanel = (HallPanel)UIContext.modules.get(UIContext.HALL_PANEL);
		//从服务器呼应中得到桌子编号
		Integer tableNumber = (Integer)response.getData("tableNumber");
		String side = (String)response.getData("side");
		//根据桌子编号得到桌子信息
		Table table = Table.getTable(tableNumber, hallPanel.getTables());
		//显示界面
		ChessFrame cf = new ChessFrame(table, ChessClientContext.chessUser);
		cf.setVisible(true);
		//告诉对方进入游戏(如果有对方玩家的话)
		Seat seat = table.getSeat(side);
		//得到对方座位
		Seat otherSeat = table.getAnotherSeat(seat);
		if (otherSeat.getUser() != null) {
			//有对手, 向服务器发送请求
			Request request = new Request(
					"org.crazyit.gamehall.fivechess.server.action.OpponentEnterAction", 
					"org.crazyit.gamehall.fivechess.client.action.game.OpponentEnterAction");
			//firstUserId是对手的ID(第一个进入游戏的玩家)
			request.setParameter("firstUserId", otherSeat.getUser().getId());
			//secondUserId是自己的ID(后进入游戏的玩家)
			request.setParameter("secondUserId", seat.getUser().getId());
			ChessClientContext.chessUser.getPrintStream().println(XStreamUtil.toXML(request));
		}
	}

}
