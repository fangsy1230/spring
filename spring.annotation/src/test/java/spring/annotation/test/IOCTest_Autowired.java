package spring.annotation.test;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import spring.annotation.bean.Boss;
import spring.annotation.bean.Car;
import spring.annotation.config.MainConfigOfAutowired;
import spring.annotation.config.MainConfigOfLifeCycle;
import spring.annotation.dao.BookDao;
import spring.annotation.service.BookService;

public class IOCTest_Autowired {
	
	@Test
	public void test01(){
		//创建ioc容器
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfigOfAutowired.class);
		
		BookService bookService = context.getBean(BookService.class);
		bookService.print();
		
//		BookDao bookDao = context.getBean(BookDao.class);
//		System.out.println(bookDao);
		
		Boss boss = context.getBean(Boss.class);
		System.out.println(boss);
		
		Car car = context.getBean(Car.class);
		System.out.println(car);
		context.close();
	}

}
