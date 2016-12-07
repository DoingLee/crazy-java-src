
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
public class GenericList
{
	public static void main(String[] args) 
	{
		// 创建一个只想保存字符串的List集合
		List<String> strList = new ArrayList<String>();  // ①
		strList.add("疯狂Java讲义");
		strList.add("疯狂Android讲义");
		strList.add("轻量级Java EE企业应用实战");
		// 下面代码将引起编译错误
		strList.add(5);    // ②
		for (int i = 0; i < strList.size() ; i++ )
		{
			// 下面代码无须强制类型转换
			String str = strList.get(i);    // ③
		}
	}
}

