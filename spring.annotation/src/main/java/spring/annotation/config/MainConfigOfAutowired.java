package spring.annotation.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import spring.annotation.bean.Car;
import spring.annotation.bean.Color;
import spring.annotation.dao.BookDao;

/**
 * 自动装配：
 *      Spring利用依赖注入（DI），完成对IOC容器中各个组件的依赖关系赋值
 *      1、@Autowired [spring定义的] 1）默认优先按照类型去容器中找对应的组件，如果找到多个相同类型的组件，再将属性的名称作为组件id去容器中查找
 *                    2）@Qualifier("bookDao"),指定需要装配的组件的id，而不是使用属性名
 *                    3）自动装配默认一定要将属性值赋值好，没有就会报错,可以使用@Autowired(required=false)，则没有就直接返回null，不会报错
 *                    4）@Primary 加上该注解，表示首选，表示让spring进行装配的时候，首选加了该注解的bean，也可以@Qualifier指定组件
 *      2、@Resource(JSR250)和@Inject(JSR330)[java规范注解]
 *         @Resource： 可以和@Autowired一样实现自动装配功能，默认是按照组件名称进行装配的，可以使用@Resource(name="bookDao2")更改
 *                    没有支持@Primary和@Autowired(required=false)功能
 *         @Inject:
 *               需要导入javax.inject的包，和Autowired的功能一样，没有required=false的功能
 *         
 * AutowiredAnnotationBeanPostProcessor完成自动装配功能
 * 
 * @Autowired：构造器，参数，方法，属性
 *     构造器：如果组件只有一个有参构造器，这个有参构造器@Autowired可以省略
 *     方法：@Bean+方法参数，参数从容器中获取；默认不写@Autowired
 *     
 *  自定义组件想要使用spring容器底层的一些组件（ApplicationContext，BeanFactory，xxx）
 *       自定义组件实现xxxAware接口：在创建对象的时候，会调用接口规定的方法
 *       xxxAware的功能使用xxxProcessor处理
 */
@Configuration
@ComponentScan({"spring.annotation.controller","spring.annotation.dao","spring.annotation.service","spring.annotation.bean"})
public class MainConfigOfAutowired {
	
	@Primary
	@Bean("bookDao2")
	public BookDao bookDao(){
		BookDao bookDao = new BookDao();
		bookDao.setLabel("2");
		return bookDao;
	}
	/**
	 * @Bean 标注的方法创建对象的时候，方法参数的值从容器中获取
	 * @param car
	 * @return
	 */
	@Bean
	public Color color(Car car){
		Color color = new Color();
		color.setCar(car);
		return color;
	}

}
