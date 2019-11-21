package spring.annotation.test;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import spring.annotation.bean.Person;
import spring.annotation.config.MainConfigOfLifeCycle;
import spring.annotation.config.MainConfigOfPropertyValues;

public class IOCTest_property {
	
	AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfigOfPropertyValues.class);
	@Test
	public void test01(){
		//创建ioc容器
		System.out.println("容器创建完成");
		printBeans(context);
		
		
		Person person = (Person)context.getBean("person");
		System.out.println(person);
		
		ConfigurableEnvironment configurableEnvironment = context.getEnvironment();
		String pp = configurableEnvironment.getProperty("person.nickname");
		System.out.println(pp);
		context.close();
	}
	
	private void printBeans(AnnotationConfigApplicationContext applicationContext){
		String[] nameStrings =  applicationContext.getBeanDefinitionNames();
		for(String name : nameStrings){
			System.out.println(name);
		}
	}

}
