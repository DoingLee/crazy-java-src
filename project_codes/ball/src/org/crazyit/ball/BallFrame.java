package org.crazyit.ball;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;

/**
 * 游戏界面
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @author Kelvin Mak kelvin.mak125@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class BallFrame extends JFrame {
	// 定义JPanel的宽度
	private final int BALLPANEL_WIDTH = 307;
	// 定义JPanel的高度
	private final int BALLPANEL_HEIGHT = 400;
	// 定义画板
	private BallPanel ballPanel = null;
	// 定义档板
	// private Image stick = null;
	// 设置档板x坐标
	private int stickX = -1;
	// 创建一个BallService实例
	private BallService service = null;
	// 定义一个timer
	Timer timer = null;

	/**
	 * 默认构造器
	 */
	public BallFrame() throws IOException {
		super();
		// 初始化
		initialize();
	}

	/**
	 * 初始化界面
	 * 
	 * @return void
	 */
	public void initialize() throws IOException {
		// 设置窗口的标题
		this.setTitle("弹球");
		// 设置为不可改变大小
		this.setResizable(false);
		// 设置背景为黑色
		this.setBackground(Color.BLACK);
		// 获取画板
		ballPanel = getBallPanel();
		// 创建BallService实例
		service = new BallService(this, BALLPANEL_WIDTH, BALLPANEL_HEIGHT);

		// 定义每0.1秒执行一次监听器
		ActionListener task = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 开始改变位置
				service.run();
				// 刷新画板
				ballPanel.repaint();
			}
		};
		// 如果timer不为空
		if (timer != null) {
			// 重新开始timer
			timer.restart();
		} else {
			// 新建一个timer
			timer = new Timer(100, task);
			// 开始timer
			timer.start();
		}

		this.add(ballPanel);
		// 增加事件监听器
		KeyListener[] klarr = this.getKeyListeners();
		if (klarr.length == 0) {
			// 定义键盘监听适配器
			KeyListener keyAdapter = new KeyAdapter() {
				public void keyPressed(KeyEvent ke) {
					// 改变档板的坐标
					service.setStickPos(ke);
				}
			};
			this.addKeyListener(keyAdapter);
		}
	}

	/**
	 * 获取画板
	 * 
	 * @return BallPanel 返回BallPanle
	 */
	public BallPanel getBallPanel() {

		if (ballPanel == null) {
			// 新建一个画板
			ballPanel = new BallPanel();
			// 设置画板的大小
			ballPanel.setPreferredSize(new Dimension(BALLPANEL_WIDTH,
					BALLPANEL_HEIGHT));
		}
		return ballPanel;
	}

	// 定义一个JPanel内部类来完成画图功能
	public class BallPanel extends JPanel {
		/**
		 * 重写void paint( Graphics g )方法
		 * 
		 * @param g
		 *            Graphics
		 * @return void
		 */
		public void paint(Graphics g) {
			// 画图
			service.draw(g);
		}
	}

}