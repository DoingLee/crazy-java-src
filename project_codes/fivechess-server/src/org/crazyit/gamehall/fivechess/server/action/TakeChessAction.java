package org.crazyit.gamehall.fivechess.server.action;

import java.net.Socket;

import org.crazyit.gamehall.commons.Request;
import org.crazyit.gamehall.commons.Response;
import org.crazyit.gamehall.commons.ServerAction;
import org.crazyit.gamehall.fivechess.commons.Chess;
import org.crazyit.gamehall.fivechess.commons.ChessUser;
import org.crazyit.gamehall.fivechess.commons.Table;
import org.crazyit.gamehall.fivechess.server.ChessContext;
import org.crazyit.gamehall.util.XStreamUtil;

/**
 * 玩家下棋时服务器接收请求的Action
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class TakeChessAction implements ServerAction {

	public void execute(Request request, Response response, Socket socket) {
		//得到下棋的位置, 在数组中一维和二维的值
		Integer i = (Integer)request.getParameter("i");
		Integer j = (Integer)request.getParameter("j");
		//下棋的玩家
		ChessUser user = ChessContext.users.get((String)request.getParameter("userId"));
		//得到棋的颜色
		String color = (String)request.getParameter("color");
		//得到桌子编号
		Integer tableNumber = (Integer)request.getParameter("tableNumber");
		Table table = Table.getTable(tableNumber, ChessContext.tables);
		//设置颜色
		Chess[][] chessArray = ChessContext.tableChesses.get(tableNumber);
		chessArray[i][j].setColor(color);
		//告诉对手已经下棋了
		ChessUser opponent = table.getAnotherUser(user);
		printToOpponent(opponent, response);
		//判断是否胜利
		boolean win = validateWin(chessArray, chessArray[i][j]);
		if (opponent == null) win = true;
		//告诉双方, 赢了
		if (win) {
			//告诉赢的一方
			tellWin(request, user, response);
			//告诉输的一方
			tellLost(request, opponent, response);
			//设置服务器中双方的状态
			opponent.setReady(false);
			user.setReady(false);
		}
	}
	
	//向对手输入信息
	private void printToOpponent(ChessUser opponent, Response response) {
		if (opponent != null) opponent.getPrintStream().println(XStreamUtil.toXML(response));
	}
	
	//告诉赢的一方
	private void tellWin(Request request, ChessUser user, Response response) {
		String winAction = (String)request.getParameter("winAction");
		response.setActionClass(winAction);
		user.getPrintStream().println(XStreamUtil.toXML(response));
	}
	
	//告诉输的一方
	private void tellLost(Request request, ChessUser opponent, Response response) {
		String lostAction = (String)request.getParameter("lostAction");
		response.setActionClass(lostAction);
		printToOpponent(opponent, response);
	}

	//判断是否胜利
	private boolean validateWin(Chess[][] chessArray, Chess chess) {
		boolean win = false;
		if (vertical(chessArray, chess)) win = true;
		if (horizontal(chessArray, chess)) win = true;
		if (bevelUpToDown(chessArray, chess)) win = true;
		if (bevelDownToUp(chessArray, chess)) win = true;
		return win;
	}
	
	//纵向遍历
	private boolean vertical(Chess[][] chessArray, Chess chess) {
		//连续棋子的总数
		int count = 0;
		for (int i = 0; i < chessArray.length; i++) {
			if (i == chess.getI()) {
				for (int j = 0; j < chessArray[i].length; j++) {
					Chess c = chessArray[i][j];
					if (c.getColor() != null && c.getColor().equals(chess.getColor())) {
						count++;
					}
				}
			}
		}
		if (count >= ChessContext.LINK_COUNT) return true;
		return false;
	}
	
	//横向遍历
	private boolean horizontal(Chess[][] chessArray, Chess chess) {
		int count = 0;
		for (int j = 0; j < chessArray[0].length; j++) {
			if (j == chess.getJ()) {
				for (int i = 0; i < chessArray.length; i++) {
					Chess c = chessArray[i][j];
					if (c.getColor() != null && c.getColor().equals(chess.getColor())) {
						count++;
					}
				}
			}
		}
		if (count >= ChessContext.LINK_COUNT) return true;
		return false;
	}
	
	//上至下斜向遍历(左至右)
	private boolean bevelUpToDown(Chess[][] chessArray, Chess chess) {
		int count = 0;
		//得到斜边的开始
		int min = getMin(chess.getI(), chess.getJ());
		int beginI = chess.getI() - min;
		int beginJ = chess.getJ() - min;
		for (int i = beginI; i < chessArray.length; i++) {
			if (beginJ >= chessArray[0].length) break;
			Chess c = chessArray[i][beginJ];
			if (c.getColor() != null && c.getColor().equals(chess.getColor())) {
				count++;
			}
			beginJ++;
		}
		if (count >= ChessContext.LINK_COUNT) return true;
		return false;
	}
	
	//从i和j中得到较小的一个
	private int getMin(int i, int j) {
		if (i > j) return j;
		if (i < j) return i;
		return i;
	}
	
	//下至上斜向遍历(左至右)
	private boolean bevelDownToUp(Chess[][] chessArray, Chess chess) {
		int count = 0;
		//得到到y轴的距离
		int xInstance = chess.getI();
		//得到与组数二维最大值的差
		int yInstance = chessArray[0].length - 1 - chess.getJ();
		//求距离的最小值
		int min = getMin(xInstance, yInstance);
		//得到最左边棋子的一维与二维值
		int beginI = chess.getI() - min;
		int beginJ = chess.getJ() + min;
		for (int i = beginI; i < chessArray.length; i++) {
			if (beginJ < 0) break;
			Chess c = chessArray[i][beginJ];
			if (c.getColor() != null && c.getColor().equals(chess.getColor())) {
				count++;
			}
			beginJ--;
		}
		if (count >= ChessContext.LINK_COUNT) return true;
		return false;
	}
	
}
