
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
public class LinkedHashMapTest
{
	public static void main(String[] args) 
	{
		LinkedHashMap scores = new LinkedHashMap();
		scores.put("语文" , 80);
		scores.put("英文" , 82);
		scores.put("数学" , 76);
		//遍历scores里的所有的key-value对
		for (Object key : scores.keySet())
		{
			System.out.println(key + "------>"
				+ scores.get(key));
		}
	}
}
