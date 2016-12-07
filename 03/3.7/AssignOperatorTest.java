

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
public class AssignOperatorTest
{
	public static void main(String[] args) 
	{
		//为变量str赋值Java
		String str = "Java";
		//为变量pi赋值3.14
		double pi = 3.14;
		//为变量visited赋值true
		boolean visited = true;
		//将变量str的值赋给str2
		String str2 = str;
		int a;
		int b;
		int c;
		//通过为a, b , c赋值，四个变量的值都是7
		a = b = c = 7;
		//输出四个变量的值。
		System.out.println(a + "\n" + b + "\n" + c);

		double d1 = 12.34;
		//将表达式的值赋给d2
		double d2 = d1 + 5;
		//输出d2的值
		System.out.println(d2);

	}
}
