package org.crazyit.ball;

import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * 小球对象
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @author Kelvin Mak kelvin.mak125@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class Ball extends BallComponent {
	// 定义球的竖向速度
	private int speedY = 10;
	// 定义弹球的横向速度
	private int speedX = 8;
	// 定义是否在运动
	private boolean started = false;
	// 定义是否结束运动
	private boolean stop = false;

	/**
	 * m 有参数构造器
	 * 
	 * @param panelWidth
	 *            int 画板宽度
	 * @param panelHeight
	 *            int 画板高度
	 * @param offset
	 *            int 位移
	 * @param path
	 *            String 图片路径
	 */
	public Ball(int panelWidth, int panelHeight, int offset, String path)
			throws IOException {
		// 调用父构造器
		super(panelWidth, panelHeight, path);
		// 设置y坐标
		this.setY(panelHeight - super.getImage().getHeight(null) - offset);
	}

	/**
	 * 设置横向速度
	 * 
	 * @param speed
	 *            int 速度
	 * @return void
	 */
	public void setSpeedX(int speed) {
		this.speedX = speed;
	}

	/**
	 * 设置竖向速度
	 * 
	 * @param speed
	 *            int 速度
	 * @return void
	 */
	public void setSpeedY(int speed) {
		this.speedY = speed;
	}

	/**
	 * 设置是否在运动
	 * 
	 * @param b
	 *            boolean
	 * @return void
	 */
	public void setStarted(boolean b) {
		this.started = b;
	}

	/**
	 * 设置是否结束运动
	 * 
	 * @param b
	 *            boolean
	 * @return void
	 */
	public void setStop(boolean b) {
		this.stop = b;
	}

	/**
	 * 返回横向速度
	 * 
	 * @return int 速度
	 */
	public int getSpeedX() {
		return this.speedX;
	}

	/**
	 * 返回竖向速度
	 * 
	 * @return int 速度
	 */
	public int getSpeedY() {
		return this.speedY;
	}

	/**
	 * 是否在运动
	 * 
	 * @return boolean 是否在运动
	 */
	public boolean isStarted() {
		return this.started;
	}

	/**
	 * 是否已经结束运动
	 * 
	 * @return boolean 是否已经结束运动
	 */
	public boolean isStop() {
		return this.stop;
	}

}