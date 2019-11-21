package spring.annotation.condition;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import spring.annotation.bean.RainBow;

public class MyImportBeanDefinitionRegister implements ImportBeanDefinitionRegistrar{

	/**
	 * AnnotationMetadata:当前类的注解信息
	 * BeanDefinitionRegistry：把所需要的类需要添加到容器中的bean，调用
	 *                    beanDefinitionRegistry.registerBeanDefinition手动注册
	 */
	public void registerBeanDefinitions(AnnotationMetadata annotationMetadata,
			BeanDefinitionRegistry beanDefinitionRegistry) {
		
		boolean de1 = beanDefinitionRegistry.containsBeanDefinition("spring.annotation.bean.Red");
		boolean de2 = beanDefinitionRegistry.containsBeanDefinition("spring.annotation.bean.Blue");
		if(de1 && de2){
			RootBeanDefinition beanDefinition  = new RootBeanDefinition(RainBow.class);
			beanDefinitionRegistry.registerBeanDefinition("rainBow", beanDefinition);
		}
	}

}
