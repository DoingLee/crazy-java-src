
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
public class VectorTest
{
	public static void main(String[] args) 
	{
		Stack v = new Stack();
		//依次将三个元素push入"栈"
		v.push("疯狂Java讲义");
		v.push("轻量级Java EE企业应用实战");
		v.push("疯狂Android讲义");
		//输出：[疯狂Java讲义, 轻量级Java EE企业应用实战 , 疯狂Android讲义]
		System.out.println(v);
		//访问第一个元素，但并不将其pop出"栈"，输出：疯狂Android讲义
		System.out.println(v.peek());
		//依然输出：[疯狂Java讲义, 轻量级Java EE企业应用实战 , 疯狂Android讲义]
		System.out.println(v);
		//pop出第一个元素，输出：疯狂Android讲义
		System.out.println(v.pop());
		//输出：[疯狂Java讲义, 轻量级Java EE企业应用实战]
		System.out.println(v);
	}
}
