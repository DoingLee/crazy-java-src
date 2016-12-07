
import static java.lang.System.*;
import static java.lang.Math.*;
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

public class StaticImportTest
{
	public static void main(String[] args) 
	{
		//out是java.lang.System类的静态Field，代表标准输出
		//PI是java.lang.Math类的静态Field，表示π常量
		out.println(PI);
		//直接调用Math类的sqrt静态方法
		out.println(sqrt(256));
	}
}
