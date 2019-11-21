package spring.annotation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import spring.annotation.bean.Car;

/**
 * bean的生命周期（bean创建--初始化--销毁） 初始化和销毁方法可以自定义： 
 * 1、指定初始化和销毁方法
 * 指定init-method和destory-method 初始化：对象创建完成并将属性赋值好，调用初始化方法
 * 销毁：单实例容器关闭的时候，多实例容器不会管理这个bean，容器不会调用销毁方法
 * 2、通过让bean实现InitializingBean(初始化管理)，DisposableBean(销毁) 
 * 3、可以使用JSR250:
 * @PostConstruct 在bean创建完成并且属性赋值完成，初始化
 * @PreDestory 容器关闭的时候
 *  4、BeanPostProcessor,bean的后置处理器：在bean初始化前后进行一些处理工作
 *             postProcessBeforeInitialization：在初始化之前
 *             postProcessAfterInitialization：在初始化之后 BeanPostProcessor原理：
 *             遍历得到容器中所有的BeanPostProcessor
 *             ，挨个执行beforeInitialization，一旦返回null，就不会执行后面的BeanPostProcessor
 *             populateBean(beanName, mbd, instanceWrapper);//给bean进行属性赋值 {
 *             applyBeanPostProcessorsBeforeInitialization(wrappedBean,
 *             beanName); invokeInitMethods(beanName, wrappedBean,
 *             mbd);执行自定义初始化方法
 *             applyBeanPostProcessorsAfterInitialization(wrappedBean,
 *             beanName); }
 * 
 * 
 *             Spring底层对BeanPostProcessor的使用:
 *             bean赋值，applicationaware，@Autowired,生命周期注解功能，@Async....
 * 
 */
@ComponentScan("spring.annotation.bean")
@Configuration
public class MainConfigOfLifeCycle {

	// @Scope("prototype")
	@Bean(initMethod = "init", destroyMethod = "destory")
	public Car car() {
		return new Car();
	}

}
