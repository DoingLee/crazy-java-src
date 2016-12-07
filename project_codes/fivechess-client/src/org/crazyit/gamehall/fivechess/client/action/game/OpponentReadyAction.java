package org.crazyit.gamehall.fivechess.client.action.game;

import org.crazyit.gamehall.commons.ClientAction;
import org.crazyit.gamehall.commons.Response;
import org.crazyit.gamehall.fivechess.client.ui.UIContext;
import org.crazyit.gamehall.fivechess.client.ui.game.GamePanel;
import org.crazyit.gamehall.fivechess.commons.ChessUser;

/**
 * 对手准备好, 接收服务器响应的Action
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class OpponentReadyAction implements ClientAction {

	
	public void execute(Response response) {
		GamePanel gamePanel = (GamePanel)UIContext.modules.get(UIContext.GAME_PANEL);
		//得到对手
		ChessUser opponent = gamePanel.getOtherChessUser();
		if (opponent != null) {
			opponent.setReady(true);
		}
		gamePanel.repaint();
	}

}
