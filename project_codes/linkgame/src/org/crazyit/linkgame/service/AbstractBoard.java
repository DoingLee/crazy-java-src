package org.crazyit.linkgame.service;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.awt.image.BufferedImage;

import org.crazyit.linkgame.commons.GameConfiguration;
import org.crazyit.linkgame.commons.Piece;
import org.crazyit.linkgame.utils.ImageUtil;

/**
 * 游戏区域的抽象类
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public abstract class AbstractBoard {
	// 在棋盘对象中记录每张图片的宽
	private int commonImageWidth;

	// 在棋盘对象中记录每张图片的高
	private int commonImageHeight;

	public int getCommonImageWidth() {
		return this.commonImageWidth;
	}

	public int getCommonImageHeight() {
		return this.commonImageHeight;
	}

	// 建立一个抽象方法, 让子类去实现
	protected abstract List<Piece> createPieces(GameConfiguration config,
			Piece[][] pieces);

	public Piece[][] create(GameConfiguration config) {
		// 创建结果数组
		Piece[][] pieces = new Piece[config.getXSize()][config.getYSize()];
		// 返回非空的Piece集合, 该集合由子类去创建
		List<Piece> notNullPieces = createPieces(config, pieces);
		// 根据非空Piece对象的集合的大小来取图片
		List<BufferedImage> playImages = getPlayImages(notNullPieces.size());
		int imageWidth = playImages.get(0).getWidth();
		int imageHeight = playImages.get(0).getHeight();
		// 将第一张图片的宽和高设进本对象, 由于约定了每张图片的大小必须一致
		this.commonImageWidth = imageWidth;
		this.commonImageHeight = imageHeight;
		// 遍历非空的Piece集合
		for (int i = 0; i < notNullPieces.size(); i++) {
			// 拿到上面创建的非空Piece后, 再重新构造一个Piece对象, 用于放进数组中
			Piece piece1 = notNullPieces.get(i);
			// 获取piece1的indexX(在数组中一维的值)
			int indexX = piece1.getIndexX();
			// 获取piece1的indexY(在数组中二维的值)
			int indexY = piece1.getIndexY();
			// 以piece1的indexX和indexY来重新构建一个Piece, 并将这个新的Piece对象放到数组中
			Piece piece2 = new Piece(indexX * imageWidth
					+ config.getBeginImageX(), indexY * imageHeight
					+ config.getBeginImageY(), indexX, indexY, playImages
					.get(i));
			// 将piece2放到数组中
			pieces[indexX][indexY] = piece2;
		}
		return pieces;
	}

	// 获取size张图片
	public List<BufferedImage> getPlayImages(int size) {
		return ImageUtil.getPlayImages(new File("images/pieces"), size);
	}
}
