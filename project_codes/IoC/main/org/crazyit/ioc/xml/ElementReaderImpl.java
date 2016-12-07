package org.crazyit.ioc.xml;

import java.util.ArrayList;
import java.util.List;

import org.crazyit.ioc.xml.autowire.Autowire;
import org.crazyit.ioc.xml.autowire.ByNameAutowire;
import org.crazyit.ioc.xml.autowire.NoAutowire;
import org.crazyit.ioc.xml.construct.DataElement;
import org.crazyit.ioc.xml.construct.RefElement;
import org.crazyit.ioc.xml.construct.ValueElement;
import org.crazyit.ioc.xml.property.PropertyElement;
import org.dom4j.Element;

/**
 * 元素读取实现类
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public class ElementReaderImpl implements ElementReader {

	public String getAttribute(Element element, String name) {
		String value = element.attributeValue(name);
		return value;
	}

	public List<Element> getConstructorElements(Element element) {
		List<Element> children = element.elements();
		List<Element> result = new ArrayList<Element>();
		for (Element e : children) {
			if ("constructor-arg".equals(e.getName())) {
				result.add(e);
			}
		}
		return result;
	}

	public List<Element> getPropertyElements(Element element) {
		List<Element> children = element.elements();
		List<Element> result = new ArrayList<Element>();
		for (Element e : children) {
			if("property".equals(e.getName())) {
				result.add(e);
			}
		}
		return result;
	}

	public Autowire getAutowire(Element element) {
		String value = this.getAttribute(element, "autowire");
		String parentValue = this.getAttribute(element.getParent(), "default-autowire");
		if ("no".equals(parentValue)) {
			//根节点不需要自动装配
			if ("byName".equals(value)) {
				return new ByNameAutowire(value);
			}
			return new NoAutowire(value);
		} else if ("byName".equals(parentValue)) {
			//根节点byName
			if ("no".equals(value)) {
				return new NoAutowire(value);
			}
			return new ByNameAutowire(value);
		}
		return new NoAutowire(value);
	}

	public boolean isLazy(Element element) {
		String lazy = getAttribute(element, "lazy-init");
		Element parent = element.getParent();
		Boolean parentLazy = new Boolean(getAttribute(parent, "default-lazy-init"));
		if (parentLazy) {
			//根节点需要延迟加载
			if ("false".equals(lazy)) {
				return false;
			}
			return true;
		} else {
			//根节点不需要延迟加载
			if ("true".equals(lazy)) {
				return true;
			}
			return false;
		}
	}

	public boolean isSingleton(Element element) {
		Boolean singleton = new Boolean(getAttribute(element, "singleton"));
		return singleton;
	}

	public List<DataElement> getConstructorValue(Element element) {
		//调用本类中的getConstructorElements方法取得全部的constructor-arg元素
		List<Element> cons = getConstructorElements(element);
		List<DataElement> result = new ArrayList<DataElement>();
		for (Element e : cons) {
			//获得constructor-arg下的ref元素或者value元素(只有一个)
			List<Element> els = e.elements();
			DataElement dataElement = getDataElement(els.get(0));
			result.add(dataElement);
		}
		return result;
	}
	
	public List<PropertyElement> getPropertyValue(Element element) {
		List<Element> properties = getPropertyElements(element);
		List<PropertyElement> result = new ArrayList<PropertyElement>();
		for (Element e : properties) {
			//获得property下的ref元素或者value元素(只有一个)
			List<Element> els = e.elements();
			DataElement dataElement = getDataElement(els.get(0));
			String propertyNameAtt = this.getAttribute(e, "name");
			//将数据值和property元素的name属性封装成PropertyElement对象
			PropertyElement pe = new PropertyElement(propertyNameAtt, dataElement);
			result.add(pe);
		}
		return result;
	}

	/**
	 * 判断Element类型是ref还是value, 并将其封装成DataElement对象
	 * @param dataElement
	 * @return
	 */
	private DataElement getDataElement(Element dataElement) {
		String name = dataElement.getName();
		if ("value".equals(name)) {
			String classTypeName = dataElement.attributeValue("type");
			String data = dataElement.getText();
			return new ValueElement(getValue(classTypeName, data));
		} else if("ref".equals(name)) {
			return new RefElement(this.getAttribute(dataElement, "bean"));
		}
		return null;
	}
	
	/**
	 * 判断className的类型是否为基础类型, 是的话进行转换
	 * @param className
	 * @param data
	 * @return
	 */
	private Object getValue(String className, String data) {
		if (isType(className, "Integer")) {
			return Integer.parseInt(data);
		} else if (isType(className, "Boolean")) {
			return Boolean.valueOf(data);
		} else if (isType(className, "Long")) {
			return Long.valueOf(data);
		} else if (isType(className, "Short")) {
			return Short.valueOf(data);
		} else if (isType(className, "Double")) {
			return Double.valueOf(data);
		} else if (isType(className, "Float")) {
			return Float.valueOf(data);
		} else if (isType(className, "Character")) {
			return data.charAt(0);
		} else if (isType(className, "Byte")) {
			return Byte.valueOf(data);
		} else {
			return data;
		}
	}
	
	private boolean isType(String className, String type) {
		if (className.indexOf(type) != -1) return true;
		return false;
	}
}
