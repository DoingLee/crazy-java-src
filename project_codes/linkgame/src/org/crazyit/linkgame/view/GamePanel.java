package org.crazyit.linkgame.view;

import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import org.crazyit.linkgame.commons.LinkInfo;
import org.crazyit.linkgame.commons.Piece;
import org.crazyit.linkgame.commons.Point;
import org.crazyit.linkgame.service.GameService;
import org.crazyit.linkgame.utils.ImageUtil;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.awt.image.BufferedImage;

/**
 * 游戏区域界面对象
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class GamePanel extends JPanel {
	private GameService gameService;

	// GamePanel中最多只显示一个已经被选中的棋子
	private Piece selectPiece;

	// 连接信息对象
	private LinkInfo linkInfo;

	// 游戏结束时候显示的图片
	private BufferedImage overImage;

	public void setLinkInfo(LinkInfo linkInfo) {
		this.linkInfo = linkInfo;
	}

	public GamePanel(GameService gameService) {
		// 设置这个JPanel的背景颜色
		this.setBackground(new Color(55, 77, 118));
		// 设置这个JPanel的边框样式
		this.setBorder(new EtchedBorder());
		// 用于在构造本对象时设置GameService
		this.gameService = gameService;
	}

	public void setOverImage(BufferedImage overImage) {
		this.overImage = overImage;
	}

	public BufferedImage getOverImage() {
		return overImage;
	}

	public void setSelectPiece(Piece piece) {
		this.selectPiece = piece;
	}

	public void paint(Graphics g) {
		// 画背景图，从GamePanel(棋盘)的0, 0开始画，画满整个GamePanel
		g.drawImage(ImageUtil.getImage("images/background.gif"), 0, 0,
				getWidth(), getHeight(), null);
		// 通过gameService获得pieces数组对象
		Piece[][] pieces = gameService.getPieces();
		// 根据这个数组来画这个组件
		// 如果pieces没有初始化，就不要进行下面的行为
		if (pieces != null) {
			// 对这个二维数组进行遍历
			for (int i = 0; i < pieces.length; i++) {
				for (int j = 0; j < pieces[i].length; j++) {
					// 如果定位到这个二维数组的某个值不为空，那么将这个对象的图片画出来
					if (pieces[i][j] != null) {
						// 得到这个Piece对象
						Piece piece = pieces[i][j];
						// 调用Graphics类的drawImage方法画图，从piece的开始坐标开始画
						g.drawImage(piece.getImage(), piece.getBeginX(), piece
								.getBeginY(), null);
					}
				}
			}
		}
		if (this.selectPiece != null) {
			// 当前有棋子被选中, 绘制选择的图片
			g.drawImage(ImageUtil.getImage("images/selected.gif"),
					this.selectPiece.getBeginX(), this.selectPiece.getBeginY(),
					null);
		}
		// 如果当前对象中有linkInfo对象, 即连接信息
		if (this.linkInfo != null) {
			// 暂时不提供处理
			drawLine(this.linkInfo, g);
			// 处理完后清空linkInfo对象
			this.linkInfo = null;
		}
		// 如果overImage不为空, 则表示游戏已经胜利或者失败
		if (this.overImage != null) {
			g.drawImage(this.overImage, 0, 0, null);
		}
	}

	private void drawLine(LinkInfo linkInfo, Graphics g) {
		List<Point> points = linkInfo.getLinkPoints();
		for (int i = 0; i < points.size() - 1; i++) {
			Point currentPoint = points.get(i);
			Point nextPoint = points.get(i + 1);

			g.drawLine(currentPoint.getX(), currentPoint.getY(), nextPoint
					.getX(), nextPoint.getY());
		}
	}
}
