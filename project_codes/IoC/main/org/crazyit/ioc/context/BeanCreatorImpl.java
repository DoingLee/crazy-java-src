package org.crazyit.ioc.context;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import org.crazyit.ioc.context.exception.BeanCreateException;

/**
 * Bean创建实现类
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class BeanCreatorImpl implements BeanCreator {

	public Object createBeanUseDefaultConstruct(String className) {
		try {
			Class clazz = Class.forName(className);
			return clazz.newInstance();
		} catch (ClassNotFoundException e) {
			throw new BeanCreateException("class not found " + e.getMessage());
		} catch (Exception e) {
			throw new BeanCreateException(e.getMessage());
		}
	}
	
	/**
	 * 查找一个类的构造器
	 * @param clazz 类型
	 * @param argsClass 构造参数
	 * @return
	 */
	private Constructor getConstructor(Class clazz, Class[] argsClass) {
		try {
			Constructor constructor = clazz.getConstructor(argsClass);
			return constructor;
		} catch (NoSuchMethodException e) {
			return null;
		}
	}
	
	/**
	 * 查找一个类的构造器, 一个类中构造器参数有可能是原来构造器类型的子类
	 * @param clazz
	 * @param argsClass
	 * @return
	 * @throws NoSuchMethodException
	 */
	private Constructor findConstructor(Class clazz, Class[] argsClass)
		throws NoSuchMethodException {
		Constructor constructor = getConstructor(clazz, argsClass);
		if (constructor == null) {
			Constructor[] constructors = clazz.getConstructors();
			for (Constructor c : constructors) {
				//获取该类所有的构造器
				Class[] constructorArgsCLass = c.getParameterTypes();
				//参数数量与构造器的参数数量相同
				if (constructorArgsCLass.length == argsClass.length) {
					if (isSameArgs(argsClass, constructorArgsCLass)) {
						return c;
					}
				}
			}
		} else {
			return constructor;
		}
		throw new NoSuchMethodException("could not find any constructor");
	}
	
	/**
	 * 判断两个参数数组类型是否匹配
	 * @param argsClass
	 * @param constructorArgsCLass
	 * @return
	 */
	private boolean isSameArgs(Class[] argsClass, Class[] constructorArgsCLass) {
		for (int i = 0; i < argsClass.length; i++) {
			try {
				//将参数类型与构造器中的参数类型进行强制转换
				argsClass[i].asSubclass(constructorArgsCLass[i]);
				//循环到最后一个都没有出错, 表示该构造器合适
				if (i == (argsClass.length - 1)) {
					return true;
				}
			} catch (Exception e) {
				//有一个参数类型不符合, 跳出该循环
				break;
			}
		}
		return false;
	}
	
	public Object createBeanUseDefineConstruce(String className,
			List<Object> args) {
		Class[] argsClass = getArgsClasses(args);
		try {
			Class clazz = Class.forName(className);
			Constructor constructor = findConstructor(clazz, argsClass);
			return constructor.newInstance(args.toArray());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new BeanCreateException("class not found " + e.getMessage());
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			throw new BeanCreateException("no such constructor " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new BeanCreateException(e.getMessage());
		}
	}
	
	/**
	 * 获得一个Object的class
	 * @param obj
	 * @return
	 */
	private Class getClass(Object obj) {
		if (obj instanceof Integer) {
			return Integer.TYPE;
		} else if (obj instanceof Boolean) {
			return Boolean.TYPE;
		} else if (obj instanceof Long) {
			return Long.TYPE;
		} else if (obj instanceof Short) {
			return Short.TYPE;
		} else if (obj instanceof Double) {
			return Double.TYPE;
		} else if (obj instanceof Float) {
			return Float.TYPE;
		} else if (obj instanceof Character) {
			return Character.TYPE;
		} else if (obj instanceof Byte) {
			return Byte.TYPE;
		}
		return obj.getClass();
	}
	
	/**
	 * 得到参数的类型
	 * @param args
	 * @return
	 */
	private Class[] getArgsClasses(List<Object> args) {
		List<Class> result = new ArrayList<Class>();
		for (Object arg : args) {
			result.add(getClass(arg));
		}
		Class[] a = new Class[result.size()];
		return result.toArray(a);
	}

}
