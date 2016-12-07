package org.crazyit.ioc.context;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.crazyit.ioc.context.exception.BeanCreateException;
import org.crazyit.ioc.xml.DocumentHolder;
import org.crazyit.ioc.xml.ElementLoader;
import org.crazyit.ioc.xml.ElementLoaderImpl;
import org.crazyit.ioc.xml.ElementReader;
import org.crazyit.ioc.xml.ElementReaderImpl;
import org.crazyit.ioc.xml.XmlDocumentHolder;
import org.crazyit.ioc.xml.autowire.Autowire;
import org.crazyit.ioc.xml.autowire.ByNameAutowire;
import org.crazyit.ioc.xml.autowire.NoAutowire;
import org.crazyit.ioc.xml.construct.DataElement;
import org.crazyit.ioc.xml.construct.RefElement;
import org.crazyit.ioc.xml.construct.ValueElement;
import org.crazyit.ioc.xml.property.PropertyElement;
import org.dom4j.Document;
import org.dom4j.Element;

/**
 * IoC容器抽象类
 * 
 * @author yangenxiong yangenxiong2009@gmail.com
 * @version  1.0
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br>Copyright (C), 2009-2010, yangenxiong
 * <br>This program is protected by copyright laws.
 */
public abstract class AbstractApplicationContext implements ApplicationContext {

	//元素加载对象
	protected ElementLoader elementLoader = new ElementLoaderImpl();
	
	//文档持有对象
	protected DocumentHolder documentHolder = new XmlDocumentHolder();
	
	//缓存的beans
	protected Map<String, Object> beans = new HashMap<String, Object>();
	
	//属性处理类
	protected PropertyHandler propertyHandler = new PropertyHandlerImpl();
	
	//创建bean对象的接口
	protected BeanCreator beanCreator = new BeanCreatorImpl();
	
	//Element元素读取类
	protected ElementReader elementReader = new ElementReaderImpl();
	
	
	
	/**
	 * 读取xml文件, 将各个元素缓存
	 * @param xmlPaths
	 */
	protected void setUpElements(String[] xmlPaths) {
		try {			
			URL classPathUrl = AbstractApplicationContext.class.getClassLoader().getResource(".");
			String classPath = java.net.URLDecoder.decode(classPathUrl.getPath(),"utf-8");
			for (String path : xmlPaths) {
				Document doc = documentHolder.getDocument(classPath + path);
				elementLoader.addElements(doc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 创建所有的bean实例, 延迟加载的不创建
	 */
	protected void createBeans() {
		Collection<Element> elements = elementLoader.getElements();
		for (Element e : elements) {
			boolean lazy = elementReader.isLazy(e);
			//如果不是延迟加载, 再判断是否单态
			if (!lazy) {
				String id = e.attributeValue("id");
				Object bean = this.getBean(id);
				if (bean == null) {
					//处单bean, 如果是单态的, 加到缓存中, 非单态则不创建
					handleSingleton(id);
				}
			}
		}
	}
	
	/**
	 * 处理bean, 如果是单态的, 则加到map中, 非单态, 则创建返回
	 * @param id
	 * @return
	 */
	protected Object handleSingleton(String id) {
		Object bean = createBean(id);;
		if (isSingleton(id)) {
			//单态的话, 放到map中
			this.beans.put(id, bean);
		}
		return bean;
	}
	
	/**
	 * 创建一个bean实例, 如果找不到该bean对应的配置文件的Element对象, 抛出异常
	 * @param id
	 * @return
	 */
	protected Object createBean(String id) {
		Element e = elementLoader.getElement(id);
		if (e == null) throw new BeanCreateException("element not found " + id);
		Object result = instance(e);
		System.out.println("创建bean: " + id);
		System.out.println("该bean的对象是: " + result);
		//设值注入, 先判断是否自动装配
		Autowire autowire = elementReader.getAutowire(e);
		if (autowire instanceof ByNameAutowire) {
			//使用名称自动装配
			autowireByName(result);
		} else if (autowire instanceof NoAutowire) {
			//不自动装配, 通过<property>属性
			setterInject(result, e);
		}
		return result;
	}
	
	/**
	 * 实例化一个bean, 如果该bean的配置有constructor-arg元素, 那么使用带参数的构造器
	 * @param e
	 * @return
	 */
	protected Object instance(Element e) {
		String className = elementReader.getAttribute(e, "class");
		//得到bean节点下面的constructor-arg节点
		List<Element> constructorElements = elementReader.getConstructorElements(e);
		//判断使用什么构造器进行创建(判断标准为bean元素下是否有constructor-arg子元素)
		if (constructorElements.size() == 0) {
			//没有constructor-arg子元素, 使用无参构造器
			return beanCreator.createBeanUseDefaultConstruct(className);
		} else {
			//有constructor-arg子元素, 使用有参数构造器, 构造注入参数
			List<Object> args = getConstructArgs(e);
			return beanCreator.createBeanUseDefineConstruce(className, args);
		}
	}
		
	/**
	 * 通过property元素为参数obj设置属性
	 * @param obj
	 * @param e
	 */
	protected void setterInject(Object obj, Element e) {
		List<PropertyElement> properties = elementReader.getPropertyValue(e);
		Map<String, Object> propertiesMap = getPropertyArgs(properties);
		propertyHandler.setProperties(obj, propertiesMap);
	}
	
	/**
	 * 以map的形式得到需要注入的参数对象, key为setter方法名(不要set), value为参数对象
	 * @param properties
	 * @return
	 */
	protected Map<String, Object> getPropertyArgs(List<PropertyElement> properties) {
		Map<String, Object> result = new HashMap<String, Object>();
		for (PropertyElement p : properties) {
			DataElement de = p.getDataElement();
			if (de instanceof RefElement) {
				//从容器中得到bean的实例, 再设置入map中
				result.put(p.getName(), this.getBean((String)de.getValue()));
			} else if (de instanceof ValueElement) {
				result.put(p.getName(), de.getValue());
			}
		}
		return result;
	}
	
	/**
	 * 得到一个bean里面配置的构造参数
	 * @param e
	 * @return
	 */
	protected List<Object> getConstructArgs(Element e) {
		List<DataElement> datas = elementReader.getConstructorValue(e);
		List<Object> result = new ArrayList<Object>();
		for (DataElement d : datas) {
			if (d instanceof ValueElement) {
				d = (ValueElement)d;
				result.add(d.getValue());
			} else if (d instanceof RefElement) {
				//如果是引用元素, 则直接调getBean去获取(获取不到则创建)
				d = (RefElement)d;
				String refId = (String)d.getValue();
				result.add(this.getBean(refId));
			}
		}
		return result;
	}
	
	/**
	 * 自动装配一个对象, 得到该bean的所有setter方法, 再从容器中查找对应的bean
	 * 例如, 如果bean中有一个setSchool(School)方法, 那么就去查名字为school的bean, 
	 * 再调用setSchool方法设入对象中
	 * @param obj
	 */
	protected void autowireByName(Object obj) {
		Map<String, Method> methods = propertyHandler.getSetterMethodsMap(obj);
		for (String s : methods.keySet()) {
			//得到对应的bean元素
			Element e = elementLoader.getElement(s);
			//没有对应的元素配置, 继续循环
			if (e == null) continue;
			//调用getBean方法返回bean
			Object bean = this.getBean(s);
			//执行对象的setter方法
			Method method = methods.get(s);
			propertyHandler.executeMethod(obj, bean, method);
		}
	}
	
	public boolean containsBean(String id) {
		//调用ElementLoader对象, 根据id得到对应的Element对象
		Element e = elementLoader.getElement(id);
		return (e == null) ? false : true;
	}

	public Object getBean(String id) {
		Object bean = this.beans.get(id);
		//如果获取不到该bean, 则创建
		if (bean == null) {
			//判断处理单态或者非单态的bean
			bean = handleSingleton(id);
		}
		return bean;
	}

	public boolean isSingleton(String id) {
		//使用ElementLoader方法获得对应的Element
		Element e = elementLoader.getElement(id);
		//使用ElementReader判断是否为单态
		return elementReader.isSingleton(e);
	}

	public Object getBeanIgnoreCreate(String id) {
		return this.beans.get(id);
	}
}
