
import java.util.*;
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
public class PerformanceTest
{
	public static void main(String[] args) 
	{
		//创建一个字符串数组
		String[] tst1 = new String[900000];
		//动态初始化数组元素
		for (int i = 0; i < 900000; i++)
		{
			tst1[i] = String.valueOf(i);
		}
		ArrayList al = new ArrayList();
		//将所有数组元素加入ArrayList集合中
		for (int i = 0; i < 900000 ; i++)
		{
			al.add(tst1[i]);
		}
		LinkedList ll = new LinkedList();
		//将所有数组元素加入LinkedList集合中
		for (int i = 0; i < 900000 ; i++)
		{
			ll.add(tst1[i]);
		}
		//迭代访问ArrayList集合的所有元素，并输出迭代时间
		long start = System.currentTimeMillis();
		for (Iterator it = al.iterator();it.hasNext() ; )
		{
			it.next();
		}
		System.out.println("迭代ArrayList集合元素的时间:" 
			+ (System.currentTimeMillis() - start));
		//迭代访问LinkedList集合的所有元素，并输出迭代时间
		start = System.currentTimeMillis();
		for (Iterator it = ll.iterator();it.hasNext() ; )
		{
			it.next();
		}
		System.out.println("迭代LinedList集合元素的时间:"
			+ (System.currentTimeMillis() - start));
	}
} 

