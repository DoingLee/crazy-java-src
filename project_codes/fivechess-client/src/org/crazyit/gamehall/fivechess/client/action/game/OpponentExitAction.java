package org.crazyit.gamehall.fivechess.client.action.game;

import org.crazyit.gamehall.commons.ClientAction;
import org.crazyit.gamehall.commons.Response;
import org.crazyit.gamehall.fivechess.client.ui.HallPanel;
import org.crazyit.gamehall.fivechess.client.ui.UIContext;
import org.crazyit.gamehall.fivechess.client.ui.game.ChessFrame;
import org.crazyit.gamehall.fivechess.commons.ChessUser;

/**
 * 对手退出游戏, 服务器告诉剩下的玩家, 对手退出了
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class OpponentExitAction implements ClientAction {

	public void execute(Response response) {
		//得到退出的玩家Id
		String exitUserId = (String)response.getData("userId");
		//得到大厅对象
		HallPanel hallPanel = (HallPanel)UIContext.modules.get(UIContext.HALL_PANEL);
		//得到退出的玩家
		ChessUser exitUser = hallPanel.getUser(exitUserId);
		//告诉没有退出的玩家, 让其刷新界面
		ChessFrame cf = (ChessFrame)UIContext.modules.get(UIContext.GAME_FRAME);
		cf.userExit(exitUser);
	}

}
