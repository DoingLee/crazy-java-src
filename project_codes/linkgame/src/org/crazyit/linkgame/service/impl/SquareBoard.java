package org.crazyit.linkgame.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.crazyit.linkgame.commons.GameConfiguration;
import org.crazyit.linkgame.commons.Piece;
import org.crazyit.linkgame.service.AbstractBoard;

/**
 * 创建方形的游戏区域
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */

public class SquareBoard extends AbstractBoard {
	protected List<Piece> createPieces(GameConfiguration config,
			Piece[][] pieces) {
		List<Piece> notNullPieces = new ArrayList<Piece>();
		for (int i = 0; i < pieces.length; i++) {
			for (int j = 0; j < pieces[i].length; j++) {
				// 先构造一个Piece对象, 只设置数组中的位置为i, j，其他值不设置
				Piece piece = new Piece(i, j);
				// 添加到非空Piece对象的集合中
				notNullPieces.add(piece);
			}
		}
		return notNullPieces;
	}
}
