package org.crazyit.ioc.context;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.crazyit.ioc.context.object.PropertyHandlerObject1;
import org.crazyit.ioc.context.object.School;
import org.crazyit.ioc.context.object.interfaces.Implement;
import org.crazyit.ioc.context.object.interfaces.TestImplement;

public class PropertyHandlerTest extends TestCase {

	PropertyHandler handler;

	protected void setUp() throws Exception {
		handler = new PropertyHandlerImpl();
	}

	protected void tearDown() throws Exception {
		handler = null;
	}
	
//	public void testGetSetterMethodName() {
//		Map<String, Object> properties = new HashMap<String, Object>();
//		properties.put("name", "yangenxiong");
//		properties.put("companyName", "crazyit");
//		List<String> methodNames = handler.getSetterMethodName(properties);
//		assertEquals(methodNames.size(), 2);
//		assertEquals(methodNames.get(0), "setName");
//		assertEquals(methodNames.get(1), "setCompanyName");
//	}

	
	public void testSetProperties() {
		PropertyHandlerObject1 obj = new PropertyHandlerObject1();
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("name", "yangenxiong");
		properties.put("age", 10);
		School school = new School();
		properties.put("school", school);
		properties.put("propertyBoolean", true);
		properties.put("propertyLong", 1000L);
		short propertyShort = 10;
		properties.put("propertyShort", propertyShort);
		float propertyFloat = 10.0F;
		properties.put("propertyFloat", propertyFloat);
		char propertyChar = 1;
		properties.put("propertyChar", propertyChar);
		byte propertyByte = 2;
		properties.put("propertyByte", propertyByte);
		double propertyDouble = 3.3;
		properties.put("propertyDouble", propertyDouble);
		
		PropertyHandlerObject1 newObj = (PropertyHandlerObject1)
		handler.setProperties(obj, properties);
		assertEquals(newObj.getName(), "yangenxiong");
		assertEquals(newObj.getAge(), 10);
		assertEquals(newObj.getSchool(), school);
		assertTrue(newObj.isPropertyBoolean());
		assertEquals(newObj.getPropertyLong(), 1000L);
		
		assertEquals(newObj.getPropertyShort(), propertyShort);
		assertEquals(newObj.getPropertyFloat(), propertyFloat);
		assertEquals(newObj.getPropertyChar(), propertyChar);
		assertEquals(newObj.getPropertyByte(), propertyByte);
		assertEquals(newObj.getPropertyDouble(), propertyDouble);
	}
	
//	public void testGetSetterMethodsList() {
//		PropertyHandlerObject1 obj = new PropertyHandlerObject1();
//		List<Method> setterNames = handler.getSetterMethodsList(obj);
//		assertEquals(setterNames.size(), 10);
//
//	}
	
	public void testGetSetterMethodsMap() {
		PropertyHandlerObject1 obj = new PropertyHandlerObject1();
		Map<String, Method> result = handler.getSetterMethodsMap(obj);
		assertEquals(result.keySet().size(), 10);
		assertNotNull(result.get("name"));
		assertNotNull(result.get("age"));
		assertNotNull(result.get("school"));
		assertNotNull(result.get("propertyBoolean"));
		assertNotNull(result.get("propertyLong"));
		assertNotNull(result.get("propertyShort"));
		assertNotNull(result.get("propertyDouble"));
		assertNotNull(result.get("propertyFloat"));
		assertNotNull(result.get("propertyChar"));
		assertNotNull(result.get("propertyByte"));
	}
	
	public void testExecuteMethod() throws Exception {
		PropertyHandlerObject1 obj = new PropertyHandlerObject1();
		School school = new School();
		Method m = obj.getClass().getMethod("setSchool", School.class);
		handler.executeMethod(obj, school, m);
		assertNotNull(obj.getSchool());
		assertEquals(school, obj.getSchool());
		System.out.println(obj.getSchool());
		System.out.println(school);
	}
	
//	public void testGetSetterMethod() throws Exception {
//		Method m = handler.getSetterMethod(TestImplement.class, "setInterface1", Implement.class);
//		assertNotNull(m);
//		Class[] cs = m.getParameterTypes();
//		assertEquals(cs[0].getName(), 
//				"org.crazyit.ioc.context.object.interfaces.Interface1");
//	}
}
