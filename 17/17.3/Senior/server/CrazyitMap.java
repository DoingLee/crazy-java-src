
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
// 扩展HashMap类，CrazyitMap类要求value也不可重复
public class CrazyitMap<K,V> extends HashMap<K,V>
{
	// 根据value来删除指定项
	public void removeByValue(Object value) 
	{
		for (Object key : keySet())
		{
			if (get(key) == value)
			{
				remove(key);
				break;
			}
		}
	}
	// 获取所有value组成的Set集合
	public Set<V> valueSet() 
	{
		Set<V> result = new HashSet<V>();
		// 遍历所有key组成的集合
		for (K key : keySet())
		{
			// 将每个key对应的value添加到result集合中
			result.add(get(key));
		}
		return result;
	}
	// 根据value查找key。
	public K getKeyByValue(V val) 
	{
		// 遍历所有key组成的集合
		for (K key : keySet())
		{
			// 如果指定key对应的value与被搜索的value相同
			// 则返回对应的key
			if (get(key).equals(val) 
				&& get(key) == val)
			{
				return key;
			}
		}
		return null;
	}
	// 重写HashMap的put方法，该方法不允许value重复
	public V put(K key,V value)
	{
		// 遍历所有value组成的集合
		for (V val : valueSet() )
		{
			// 如果指定value与试图放入集合的value相同
			// 则抛出一个RuntimeException异常
			if (val.equals(value)
				&& val.hashCode()== value.hashCode())
			{
				throw new RuntimeException(
					"MyMap实例中不允许有重复value!"); 
			}
		}
		return super.put(key , value);
	}
}
