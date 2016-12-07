
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
public class MyUtils
{
	// 下面dest集合元素类型必须与src集合元素类型相同，或是其父类
	public static <T> T copy(Collection<? super T> dest 
		, Collection<T> src)
	{
		T last = null;
		for (T ele  : src)
		{
			last = ele;
			dest.add(ele);
		}
		return last;
	}
	public static void main(String[] args) 
	{
		List<Number> ln = new ArrayList<>();
		List<Integer> li = new ArrayList<>();
		li.add(5);
		// 此处可准确的知道最后一个被复制的元素是Integer类型
		// 与src集合元素的类型相同
		Integer last = copy(ln , li);    // ①
		System.out.println(ln);
	}
}

