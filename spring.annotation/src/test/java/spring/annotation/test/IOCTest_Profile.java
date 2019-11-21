package spring.annotation.test;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.annotation.config.MainConfigOfProfile;


public class IOCTest_Profile {
	
	@Test
	public void test01(){
		/**
		 * 1、创建一个ApplicationContext
		 * 2、设置需要激活的环境
		 * 3、注册主配置类
		 * 4、启动刷新容器
		 */
		AnnotationConfigApplicationContext context = 
				new AnnotationConfigApplicationContext();
		context.getEnvironment().setActiveProfiles("dev");
		context.register(MainConfigOfProfile.class);
		context.refresh();
		
		String[] names  = context.getBeanNamesForType(DataSource.class);
		for(String name:names){
			System.out.println(name);
		}
	
	}

}
