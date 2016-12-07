

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

class Person
{
	//定义一个实例Field
	public String name;
	//定义一个类Field
	public static int eyeNum;
}
public class PersonTest
{
	public static void main(String[] args) 
	{
		//Person类已经初始化了，则eyeNum变量起作用了，输出0
		System.out.println("Person的eyeNum类Field值:"
			+ Person.eyeNum);
		//创建Person对象
		Person p = new Person();
		//通过Person对象的引用p来访问Person对象name实例Field
		//并通过实例访问eyeNum类Field
		System.out.println("p变量的name Field值是：" + p.name 
			+ " p对象的eyeNum Field值是：" + p.eyeNum);
		//直接为name实例Field赋值
		p.name = "孙悟空";
		//通过p访问eyeNum类Field，依然是访问Person的eyeNum类Field
		p.eyeNum = 2;
		//再次通过Person对象来访问name实例Field和eyeNum类Field
		System.out.println("p变量的name Field值是：" + p.name 
			+ " p对象的eyeNum Field值是：" + p.eyeNum);
		//前面通过p修改了Person的eyeNum，此处的Person.eyeNum将输出2
		System.out.println("Person的eyeNum类Field值:" + Person.eyeNum);
		Person p2 = new Person();
		//p2访问的eyeNum类Field依然引用Person类的，因此依然输出2
		System.out.println("p2对象的eyeNum类Field值:" + p2.eyeNum);
	}
}
