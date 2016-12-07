package org.crazyit.gamehall.fivechess.client.action;

import org.crazyit.gamehall.commons.ClientAction;
import org.crazyit.gamehall.commons.Response;
import org.crazyit.gamehall.fivechess.client.ui.ChatPanel;
import org.crazyit.gamehall.fivechess.client.ui.UIContext;
import org.crazyit.gamehall.fivechess.client.ui.UserTable;
import org.crazyit.gamehall.fivechess.commons.ChessUser;

/**
 * 所有玩家接收新玩家进入大厅的Action处理类
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class ReceiveInAction implements ClientAction {

	
	public void execute(Response response) {
		//得到新进入的玩家
		ChessUser newUser = (ChessUser)response.getData("newUser");
		//向玩家列表中加入一个新玩家
		UserTable userTable = (UserTable)UIContext.modules.get(UIContext.HALL_USER_TABLE);
		userTable.addUser(newUser);
		//向聊天内容中添加
		ChatPanel chatPanel = (ChatPanel)UIContext.modules.get(UIContext.HALL_CHAT_PANEL);
		chatPanel.appendContent(newUser.getName() + " 进来了");
		chatPanel.refreshJComboBox();
	}

}
