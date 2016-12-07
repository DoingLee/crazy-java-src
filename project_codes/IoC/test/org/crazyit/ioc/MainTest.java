package org.crazyit.ioc;

import org.crazyit.ioc.context.BeanCreatorTest;
import org.crazyit.ioc.context.PropertyHandlerTest;
import org.crazyit.ioc.context.XmlApplicationContextTest;
import org.crazyit.ioc.factory.XmlBeanFactoryTest;
import org.crazyit.ioc.xml.ElementLoaderTest;
import org.crazyit.ioc.xml.ElementReaderTest;
import org.crazyit.ioc.xml.XmlHolderTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses( { ElementReaderTest.class, ElementLoaderTest.class, 
	XmlHolderTest.class,BeanCreatorTest.class, PropertyHandlerTest.class,
	XmlApplicationContextTest.class, XmlBeanFactoryTest.class})
public class MainTest {
	
}
