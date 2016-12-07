package org.crazyit.image.tool;

import org.crazyit.image.ImageFrame;
import java.util.Random;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

/**
 * 喷墨工具
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @author Kelvin Mak kelvin.mak125@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class AtomizerTool extends AbstractTool {
	private static Tool tool = null;

	private AtomizerTool(ImageFrame frame) {
		super(frame, "img/atomizercursor.gif");
	}

	public static Tool getInstance(ImageFrame frame) {
		if (tool == null) {
			tool = new AtomizerTool(frame);
		}
		return tool;
	}

	/**
	 * 按下鼠标
	 * 
	 * @param e
	 *            MouseEvent
	 * @return void
	 */
	public void mousePressed(MouseEvent e) {
		if (e.getX() > 0 && e.getX() < AbstractTool.drawWidth && e.getY() > 0
				&& e.getY() < AbstractTool.drawHeight) {
			setPressX(e.getX());
			setPressY(e.getY());
			Graphics g = getFrame().getBufferedImage().getGraphics();
			draw(e, g);
		}
	}

	/**
	 * 拖动鼠标
	 * 
	 * @param e
	 *            MouseEvent
	 * @return void
	 */
	public void mouseDragged(MouseEvent e) {
		super.mouseDragged(e);
		Graphics g = getFrame().getBufferedImage().getGraphics();
		draw(e, g);
	}

	/**
	 * 画图
	 * 
	 * @param e
	 *            MouseEvent
	 * @param g
	 *            Graphics
	 * @return void
	 */
	public void draw(MouseEvent e, Graphics g) {
		int x = 0;
		int y = 0;
		// 喷枪大小
		int size = 8;
		// 喷枪点数
		int count = 10;
		if (getPressX() > 0 && getPressY() > 0
				&& e.getX() < AbstractTool.drawWidth
				&& e.getY() < AbstractTool.drawHeight) {
			g.setColor(AbstractTool.color);
			for (int i = 0; i < count; i++) {
				x = new Random().nextInt(size) + 1;
				y = new Random().nextInt(size) + 1;
				g.fillRect(e.getX() + x, e.getY() + y, 1, 1);
			}

			setPressX(e.getX());
			setPressY(e.getY());
			getFrame().getDrawSpace().repaint();
		}
	}

}
