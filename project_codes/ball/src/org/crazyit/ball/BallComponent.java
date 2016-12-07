package org.crazyit.ball;

import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * 桌面弹球游戏相关组件的父类
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @author Kelvin Mak kelvin.mak125@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class BallComponent {
	// 设置x坐标
	private int x = -1;
	// 设置y坐标
	private int y = -1;
	// 设置图片
	private Image image = null;
	// 设置图片速度
	private int speed = 5;

	/**
	 * 构造器
	 * 
	 * @param path
	 *            String 图片路径
	 */
	public BallComponent(String path) throws IOException {
		super();
		this.image = ImageIO.read(new File(path));
	}

	/**
	 * 构造器
	 * 
	 * @param panelWidth
	 *            int 画板宽度
	 * @param panelHeight
	 *            int 画板高度
	 * @param path
	 *            String 图片路径
	 */
	public BallComponent(int panelWidth, int panelHeight, String path)
			throws IOException {
		super();
		// 读取图片
		this.image = ImageIO.read(new File(path));
		// 设置x坐标
		this.x = (int) ((panelWidth - image.getWidth(null)) / 2);
	}

	/**
	 * 构造器
	 * 
	 * @param x
	 *            int 图像的x坐标
	 * @param y
	 *            int 图像的y坐标
	 * @param path
	 *            String 图片路径
	 */
	public BallComponent(String path, int x, int y) throws IOException {
		super();
		// 读取图片
		this.image = ImageIO.read(new File(path));
		this.x = x;
		this.y = y;
	}

	/**
	 * 获取x坐标
	 * 
	 * @return int x坐标
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * 获取y坐标
	 * 
	 * @return int y坐标
	 */
	public int getY() {
		return this.y;
	}

	/**
	 * 获取图片速度
	 * 
	 * @return int 图片速度
	 */
	public int getSpeed() {
		return this.speed;
	}

	/**
	 * 设置x坐标
	 * 
	 * @param x
	 *            int x坐标
	 * @return void
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * 设置y坐标
	 * 
	 * @param y
	 *            int y坐标
	 * @return void
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * 返回图片
	 * 
	 * @return Image 图片
	 */
	public Image getImage() {
		return this.image;
	}
}