package org.crazyit.image.tool;

import org.crazyit.image.ImageFrame;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Robot;
import java.awt.Color;
import java.awt.AWTException;
import java.awt.event.MouseEvent;

/**
 * 拾色器
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @author Kelvin Mak kelvin.mak125@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class ColorPickedTool extends AbstractTool {
	private static Tool tool = null;

	private ColorPickedTool(ImageFrame frame) {
		super(frame, "img/colorcursor.gif");
	}

	public static Tool getInstance(ImageFrame frame) {
		if (tool == null) {
			tool = new ColorPickedTool(frame);
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
		if (e.getX() > 0 && e.getY() > 0) {
			if (e.getX() < AbstractTool.drawWidth
					&& e.getY() < AbstractTool.drawHeight) {
				setPressX(e.getX());
				setPressY(e.getY());
				/**
				 * 设置颜色 getRGB()返回默认 sRGB ColorModel 中表示颜色的 RGB 值 24-31 位表示
				 * alpha，16-23 位表示红色， 8-15 位表示绿色，0-7 位表示蓝色
				 */
				int rgb = getFrame().getBufferedImage().getRGB(e.getX(),
						e.getY());
				// 前8位
				int int8 = (int) Math.pow(2, 8);
				// 前16位
				int int16 = (int) Math.pow(2, 16);
				// 前24位
				int int24 = (int) Math.pow(2, 24);
				// 分别取0-7位,8-15位,16-23位
				int r = (rgb & (int24 - int16)) >> 16;
				int g = (rgb & (int16 - int8)) >> 8;
				int b = (rgb & (int8 - 1));
				// 设置颜色
				AbstractTool.color = new Color(r, g, b);
			} else {
				try {
					AbstractTool.color = new Robot().getPixelColor(e.getX(), e
							.getY());
				} catch (AWTException ae) {
					ae.printStackTrace();
				}
			}
			// 设置目前显示的颜色
			getFrame().getCurrentColorPanel().setBackground(AbstractTool.color);
		}
	}
}
