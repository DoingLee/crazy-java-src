package org.crazyit.ioc.context;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.crazyit.ioc.context.exception.BeanCreateException;
import org.crazyit.ioc.context.exception.PropertyException;

/**
 * 属性处理实现类
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
@SuppressWarnings("unchecked")
public class PropertyHandlerImpl implements PropertyHandler {

	
	public Object setProperties(Object obj, Map<String, Object> properties) {
		Class clazz = obj.getClass();
		try {
			for (String key : properties.keySet()) {
				String setterName = getSetterMethodName(key);
				Class argClass = getClass(properties.get(key));
				Method setterMethod = getSetterMethod(clazz, setterName, argClass);
				setterMethod.invoke(obj, properties.get(key));
			}
			return obj;
		} catch (NoSuchMethodException e) {
			throw new PropertyException("setter method not found " + e.getMessage());
		} catch (IllegalArgumentException e) {
			throw new PropertyException("wrong argument " + e.getMessage());
		} catch (Exception e) {
			throw new PropertyException(e.getMessage());
		}
	}
	
	/**
	 * 方法集合中寻找参数类型是interfaces其中一个的方法
	 * @param argClass 参数类型
	 * @param methods 方法集合
	 * @return
	 */
	private Method findMethod(Class argClass, List<Method> methods) {
		for (Method m : methods) {
			//判断参数类型与方法的参数类型是否一致
			if (isMethodArgs(m, argClass)) {
				return m;
			}
		}
		return null;
	}
	
	/**
	 * 判断参数类型(argClass)是否是该方法(m)的参数类型
	 * @param m
	 * @param argClass
	 * @return
	 */
	private boolean isMethodArgs(Method m, Class argClass) {
		Class[] c = m.getParameterTypes();
		if (c.length == 1) {
			try {
				//将参数类型(argClass)与方法中的参数类型进行强制转换, 不抛异常返回该Method
				argClass.asSubclass(c[0]);
				return true;
			} catch (ClassCastException e) {
				return false;
			}
		}
		return false;
	}
	
	/**
	 * 根据方法名和参数类型得到方法, 如果没有该方法返回null
	 * @param objClass
	 * @param methodName
	 * @param argClass
	 * @return
	 */
	private Method getMethod(Class objClass, String methodName, Class argClass) {
		try {
			Method method = objClass.getMethod(methodName, argClass);
			return method;
		} catch (NoSuchMethodException e) {
			return null;
		}
	}
	
	private Method getSetterMethod(Class objClass, String methodName, 
			Class argClass) throws NoSuchMethodException {
		//使用原类型获得方法, 如果没有找到该方法, 则得到null
		Method argClassMethod = getMethod(objClass, methodName, argClass);
		//如果找不到原类型的方法, 则找该类型所实现的接口
		if (argClassMethod == null) {
			//得到所有名字为methodName的方法
			List<Method> methods = getMethods(objClass, methodName);
			Method method = findMethod(argClass, methods);
			if (method == null) {
				//找不到任何方法
				throw new NoSuchMethodException(methodName);
			}
			return method;
		} else {
			return argClassMethod;
		}
	}
	
	/**
	 * 得到所有名字为methodName并且只有一个参数的方法
	 * @param objClass
	 * @param methodName
	 * @return
	 */
	private List<Method> getMethods(Class objClass, String methodName) {
		List<Method> result = new ArrayList<Method>();
		for (Method m : objClass.getMethods()) {
			if (m.getName().equals(methodName)) {
				//得到方法的所有参数, 如果只有一个参数, 则添加到集合中
				Class[] c = m.getParameterTypes();
				if (c.length == 1) {
					result.add(m);
				}
			}
		}
		return result;
	}
	
	private List<Method> getSetterMethodsList(Object obj) {
		Class clazz = obj.getClass();
		Method[] methods = clazz.getMethods();
		List<Method> result = new ArrayList<Method>();
		for (Method m : methods) {
			if (m.getName().startsWith("set")) {
				result.add(m);
			}
		}
		return result;
	}
	
	public Map<String, Method> getSetterMethodsMap(Object obj) {
		List<Method> methods = getSetterMethodsList(obj);
		Map<String, Method> result = new HashMap<String, Method>();
		for (Method m : methods) {
			String propertyName = getMethodNameWithOutSet(m.getName());
			result.put(propertyName, m);
		}
		return result;
	}
	
	public void executeMethod(Object object, Object argBean, Method method) {
		try {
			//获取方法的参数类型
			Class[] parameterTypes = method.getParameterTypes();
			//如果参数数量不为1，则不执行该方法
			if (parameterTypes.length == 1) {
				//如果参数类型不一样, 则不执行方法
				if (isMethodArgs(method, parameterTypes[0])) {
					method.invoke(object, argBean);
				}
			}
		} catch (Exception e) {
			throw new BeanCreateException("autowire exception " + e.getMessage());
		}
	}

	/**
	 * 将setter方法还原, setName作为参数, 得到name
	 * @param methodName
	 * @return
	 */
	private String getMethodNameWithOutSet(String methodName) {
		String propertyName = methodName.replaceFirst("set", "");
		String firstWord = propertyName.substring(0, 1);
		String lowerFirstWord = firstWord.toLowerCase();
		return propertyName.replaceFirst(firstWord, lowerFirstWord);
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

//	public List<String> getSetterMethodName(Map<String, Object> properties) {
//		Set<String> keys = properties.keySet();
//		List<String> methodNames = new ArrayList<String>();
//		for (String key : keys) {
//			methodNames.add("set" + upperCaseFirstWord(key));
//		}
//		return methodNames;
//	}
	
	/**
	 * 返回一个属性的setter方法
	 * @param propertyName
	 * @return
	 */
	private String getSetterMethodName(String propertyName) {
		return "set" + upperCaseFirstWord(propertyName);
	}
	
	/**
	 * 将参数s的首字母变为大写
	 * @param key
	 * @return
	 */
	private String upperCaseFirstWord(String s) {
		String firstWord = s.substring(0, 1);
		String upperCaseWord = firstWord.toUpperCase();
		return s.replaceFirst(firstWord, upperCaseWord);
	}

}
