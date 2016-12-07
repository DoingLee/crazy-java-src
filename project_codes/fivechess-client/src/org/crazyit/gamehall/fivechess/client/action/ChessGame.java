package org.crazyit.gamehall.fivechess.client.action;

import org.crazyit.gamehall.commons.Game;
import org.crazyit.gamehall.commons.Request;
import org.crazyit.gamehall.commons.User;
import org.crazyit.gamehall.fivechess.client.ChessClientContext;
import org.crazyit.gamehall.fivechess.commons.ChessUser;
import org.crazyit.gamehall.util.XStreamUtil;

/**
 * 实现框架中的Game接口, 五子棋游戏入口类
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class ChessGame implements Game {

	public String toString() {
		return "五子棋";
	}

	public void start(User user) {
		//得到进入游戏的玩家信息
		ChessUser cu = convertUser(user);
		ChessClientContext.chessUser = cu;
		//构造一次请求, 告诉服务器玩家进入大厅, 服务器响应处理类是LoginAction
		Request req = new Request("org.crazyit.gamehall.fivechess.server.action.LoginAction", 
				"org.crazyit.gamehall.fivechess.client.action.ClientInAction");
		req.setParameter("user", cu);
		//得到玩家的Socket并发送请求, 告诉服务器玩家进入了大厅
		cu.getPrintStream().println(XStreamUtil.toXML(req));
	}
	
	//将User转换成ChessUser对象
	private ChessUser convertUser(User user) {
		ChessUser cu = new ChessUser();
		cu.setHeadImage(user.getHeadImage());
		cu.setId(user.getId());
		cu.setName(user.getName());
		cu.setSex(user.getSex());
		cu.setSocket(user.getSocket());
		return cu;
	}

}
