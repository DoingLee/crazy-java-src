package org.crazyit.gamehall.fivechess.client.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.crazyit.gamehall.commons.ClientAction;
import org.crazyit.gamehall.commons.Request;
import org.crazyit.gamehall.commons.Response;
import org.crazyit.gamehall.commons.User;
import org.crazyit.gamehall.fivechess.client.ChessClientContext;
import org.crazyit.gamehall.fivechess.client.object.GameHallInfo;
import org.crazyit.gamehall.fivechess.client.ui.GameHallFrame;
import org.crazyit.gamehall.fivechess.commons.ChessUser;
import org.crazyit.gamehall.fivechess.commons.Table;
import org.crazyit.gamehall.util.XStreamUtil;

/**
 * 服务器让客户端启动游戏大厅
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class ClientInAction implements ClientAction {

	
	public void execute(Response response) {
		//从服务器中得到大厅信息并封装成一个GameHallInfo对象
		GameHallInfo hallInfo = getGameHallInfo(response);
		//得到全部玩家的信息
		List<ChessUser> users = getUsers(response);
		//得到进入游戏的玩家信息
		ChessUser cu = ChessClientContext.chessUser;
		//创建界面GameHallFrame
		GameHallFrame mainFrame = new GameHallFrame(hallInfo, cu, users);
		mainFrame.sendUserIn();
	}
	
	//从Response中得到所有玩家的信息, 并转换成List(服务器中保存的数据结构是Map)
	private List<ChessUser> getUsers(Response response) {
		Map<String, User> users = (Map<String, User>)response.getData("users");
		List<ChessUser> result = new ArrayList<ChessUser>();
		for (String id : users.keySet()) {
			ChessUser cu = (ChessUser)users.get(id);
			result.add(cu);
		}
		return result;
	}
	
	private GameHallInfo getGameHallInfo(Response response) {
		//得到大厅桌子列数
		int tableColumnSize = (Integer)response.getData("tableColumnSize");
		//得到大厅桌子行数
		int tableRowSize = (Integer)response.getData("tableRowSize");
		//得到桌子信息
		Table[][] tables = (Table[][])response.getData("tables");
		GameHallInfo info = new GameHallInfo(tables, tableColumnSize, tableRowSize);
		return info;
	}

}
