package spring.annotation.test;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import spring.annotation.aop.MathCalculator;

import spring.annotation.config.MainConfigOfAOP;

/**
 * 
 *    
 *
 *
 */
public class IOCTest_AOP {
	
	@Test
	public void test01(){
		//创建ioc容器
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfigOfAOP.class);
		
		//不要自己new，要用spring容器的
		MathCalculator mathCalculator = context.getBean(MathCalculator.class);
		mathCalculator.div(1, 0);
	
		context.close();
	}

}
