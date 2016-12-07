
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
public class SetTest
{
	public static void main(String[] args) 
	{
		Set books = new HashSet();
		//添加一个字符串对象
		books.add(new String("疯狂Java讲义"));
		//再次添加一个字符串对象，
		//因为两个字符串对象通过equals方法比较相等，
		//所以添加失败，返回false
		boolean result = books.add(new String("疯狂Java讲义"));
		//下面输出看到集合只有一个元素
		System.out.println(result + "-->" + books);	
	}
}
