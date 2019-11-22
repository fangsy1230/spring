package spring.annotation.test;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.annotation.tx.TxConfig;
import spring.annotation.tx.UserService;

public class IOCTest_Tx {
	
	@Test
	public void test01(){
		//创建ioc容器
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TxConfig.class);
		UserService service = context.getBean(UserService.class);
		service.insertUser();
		context.close();
	}

}
