package org.crazyit.gamehall.fivechess.server.action;

import java.net.Socket;

import org.crazyit.gamehall.commons.Request;
import org.crazyit.gamehall.commons.Response;
import org.crazyit.gamehall.commons.ServerAction;
import org.crazyit.gamehall.commons.User;
import org.crazyit.gamehall.fivechess.commons.ChessUser;
import org.crazyit.gamehall.fivechess.server.ChessContext;
import org.crazyit.gamehall.util.XStreamUtil;

/**
 * 玩家进入游戏大厅（服务器调用）
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class LoginAction implements ServerAction {

	
	public void execute(Request request, Response response, Socket socket) {
		//从请求参数中得到新登录的玩家对象
		ChessUser cu = (ChessUser)request.getParameter("user");
		cu.setSocket(socket);
		//加入到所有玩家中
		ChessContext.users.put(cu.getId(), cu);
		//将玩家设置到响应中
		response.setData("user", cu);
		//将所有玩家信息设置到响应中
		response.setData("users", ChessContext.users);
		//将所有的桌子信息返回到客户端
		response.setData("tables", ChessContext.tables);
		//将大厅中桌子的列数和行数返回到客户端
		response.setData("tableColumnSize", ChessContext.TABLE_COLUMN_SIZE);
		response.setData("tableRowSize", ChessContext.TABLE_ROW_SIZE);
		//返回给登录玩家, 登录成功
		cu.getPrintStream().println(XStreamUtil.toXML(response));
	}
	
}
