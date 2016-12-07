

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
public class InstanceofTest
{
	public static void main(String[] args) 
	{
		//声明hello时使用Object类，则hello的编译类型是Object，
		//Object是所有类的父类 , 但hello变量的实际类型是String
		Object hello = "Hello";
		//String是Object类的子类，可以进行instanceof运算。返回true。
		System.out.println("字符串是否是Object类的实例："
			+ (hello instanceof Object));
		//返回true。
		System.out.println("字符串是否是String类的实例：" 
			+ (hello instanceof String));
		//Math是Object类的子类，可以进行instanceof运算。返回false。
		System.out.println("字符串是否是Math类的实例：" 
			+ (hello instanceof Math));
		//String实现了Comparable接口，所以返回true。
		System.out.println("字符串是否是Comparable接口的实例：" 
			+ (hello instanceof Comparable));
		String a = "Hello";
		//String类既不是Math类，也不是Math类的父类，
		//所以下面代码编译无法通过
		System.out.println("字符串是否是Math类的实例："
			+ (a instanceof Math));
	}
}
