package org.crazyit.tetris.object.impl;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.crazyit.tetris.object.Piece;
import org.crazyit.tetris.object.PieceCreator;
import org.crazyit.tetris.object.Square;
import org.crazyit.tetris.piece.Piece0;
import org.crazyit.tetris.piece.Piece1;
import org.crazyit.tetris.piece.Piece2;
import org.crazyit.tetris.piece.Piece3;
import org.crazyit.tetris.piece.Piece4;
import org.crazyit.tetris.piece.Piece5;
import org.crazyit.tetris.piece.Piece6;
import org.crazyit.tetris.util.ImageUtil;

public class PieceCreatorImpl implements PieceCreator {

	//缓存各个Square
	private Map<Integer, Image> images = new HashMap<Integer, Image>();
	
	//总共有七种颜色的方块
	private final static int COLOR_SIZE = 7;
	
	//总共有七种大方块
	private final static int SQUARE_SIZE = 7;
	
	private Random random = new Random();
		
	@Override
	public Piece createPiece(int x, int y) {
		//随机得到一张方块图片
		Image image = getImage(random.nextInt(COLOR_SIZE));
		Piece piece = initPiece(image);
		piece.setSquaresXLocation(x);
		piece.setSquaresYLocation(y);
		return piece;
	} 
	
	private Piece initPiece(Image image) {
		Piece piece = null;
		int pieceType = random.nextInt(SQUARE_SIZE);
		//初始化Piece，随机创建各个大方块
		if (pieceType == 0) {
			piece = new Piece0(image);
		} else if (pieceType == 1) {
			piece = new Piece1(image);
		} else if (pieceType == 2) {
			piece = new Piece2(image);
		} else if (pieceType == 3) {
			piece = new Piece3(image);
		} else if (pieceType == 4) {
			piece = new Piece4(image);
		} else if (pieceType == 5) {
			piece = new Piece5(image);
		} else if (pieceType == 6) {
			piece = new Piece6(image);
		}
//		piece = new Piece1(image);
		return piece;
	}
	
	/**
	 * 从map中得到图片对象，如果map中没有存在图片对象, 则创建
	 * @param key
	 * @return
	 */
	private Image getImage(int key) {
		if (this.images.get(key) == null) {
			Image s = ImageUtil.getImage("images/square" + key + ".jpg");
			this.images.put(key, s);
		}
		return this.images.get(key);
	}

	@Override
	public Piece getPiece() {
		// TODO Auto-generated method stub
		return null;
	}

}
