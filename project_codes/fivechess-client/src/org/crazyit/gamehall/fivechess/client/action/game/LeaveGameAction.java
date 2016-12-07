package org.crazyit.gamehall.fivechess.client.action.game;

import org.crazyit.gamehall.commons.ClientAction;
import org.crazyit.gamehall.commons.Response;
import org.crazyit.gamehall.fivechess.client.ui.HallPanel;
import org.crazyit.gamehall.fivechess.client.ui.UIContext;
import org.crazyit.gamehall.fivechess.commons.Table;

/**
 * 玩家离开游戏的Action
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class LeaveGameAction implements ClientAction {

	public void execute(Response response) {
		//得到所有信息, 哪个玩家离开了哪个桌子
		Integer tableNumber = (Integer)response.getData("tableNumber");
		String side = (String)response.getData("side");
		//得到保存桌子信息的界面组件
		HallPanel hallPanel = (HallPanel)UIContext.modules.get(UIContext.HALL_PANEL);
		Table table = hallPanel.getTable(tableNumber);
		//设置座位的玩家的空
		table.getSeat(side).getUser().setReady(false);
		table.getSeat(side).setUser(null);
		//刷新界面
		hallPanel.repaint();
	}

}
