
import java.util.concurrent.*;
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

// 实现Runnable接口来定义一个简单的线程类
class MyThread implements Runnable
{
	public void run()
	{
		for (int i = 0; i < 100 ; i++ )
		{
			System.out.println(Thread.currentThread().getName()
				+ "的i值为:" + i);
		}
	}
}
public class ThreadPoolTest
{
	public static void main(String[] args) 
		throws Exception
	{
		// 创建一个具有固定线程数（6）的线程池
		ExecutorService pool = Executors.newFixedThreadPool(6);
		// 向线程池中提交两个线程
		pool.submit(new MyThread());
		pool.submit(new MyThread());
		// 关闭线程池
		pool.shutdown();
	}
}

