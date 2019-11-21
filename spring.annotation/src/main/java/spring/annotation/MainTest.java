package spring.annotation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import spring.annotation.bean.Person;
import spring.annotation.config.MainConfig;

public class MainTest {
	
	public static void main(String[] args) {
		
//		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
		//根据bean的id获取
//		Person bean = (Person) applicationContext.getBean("person");
//		System.out.println(bean);
		
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
		//根据类型获取
		Person bean = applicationContext.getBean(Person.class);
		System.out.println(bean);
		
		String[] nameForType = applicationContext.getBeanNamesForType(Person.class);
		for(String name:nameForType){
			System.out.println(name);
		}
	}

}
