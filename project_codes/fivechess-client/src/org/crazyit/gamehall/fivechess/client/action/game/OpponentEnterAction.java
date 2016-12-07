package org.crazyit.gamehall.fivechess.client.action.game;

import org.crazyit.gamehall.commons.ClientAction;
import org.crazyit.gamehall.commons.Response;
import org.crazyit.gamehall.fivechess.client.ui.ChatPanel;
import org.crazyit.gamehall.fivechess.client.ui.HallPanel;
import org.crazyit.gamehall.fivechess.client.ui.UIContext;
import org.crazyit.gamehall.fivechess.client.ui.UserTable;
import org.crazyit.gamehall.fivechess.client.ui.game.ChessFrame;
import org.crazyit.gamehall.fivechess.client.ui.game.GamePanel;
import org.crazyit.gamehall.fivechess.commons.ChessUser;

/**
 * 对手进入游戏, 由本类接收对手进入游戏的信息(服务器发送), 这里说的对手是指后进入游戏的玩家
 * 该Action由第一个进入游戏的玩家接收
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class OpponentEnterAction implements ClientAction {

	public void execute(Response response) {
		//得到大厅对象
		HallPanel gameHall = (HallPanel)UIContext.modules.get(UIContext.HALL_PANEL);
		
		//得到对手的ChessUser对象
		String opponentId = (String)response.getData("opponentId");
		//从大厅中得到对手的信息
		ChessUser opponent = gameHall.getUser(opponentId);
		ChessFrame cf = (ChessFrame)UIContext.modules.get(UIContext.GAME_FRAME);
		cf.newUserIn(opponent);

	}

}
