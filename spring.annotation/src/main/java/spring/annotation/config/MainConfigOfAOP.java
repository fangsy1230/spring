package spring.annotation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import spring.annotation.aop.LogAspects;
import spring.annotation.aop.MathCalculator;
/**
 * AOP：【动态代理】
 *    指在程序运行期间动态的将某段代码切入到指定方法指定位置进行运行的编程方式
 *    
 * 1、导入aop模块（spring-aspects）
 * 2、定义一个业务逻辑类
 * 3、定义一个日志切面类:需要动态感知业务逻辑类运行到哪里然后执行
 *       通知方法：
 *            前置通知(@Before)：在目标方法运行之前运行
 *            后置通知(@After)：在目标方法运行之后运行(无论方法正常结束还是异常结束)
 *            返回通知(@AfterReturning)：在目标方法正常返回之后运行
 *            异常通知(@AfterThrowing)：在目标方法运行异常之后运行
 *            环绕通知(@Around)：动态代理，手动推进目标方法运行（ProceedingJoinPoint.procced()）
 *                            目标方法执行之前和之后都可以执行
 *                            ProceedingJoinPoint-连接点对象：封装了(特定)具体业务的方法信息
 *4、给切面类的目标方法标注何时何地运行（通知方法注解）
 *5、将切面类和业务逻辑类都加到容器中
 *6、必须告诉spring哪个类是切面类@Aspect
 *【7】、给配置类中加@EnableAspectJAutoProxy[开启基于注解的aop模式]
 * 
 * 
 * AOP原理：[看给容器中注册了什么组件，这个组件什么时候工作，这个组件工作的功能]
 * @EnableAspectJAutoProxy ： 
 *     @Improt(AspectJAutoProxyRegistrar.class)：给容器中导入AspectJAutoProxyRegistrar
 *     利用AspectJAutoProxyRegistrar给容器中注册一个internalAutoProxyCreator->AnnotationAwareAspectJAutoProxyCreator
 * 
 *====================创建和注册AnnotationAwareAspectJAutoProxyCreator过程================
 *AnnotationAwareAspectJAutoProxyCreator
 *    AnnotationAwareAspectJAutoProxyCreator
 *           -》AspectJAwareAdvisorAutoProxyCreator
 *            -》AbstractAdvisorAutoProxyCreator
 *             -》AbstractAutoProxyCreator                              
 *     最终实现了SmartInstantiationAwareBeanPostProcessor,BeanFactoryAware接口
 *     在bean初始化前后工作，并且自动装配了BeanFactory
 *     AbstractAutoProxyCreator.setBeanFactory()
 *     AbstractAutoProxyCreator的后置处理器
 *     
       流程：
 *    1）传入配置类，创建ioc容器
 *    2）注册配置类，调用refresh()刷新容器-》registerBeanPostProcessors(beanFactory);注册bean的后置处理器，拦截bean的创建：
 *        *先获取ioc容器中已经定义了的需要创建对象的所有BeanPostProcessor
 *        * 给容器中加入别的BeanPostProcessor
 *        *优先注册实现了PriorityOrdered接口的BeanPostProcessor
 *        * 再注册实现了Ordered接口的BeanPostProcessor
 *        * 注册没实现优先级接口的BeanPostProcessor
 *        * 注册BeanPostProcessor，首次是创建BeanPostProcessor，保存在容器中：
 *        
 *        +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 *               1、创建bean实例
 *               2、populateBean，给bean的属性赋值
 *               3、initializeBean，初始化bean
 *                    invokeAwareMethods():处理aware接口
 *                    applyBeanPostProcessorBeforeInitialization():应用后置处理器的BeforeInitialization()
 *                    invokeInitMethods():执行自定义的初始化方法
 *                    applyBeanPostProcessorAfterInitialization():应用后置处理器的AfterInitialization()
 *               4、成功创建BeanPostProcessor(AnnotationAwareAspectJAutoProxyCreator)
 *         +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 *         
 *         * 把BeanPostProcessor注册到BeanFactory中：beanFatory.addBeanPostProcessor(postProcessor)
 *===================================================================================================
 *AnnotationAwareAspectJAutoProxyCreator-》InstantiationAwareBeanPostProcessor（使用postBeforeInstantiation类型的后置处理器）
 *    3）finishBeanFactoryInitialization(beanFactory):完成BeanFatory初始化工作，创建剩下的单实例bean
 *        1.遍历获取容器中所有的bean，依次创建对象
 *            getBean() -> doGetBean() ->getSingleton()
 *        2.创建bean，
 *           *先从缓存中获取当前bean，如果能获取到，说明之前被创建过，直接使用；否则再创建；（只要创建好的bean都会被缓存）
 *           * createBean()
 *           【BeanPostProcessor是在bean对象创建完成初始化前后调用的】
 *           【InstantiationAwareBeanPostProcessor是在创建Bean实例之前先尝试用后置处理返回对象】
 *               1）resolveBeforeInstantiation(beanName,mbdToUse):解析BeforeInstantiation，希望后置处理器在此能返回一个代理对象
 *               如果能返回代理对象，不能则2）
 *                   *后置处理器先尝试返回对象： 
 *                        bean = applyBeanPostProcessorBeforeInstantiation();  :拿到所有的后置处理器，如果是
 *                                                                              InstantiationAwareBeanPostProcessor类型
 *                                                                              就执行PostBeforeInstantiation()
 *                        if(bean != null){
 *                           bean = applyBeanPostProcessorAfterInstantiation
 *                        }
 *               2）doCreateBean(),真正的去创建一个bean实例，过程如以上+++中间部分
 * 
 * 
 * 
 * MathCalculator和LogAspects的创建（AnnotationAwareAspectJAutoProxyCreator【InstantiationAwareBeanPostProcessor】）
 * 1、postProcessBeforeInitialization
 *      1）判断当前bean是否在advisedBeans中
 *      2）判断当前bean是否是基础类型Advice，Pointcut，Advisor等或者是否使切面（@Aspect）
 *      3）是否需要跳过
 *          获取候选的增强器（切面里面的通知方法）
 *          每一个封装的通知方法的增强器都是InstantiationModelAwarePointcutAdvisor
 *          判断每一个增强器是否使AspectJpointcutAdvisor类型的，返回true
 * 2.创建对象
 * 3.postProcessAfterInitialization
 *     return wrapIfNecessary() 包装如果需要的话
 *     1）获取当前bean的所有增强器（通知方法）
 *        找到候选的左右增强器（找到哪些通知方法是需要切入当前bean方法的）
 *        获取到能在bean中使用的增强器
 *        给增强器排序
 *     2）保存当前bean到advisedBeans中
 *     3）如果当前bean需要增强，创建当前bean的代理对象
 *         获取所有增强器
 *         保存到proxyFactory中
 *         创建代理对象：spring自动决定
 *            JdkDynamicAopProxy JDK动态代理
 *            ObjenesisCglibAopProxy  cglib动态代理
 *      4）给容器中返回当前组件使用了cglib增强了的代理对象
 *      5）以后容器中获取到的就是这个组件的代理对象，执行目标方法的时候，代理对象就会执行通知方法的流程
 *      
 * 目标方法的执行（div）
 *   容器中保存了组件的代理对象（cglib增强后的对象），这个对象保存了详细信息（增强器，目标方法等）
 *   1）CglibAopProxy.intercept()，拦截目标方法的执行
 *   2）根据ProxyFactory对象获取将要执行的目标方法拦截器链
 *      List<Object> chain = this.advised.getInterceptorsAndDynamicInterceptor
 *      1)List<Object> interceptorList保存所有拦截器（5个）
 *         一个默认的ExposeInvocationInterceptor和4个增强器
 *   3）如果没有拦截器链，直接执行目标方法
 *      拦截器链（每一个通知方法又被包装成方法拦截器，利用MethodInterceptor机制）
 *   4）如果有拦截器连，把需要执行的目标对象，目标方法，拦截器等信息传入创建一个cglibMethodInvocation对象
 *      调用XXXMethodInterceptor.proceed()
 *   5)拦截器链触发过程：
 *      1）如果没有拦截器，或者拦截器的索引和拦截器数组-1大小一样（指定到了最后一个拦截器）
 *      2）链式获取每一个拦截器，拦截器执行invoke方法，每一个拦截器等待下一个拦截器执行完成返回是再执行
 * 
 * 总结：
 *   @EnableAspectJAutoProxy 开启aop功能，给容器中注册了一个AnnotationAwareAspectJAutoProxyCreator组件
 *   AnnotationAwareAspectJAutoProxyCreator是一个后置处理器
 *   容器的创建流程：
 *     1、registerBeanPostProcessors()注册后置处理器，创建AnnotationAwareAspectJAutoProxyCreator
 *     2、finishBeanFactoryInitialization()初始化剩下的实例bean
 *        创建业务逻辑类和切面类
 *        AnnotationAwareAspectJAutoProxyCreator拦截组件创建过程
 *        组件创建完成后，判断是否需要增强
 *          是：切面的通知方法，包装成增强器，给业务逻辑创建一个代理对象
 *     3、执行目标方法：
 *        1）代理对象执行目标方法
 *        2）CglibAopProxy.intercept()
 *           得到目标方法的拦截器链（增强器包装成拦截器MethodInterceptor）
 *           利用拦截器连的链式机制，依次进入每一个拦截器执行
 *           效果：
 *             正常执行：前置通知-》目标方法-》后置通知-》返回通知
 *             出现异常：前置通知-》目标方法-》后置通知-》异常通知
 */
@EnableAspectJAutoProxy
@Configuration
public class MainConfigOfAOP {
	
	@Bean
	public MathCalculator calculator(){
		return new MathCalculator();
	}
	
	@Bean
	public LogAspects logAspects(){
		return new LogAspects();
	}

}
