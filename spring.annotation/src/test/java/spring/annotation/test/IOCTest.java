package spring.annotation.test;

import java.util.Map;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;

import spring.annotation.bean.Person;
import spring.annotation.bean.Yellow;
import spring.annotation.config.MainConfig;
import spring.annotation.config.MainConfig2;

public class IOCTest {
	
	AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig2.class);
	
	@Test
	public void testImport(){
		printBeans(applicationContext);
		Yellow   yellow = applicationContext.getBean(Yellow.class);
		System.out.println(yellow);
		
		//工厂bean获取的是调用getObject创建的对象spring.annotation.bean.Color
		Object beanObject = applicationContext.getBean("colorFactoryBean");
		Object beanObject1 = applicationContext.getBean("colorFactoryBean");
		Object beanObject2 = applicationContext.getBean("colorFactoryBean");
		System.out.println("bean的类型"+beanObject.getClass());
		
		//&获取工厂bean本身
		Object beanObject3 = applicationContext.getBean("&colorFactoryBean");
		System.out.println("bean的类型"+beanObject3.getClass());
		
		//applicationContext.getBeansOfType(type)获取类本身及其子类类名
	}
	
	private void printBeans(AnnotationConfigApplicationContext applicationContext){
		String[] nameStrings =  applicationContext.getBeanDefinitionNames();
		for(String name : nameStrings){
			System.out.println(name);
		}
	}
	
	@Test
	public void test03(){
		String[] nameStrings =  applicationContext.getBeanNamesForType(Person.class);
		ConfigurableEnvironment environment = applicationContext.getEnvironment();
		//获取操作系统的名字
		String property  = environment.getProperty("os.name");
		System.out.println(property);
		
		for(String name : nameStrings){
			System.out.println(name);
		}
		
		Map<String, Person> persons = applicationContext.getBeansOfType(Person.class);
		System.out.println(persons);
	}
	
	@Test
	public void test02(){
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig2.class);
		String[] nameStrings =  applicationContext.getBeanDefinitionNames();
		for(String name : nameStrings){
			System.out.println(name);
		}
		//默认是单实例的
		System.out.println("ioc容器创建完成。。。");
		Object bean = applicationContext.getBean("person");
		Object bean2 = applicationContext.getBean("person");
		System.out.println(bean == bean2);
	}
	
	@Test
	public void test01(){
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
		String[] nameStrings =  applicationContext.getBeanDefinitionNames();
		for(String name : nameStrings){
			System.out.println(name);
		}
	}

}
