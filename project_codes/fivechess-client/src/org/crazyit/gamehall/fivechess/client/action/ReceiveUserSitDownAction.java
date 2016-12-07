package org.crazyit.gamehall.fivechess.client.action;

import org.crazyit.gamehall.commons.ClientAction;
import org.crazyit.gamehall.commons.Response;
import org.crazyit.gamehall.fivechess.client.ui.HallPanel;
import org.crazyit.gamehall.fivechess.client.ui.UIContext;
import org.crazyit.gamehall.fivechess.commons.util.FiveChessErrorCode;

/**
 * 接收玩家坐下的Action
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class ReceiveUserSitDownAction implements ClientAction {

	
	public void execute(Response response) {
		//得到界面对象
		HallPanel hallPanel = (HallPanel)UIContext.modules.get(UIContext.HALL_PANEL);
		if (response.getErrorCode() != null) {
			String errorCode = (String)response.getErrorCode();
			UIContext.showMessage(FiveChessErrorCode.getMessage(errorCode));
		} else {
			//有新的玩家坐下, 这里由所有玩家(不包括发送人)执行
			int tableNumber = (Integer)response.getData("tableNumber");
			String side = (String)response.getData("side");
			String userId = (String)response.getData("userId");
			hallPanel.newUserSitDown(tableNumber, side, userId);
		}
	}

}
