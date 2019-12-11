package spring.annotation.test;

import org.junit.Test;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import spring.annotation.ext.ExtConfig;

public class IOCTest_Ext {
	
	@Test
	public void test01(){
		//创建ioc容器
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ExtConfig.class);
		//发布事件
		context.publishEvent(new ApplicationEvent(new String("我发布的事件")) {
		});
	
		context.close();
	}

}
