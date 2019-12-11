package spring.annotation.ext;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.stereotype.Component;

import spring.annotation.bean.Blue;
import spring.annotation.bean.Car;

@Component
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor{


	//BeanDefinitionRegistry bean定义信息的保存中心，BeanFactory就是按照BeanDefinitionRegistry里面保存的每一个bean定义信息创建bean实例
	public void postProcessBeanDefinitionRegistry(
			BeanDefinitionRegistry registry) throws BeansException {
		System.out.println("MyBeanDefinitionRegistryPostProcessor...bean的数量：" + registry.getBeanDefinitionCount());
		//RootBeanDefinition beanDefinition = new RootBeanDefinition(Blue.class);
		AbstractBeanDefinition beanDefinition =   BeanDefinitionBuilder.rootBeanDefinition(Car.class).getBeanDefinition();
		registry.registerBeanDefinition("hello", beanDefinition);
	}
	
	public void postProcessBeanFactory(
			ConfigurableListableBeanFactory beanFactory) throws BeansException {
		System.out.println("postProcessBeanFactory...bean的数量：" + beanFactory.getBeanDefinitionCount());
		
	}

}
