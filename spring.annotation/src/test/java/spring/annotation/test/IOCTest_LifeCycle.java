package spring.annotation.test;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import spring.annotation.config.MainConfigOfLifeCycle;

public class IOCTest_LifeCycle {
	
	@Test
	public void test01(){
		//创建ioc容器
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfigOfLifeCycle.class);
		System.out.println("容器创建完成");
		printBeans(context);
		//context.getBean("car");
		context.close();
	}
	
	private void printBeans(AnnotationConfigApplicationContext applicationContext){
		String[] nameStrings =  applicationContext.getBeanDefinitionNames();
		for(String name : nameStrings){
			System.out.println(name);
		}
	}


}
