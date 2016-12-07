
import java.awt.*;
import java.awt.event.*;
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
public class CardLayoutTest
{
	Frame f = new Frame("测试窗口");
	String[] names = {"第一张" , "第二张" , "第三张" 
		, "第四张" , "第五张"};
	Panel pl = new Panel();
	public void init()
	{
		final CardLayout c = new CardLayout();
		pl.setLayout(c);
		for (int i = 0 ; i < names.length ; i++)
		{
			pl.add(names[i] , new Button(names[i]));
		}
		Panel p = new Panel();
		// 控制显示上一张的按钮
		Button previous = new Button("上一张");
		previous.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				c.previous(pl);
			}
		});
		// 控制显示下一张的按钮
		Button next = new Button("下一张");
		next.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				c.next(pl);
			}
		});
		// 控制显示第一张的按钮
		Button first = new Button("第一张");
		first.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				c.first(pl);
			}
		});
		// 控制显示最后一张的按钮
		Button last = new Button("最后一张");
		last.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				c.last(pl);
			}
		});
		// 控制根据Card名显示的按钮
		Button third = new Button("第三张");
		third.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				c.show(pl , "第三张");
			}
		});
		p.add(previous);
		p.add(next);
		p.add(first);
		p.add(last);
		p.add(third);
		f.add(pl);
		f.add(p , BorderLayout.SOUTH);
		f.pack();
		f.setVisible(true);
	}
	public static void main(String[] args) 
	{
		new CardLayoutTest().init();
	}
}
