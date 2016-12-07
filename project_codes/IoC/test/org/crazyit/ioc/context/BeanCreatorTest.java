package org.crazyit.ioc.context;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.crazyit.ioc.context.object.BeanCreatorObject1;
import org.crazyit.ioc.context.object.BeanCreatorObject2;
import org.crazyit.ioc.context.object.interfaces.Implement;
import org.crazyit.ioc.context.object.interfaces.TestImplement;

public class BeanCreatorTest extends TestCase {

	BeanCreator creator;
	
	
	public void setUp() {
		creator = new BeanCreatorImpl();
		
	}
	
	public void tearDown() {
		creator = null;
	}
	
	public void testCreateBeanUseDefaultConstruct() {
		//无参数构造器创建实例
		String className = "org.crazyit.ioc.context.object.BeanCreatorObject1";
		BeanCreatorObject1 obj = (BeanCreatorObject1)
		creator.createBeanUseDefaultConstruct(className);
		assertNotNull(obj);
		assertNull(obj.getName());
		assertNull(obj.getValue());
		System.out.println(obj);
		System.out.println(obj.getName());
		System.out.println(obj.getValue());
		//测试找不到该类
		className = "abc";
		Exception ex = null;
		try {
			obj = (BeanCreatorObject1)
			creator.createBeanUseDefaultConstruct(className);
		} catch (Exception e) {
			ex = e;
		}
		assertNotNull(ex);
	}
	
	public void testCreateBeanUseDefineConstruce() {
		//有参数构造器创建实例
		String className = "org.crazyit.ioc.context.object.BeanCreatorObject2";
		List<Object> args = new ArrayList<Object>();
		args.add("yangenxiong");
		args.add("crazyit");
		
		BeanCreatorObject2 obj = (BeanCreatorObject2)
		creator.createBeanUseDefineConstruce(className, args);
		assertEquals(obj.getName(), "yangenxiong");
		assertEquals(obj.getValue(), "crazyit");
		System.out.println(obj.getName());
		System.out.println(obj.getValue());
		//测试参数类型不符合
		args.removeAll(args);
		args.add(new Integer(1));
		args.add(new Long(1000));
		Exception ex = null;
		try {
			obj = (BeanCreatorObject2)
			creator.createBeanUseDefineConstruce(className, args);
		} catch (Exception e) {
			ex = e;
		}
		assertNotNull(ex);
	}
	
//	public void testFindConstructor() throws Exception {
//		Constructor c = creator.findConstructor(TestImplement.class, new Class[]{Implement.class});
//		assertNotNull(c);
//		c = creator.findConstructor(TestImplement.class, new Class[]{Implement.class, Implement.class});
//		assertNotNull(c);
//	}
}
