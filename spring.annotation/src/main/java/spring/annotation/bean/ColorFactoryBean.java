package spring.annotation.bean;

import org.springframework.beans.factory.FactoryBean;

//创建一个spring定义的工厂bean
public class ColorFactoryBean implements FactoryBean<Color>{

	//返回一个color对象，这个对象会添加到容器中
	public Color getObject() throws Exception {
		System.out.println("ColorFactoryBean++++");
		return new Color();
	}

	public Class<?> getObjectType() {
		return Color.class;
	}

	//控制单例
	//true：单实例
	public boolean isSingleton() {
		return false;
	}

}
