
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
public class PriorityQueueTest
{
	public static void main(String[] args) 
	{
		PriorityQueue pq = new PriorityQueue();
		//下面代码依次向pq中加入四个元素
		pq.offer(6);
		pq.offer(-3);
		pq.offer(9);
		pq.offer(0);
		//输出pq队列，并不是按元素的加入顺序排列，
		//而是按元素的大小顺序排列，输出[-3, 0, 9, 6]
		System.out.println(pq);
		//访问队列第一个元素，其实就是队列中最小的元素：-3
		System.out.println(pq.poll());
	}
}
