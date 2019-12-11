package spring.annotation.ext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import spring.annotation.bean.Car;

/**
 * 扩展原理
 * 1、BeanPostProcessor：bean后置处理器，bean创建对象初始化前后进行拦截工作的
 *  BeanFactoryPostProcessor：beanFactory的后置处理器
 *     在beanFactory标准初始化之后调用；来定制和修改BeanFactory的内容
 *     所有的bean定义已经保存加载到beanFactory，但是bean实例还未创建对象
 *     
 *     1）ioc容器创建对象
 *     2）invokeBeanFactoryPostProcessor(beanFactory);执行BeanFactoryPostProcessor；
 *         如何找到所有的BeanFactoryPostProcessor并执行他们的方法
 *          1、直接在BeanFactory中找到所有类型是BeanFactoryPostProcessor的组件并执行他们的方法
 *          2、在初始化创建其他组件的前面
 * 
 *2、BeanDefinitionRegistryPostProcessor extends BeanFactoryPostProcessor
 *     postProcessBeanDefinitionRegistry()
 *     在所有bean定义信息将要被加载，bean实例还未创建
 *     优先于BeanFactoryPostProcessor执行，可利用BeanDefinitionRegistryPostProcessor给容器中再额外添加一些组件
 *     
 *     原理：
 *     1）创建ioc容器
 *     2）refresh() ->invokeBeanFactoryPostProcessor(beanFactory)
 *     3)从容器中获取到所有的BeanDefinitionRegistryPostProcessor组件，
 *          1、触发所有的postProcessBeanDefinitionRegistry()方法
 *          2、再触发postProcessBeanFactory()方法
 *     4）再从容器中找到BeanFactoryPostProcessor组件，然后触发postProcessBeanFactory()方法
 *     
 *3、ApplicationListener：监听容器中发布的事件
 *  public interface ApplicationListener<E extends ApplicationEvent>
 *  监听ApplicationEvent及其事件
 *  
 *  步骤：
 *  1）写一个监听器监听某个事件（ApplicationEvent及其子类）
 *    @EventListener
 *  2）把监听器加入到容器中
 *  3）只要容器中有相关事件的发布，就能监听到
 *      ContextRefreshedEvent：容器刷新完成（所有bean都完成创建）会发布这个事件
 *      ContextClosedEvent：容器关闭事件
 *  4）发布事件
 *      applicationContext.publishEvent()
 *      
 * 原理：
 *
 *    1、容器创建对象，refresh()
 *    2、finishRefresh()容器刷新完成
 *    3、publishEvent(new ContextRefreshedEvent(this))
 *       事件发布流程：
 *         获取到事件的多播器（派发器）：getApplicationEventMulticaster()
 *         multicastEvent派发事件：
 *              获取到所有的ApplicationListener
 *              如果有Executor，可以支持使用Executor进行异步派发
 *              否则，同步的方式直接执行listener方法：invokeListener(listener,event)
 *              拿到listener回调onApplicationEvent方法
 *              
 * 【事件多播器】    
 *    1）容器创建对象，refresh()
 *    2)initApplicationEventMulticaster();初始化
 *       先去容器中找有没有id=“applicationEventMulticaster”的组件，有直接getbean获取
 *       没有new SimpleApplicationEventMulticaster(beanFactory)并加入容器中，就可以在其他组件要派发事件的时候，自动注入applicationEventMulticaster
 *  
 * 【容器中有哪些组件】
 *    1）容器创建对象，refresh()
 *    2）registerListeners()
 *      从容器中拿到所有的监听器，把他们注册到applicationEventMulticaster中
 *         
 */

@ComponentScan("spring.annotation.ext")
@Configuration
public class ExtConfig {
	
	@Bean
	public Car car(){
		return new Car();
	}

}
