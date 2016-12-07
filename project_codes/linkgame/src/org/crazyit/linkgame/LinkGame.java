package org.crazyit.linkgame;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JRootPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Image;
import javax.swing.event.MouseInputAdapter;

import org.crazyit.linkgame.commons.GameConfiguration;
import org.crazyit.linkgame.listener.BeginListener;
import org.crazyit.linkgame.listener.GameListener;
import org.crazyit.linkgame.service.GameService;
import org.crazyit.linkgame.service.impl.GameServiceImpl;
import org.crazyit.linkgame.view.GamePanel;

import java.awt.event.MouseEvent;

/**
 * 游戏入口类
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version 1.0 <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a> <br>
 *          Copyright (C), 2009-2010, yangenxiong <br>
 *          This program is protected by copyright laws.
 */
public class LinkGame {
	public static void main(String[] args) throws Exception {
		// 设置窗口的标题
		JFrame frame = new JFrame("连连看");
		// 设置关闭窗口的动作，退出程序。
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 设置宽和高
		frame.setSize(830, 600);
		// 设置frame的位置
		frame.setLocation(100, 50);
		// 设置不可改变大小
		frame.setResizable(false);
		// 去掉原来窗口风格
		frame.setUndecorated(true);
		// 设置标题栏样式
		frame.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
		frame.setLayout(new BorderLayout(5, 0));

		// 创建棋盘一维最大值为15， 二维最大值为10的游戏配置, 消除一次加10分, 游戏时间一百秒
		final GameConfiguration config = new GameConfiguration(15, 10, 30, 30,
				10, 150);
		// 使用上面的GameConfiguration对象创建一个GameService的实现类
		final GameService gameService = new GameServiceImpl(config);
		// 创建一个JPanel对象
		final GamePanel gamePanel = new GamePanel(gameService);

		frame.add(gamePanel, BorderLayout.CENTER);

		// 创建一个JPanel对象
		JPanel controlPanel = new JPanel();
		// 设置背景颜色
		controlPanel.setBackground(new Color(127, 174, 252));
		// 设置边框样式
		controlPanel.setBorder(new EtchedBorder());
		// 创建一个BoxLayout对象，第一个参数为需要布局的的容器，第二个参数为设置从上至下进行布局
		BoxLayout controlLayout = new BoxLayout(controlPanel, BoxLayout.Y_AXIS);
		// 设置controlPanel的布局为BoxLayout
		controlPanel.setLayout(controlLayout);
		// 放在主界面frame的EAST
		frame.add(controlPanel, BorderLayout.EAST);

		// 创建一个JPanel, 用于存放疯狂Java联盟的logo
		JPanel crazyItLogoPanel = new JPanel();
		// 设置这个JPanel的边框
		crazyItLogoPanel.setBorder(new EtchedBorder());
		// 读取logo图片
		Image crazyItLogoImage = ImageIO
				.read(new File("images/crazyItLogo.jpg"));
		// 创建一个以上面读取的图片为背景的JLabel
		JLabel crazyItLogoLable = new JLabel(new ImageIcon(crazyItLogoImage));
		// 设置JPanel的背景颜色
		crazyItLogoPanel.setBackground(new Color(127, 174, 252));
		// 将上面的JLabel加入到JPanel中
		crazyItLogoPanel.add(crazyItLogoLable);
		// 最后将存放疯狂Java联盟logo的JPanel放到控制区的那个JPanel(controlPanel)中
		controlPanel.add(crazyItLogoPanel);
		// 创建空白的JPanel
		controlPanel.add(createBlankPanel());

		// 再新建一个自己的logo
		JPanel logoPanel = new JPanel();
		// 设置边框
		logoPanel.setBorder(new EtchedBorder());
		// 读取图片
		Image logoImage = ImageIO.read(new File("images/logo.gif"));
		// 创建一个JLabel
		JLabel logoLable = new JLabel(new ImageIcon(logoImage));
		// 设置背景颜色
		logoPanel.setBackground(new Color(127, 174, 252));
		// 将JLabel加入到JPanel中
		logoPanel.add(logoLable);
		// 加到controlPanel中
		controlPanel.add(logoPanel);
		// 创建空白的JPanel
		controlPanel.add(createBlankPanel());

		// 创建一个存放分数文字的JPanel
		JPanel pointTextPanel = new JPanel();
		// 设置这个JPanel的背景色
		pointTextPanel.setBackground(new Color(169, 210, 254));
		// 设置这个JPanel的边框
		pointTextPanel.setBorder(new EtchedBorder());
		// 创建一个JLabel用于放置"分数"两个字
		JLabel pointTextLabel = new JLabel();
		// 设置这个JLabel的text为"分数", 为了更好看, 我们在分数两个字之间加入空格
		pointTextLabel.setText("分            数");
		// 将JLabel加入到JPanel中
		pointTextPanel.add(pointTextLabel);
		// 将存放分数文字的JPanel放到游戏控制区的JPanel(controlPanel)中
		controlPanel.add(pointTextPanel);

		// 创建一个JPanel
		JPanel pointPanel = new JPanel();
		// 设置边框
		pointPanel.setBorder(new EtchedBorder());
		// 设置背景颜色
		pointPanel.setBackground(new Color(208, 223, 255));
		// 创建一个JLabel
		final JLabel pointLabel = new JLabel();
		// 设置初始文字
		pointLabel.setText("0");
		// 将JLabel添加到JPanel中
		pointPanel.add(pointLabel);
		// 加入到游戏控制区的JPanel(controlPanel)中
		controlPanel.add(pointPanel);
		// 创建空白的JPanel
		controlPanel.add(createBlankPanel());

		// 时间计算字体的Panel
		JPanel timeTextPanel = new JPanel();
		timeTextPanel.setBackground(new Color(169, 210, 254));
		timeTextPanel.setBorder(new EtchedBorder());
		JLabel timeTextLabel = new JLabel();// "分数"两个字存放字体
		timeTextLabel.setText("时            间");
		timeTextPanel.add(timeTextLabel);
		controlPanel.add(timeTextPanel);

		// 时间计算
		JPanel timePanel = new JPanel();
		timePanel.setBorder(new EtchedBorder());
		timePanel.setBackground(new Color(208, 223, 255));
		JLabel timeLabel = new JLabel();// 分数结果存放
		timeLabel.setText("0   秒");
		timePanel.add(timeLabel);
		controlPanel.add(timePanel);
		// 创建空白的JPanel
		controlPanel.add(createBlankPanel());

		// 创建一个timer

		// 新建一个放置button的JPanel
		JPanel buttonsPanel = new JPanel();
		// 设置这个JPanel的布局方法, 采用BoxLayout并进行从左到右进行布局
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
		// 设置背景颜色
		buttonsPanel.setBackground(new Color(127, 174, 252));
		// 创建开始按钮
		JButton beginButton = new JButton("开    始");
		buttonsPanel.add(beginButton);
		// 新建一个JLabel用于隔开两个按钮, 使用界面好看点
		JLabel blankLabel = new JLabel();
		blankLabel.setText("     ");
		buttonsPanel.add(blankLabel);

		// 创建一个监听器类
		BeginListener beginListener = new BeginListener(gamePanel, gameService,
				pointLabel, timeLabel, config);
		// 使用addMouseListener方法，加入该按钮的鼠标监听器
		beginButton.addMouseListener(beginListener);
		// 创建游戏区的监听器类
		GameListener gameListener = new GameListener(gameService, gamePanel,
				pointLabel, beginListener);
		// 为GamePanel加入鼠标监听器, 并将beginListener传入,用于获取它的timer属性
		gamePanel.addMouseListener(gameListener);

		// 创建退出按钮
		JButton exitButton = new JButton("退    出");
		// 为退出操作按钮加入事件
		exitButton.addMouseListener(new MouseInputAdapter() {
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		buttonsPanel.add(exitButton);
		controlPanel.add(buttonsPanel);
		// 创建空白的JPanel
		controlPanel.add(createBlankPanel());

		frame.setVisible(true); // 使窗口可见
	}

	private static JPanel createBlankPanel() {
		// 创建一个JPanel
		JPanel blankPanel = new JPanel();
		// 设置这个JPanel的背景颜色(与控制区的JPanel(controlPanel)背景颜色一致
		blankPanel.setBackground(new Color(127, 174, 252));
		// 创建一个JLabel, 用于存放空格
		JLabel blankLabel = new JLabel();
		// 设置字体为空格
		blankLabel.setText("      ");
		// 将JLabel添加到空白的JPanel中
		blankPanel.add(blankLabel);
		// 返回这个JPanel对象
		return blankPanel;
	}
}
