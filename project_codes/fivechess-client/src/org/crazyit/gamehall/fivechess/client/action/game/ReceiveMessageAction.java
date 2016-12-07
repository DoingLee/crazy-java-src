package org.crazyit.gamehall.fivechess.client.action.game;

import org.crazyit.gamehall.commons.ClientAction;
import org.crazyit.gamehall.commons.Response;
import org.crazyit.gamehall.fivechess.client.ui.ChatPanel;
import org.crazyit.gamehall.fivechess.client.ui.UIContext;

/**
 * 游戏中接收聊天信息的类
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class ReceiveMessageAction implements ClientAction {

	public void execute(Response response) {
		//得到聊天的界面组件
		ChatPanel chatPanel = (ChatPanel)UIContext.modules.get(UIContext.GAME_CHAT_PANEL);
		//从服务器响应中得到内容
		String content = (String)response.getData("content");
		chatPanel.appendContent(content);
	}

}
