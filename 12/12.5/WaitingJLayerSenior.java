
import java.beans.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.plaf.LayerUI;
/**
 * Description:
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a> 
 * <br/>Copyright (C), 2001-2012, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author Yeeku.H.Lee kongyeeku@163.com
 * @version 1.0
 */
class WaitLayerUI extends LayerUI<JComponent> 
{
	private boolean isRunning;
	private boolean isFadingOut;
	private Timer timer;
	// 记录转过的角度
	private int angle;
	private int fadeCount;
	private int FADE_LIMIT = 15;

	public void paint (Graphics g, JComponent c) 
	{
		int w = c.getWidth();
		int h = c.getHeight();
		super.paint (g, c);
		// 已经停止运行
		if (!isRunning)
			return;
		Graphics2D g2 = (Graphics2D)g.create();
		float fade = (float)fadeCount / FADE_LIMIT;
		Composite urComposite = g2.getComposite();
		g2.setComposite(AlphaComposite.getInstance(
			AlphaComposite.SRC_OVER, .5f * fade));
		// 填充矩形
		g2.fillRect(0, 0, w, h);
		g2.setComposite(urComposite);
		// -----下面代码开始绘制转动中的“齿轮”----
		// 计算得到宽、高中较小值的1/5
		int s = Math.min(w , h) / 5;
		int cx = w / 2;
		int cy = h / 2;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING 
			, RenderingHints.VALUE_ANTIALIAS_ON);
		// 设置笔触
		g2.setStroke( new BasicStroke(s / 2
			, BasicStroke.CAP_ROUND , BasicStroke.JOIN_ROUND));
		g2.setPaint(Color.BLUE);
		// 画笔绕被装饰组件的中心转过angle度
		g2.rotate(Math.PI * angle / 180, cx, cy);
		// 循环绘制12条线条，形成“齿轮”
		for (int i = 0; i < 12; i++) 
		{
			float scale = (11.0f - (float)i) / 11.0f;
			g2.drawLine(cx + s, cy, cx + s * 2, cy);
			g2.rotate(-Math.PI / 6, cx, cy);
			g2.setComposite(AlphaComposite.getInstance(
				AlphaComposite.SRC_OVER, scale * fade));
		}
		g2.dispose();
	}
	// 控制等待（齿轮开始转动）的方法
	public void start() 
	{
		// 如果已经在运行中，直接返回
		if (isRunning) 
			return;
		// 启动一条线程来开始动画
		isRunning = true;
		isFadingOut = false;
		fadeCount = 0;
		// 每隔0.1秒重绘一次
		timer = new Timer(100, new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (isRunning) 
				{
					System.out.println(fadeCount);
					// 触发applyPropertyChange()方法，让JLayer重绘。
					// 在这行代码中，后面两个参数没有意义。
					firePropertyChange("crazyitFlag", 0 , 1);
					// 角度加3
					angle += 6;
					// 到达360后再从0开始
					if (angle >= 360) 
						angle = 0;
					if (isFadingOut)
					{
						if (--fadeCount == 0) 
						{
							isRunning = false;
							timer.stop();
						}
					}
					else if (fadeCount < FADE_LIMIT)
					{
						fadeCount++;
					}
				}
			}
		});
		timer.start();
	}
	// 控制停止等待（齿轮停止转动）的方法
	public void stop()
	{
		isFadingOut = true;
	}
	public void applyPropertyChange(PropertyChangeEvent pce
		, JLayer layer) 
	{
		// 控制JLayer重绘
		if (pce.getPropertyName().equals("crazyitFlag"))
		{
			layer.repaint();
		}
	}
}
public class WaitingJLayerSenior
{
	public void init() 
	{
		JFrame f = new JFrame("转动的“齿轮”");
		JPanel p = new JPanel();
		ButtonGroup group = new ButtonGroup();
		JRadioButton radioButton;
		// 创建3个RadioButton，并将它们添加成一组
		p.add(radioButton = new JRadioButton("网购购买", true));
		group.add(radioButton);
		p.add(radioButton = new JRadioButton("书店购买"));
		group.add(radioButton);
		p.add(radioButton = new JRadioButton("图书馆借阅"));
		group.add(radioButton);
		// 添加3个JCheckBox
		p.add(new JCheckBox("疯狂Java讲义"));
		p.add(new JCheckBox("疯狂Android讲义"));
		p.add(new JCheckBox("疯狂Ajax讲义"));
		p.add(new JCheckBox("轻量级Java EE企业应用"));
		JButton orderButton = new JButton("投票");
		p.add(orderButton);
		// 创建LayerUI对象
		final WaitLayerUI layerUI = new WaitLayerUI();
		// 使用layerUI来装饰指定JPanel组件
		JLayer<JComponent> layer = new JLayer<JComponent>(p, layerUI);
		// 设置5秒之后执行指定动作：调用layerUI的stop()方法
		final Timer stopper = new Timer(5000, new ActionListener()
		{
			public void actionPerformed(ActionEvent ae) 
			{
				layerUI.stop();
			}
		});
		// 设置stopper定时器只触发一次。
		stopper.setRepeats(false);
		// 为orderButton绑定事件监听器：单击该按钮时:调用layerUI的start()方法
		orderButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent ae) 
			{
				layerUI.start();
				// 如果stopper定时器已停止，启动它
				if (!stopper.isRunning()) 
				{
					stopper.start();
				}
			}
		});
		// 将装饰后的JPanel组件添加到容器中
		f.add(layer);
		f.setSize(300, 170);
		f.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		f.setVisible (true);
	}
	public static void main(String[] args) 
	{
		new WaitingJLayerSenior().init();
	}
}