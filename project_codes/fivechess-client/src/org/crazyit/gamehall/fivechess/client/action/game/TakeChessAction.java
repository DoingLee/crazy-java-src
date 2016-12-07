package org.crazyit.gamehall.fivechess.client.action.game;

import org.crazyit.gamehall.commons.ClientAction;
import org.crazyit.gamehall.commons.Response;
import org.crazyit.gamehall.fivechess.client.ui.UIContext;
import org.crazyit.gamehall.fivechess.client.ui.game.GamePanel;

/**
 * 对手下棋了, 接收服务器发送的响应
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class TakeChessAction implements ClientAction {

	public void execute(Response response) {
		//得到参数
		Integer i = (Integer)response.getData("i");
		Integer j = (Integer)response.getData("j");
		String color = (String)response.getData("color");
		GamePanel gamePanel = (GamePanel)UIContext.modules.get(UIContext.GAME_PANEL);
		gamePanel.getChessArray()[i][j].setColor(color);
		gamePanel.myTurn();
	}

}
