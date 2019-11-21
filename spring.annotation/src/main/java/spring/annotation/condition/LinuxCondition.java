package spring.annotation.condition;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

//判断是否linux系统
public class LinuxCondition implements Condition {

	/**
	 * ConditionContext:判断条件使用的上下文环境
	 * AnnotatedTypeMetadata：注释信息
	 */
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata annotatedTypeMetadata) {
		//1、获取ioc使用的beanFactory
		ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
		//2、获取类加载器
		ClassLoader classLoader = context.getClassLoader();
		//获取当前环境信息
		Environment environment = context.getEnvironment();
		//获取bean定义的注册类
		BeanDefinitionRegistry registry = context.getRegistry();
		//boolean b = registry.containsBeanDefinition("person");
		String property = environment.getProperty("os.name");
		if(property.contains("linux")){
			return true;
		}
		return false;
	}

}
