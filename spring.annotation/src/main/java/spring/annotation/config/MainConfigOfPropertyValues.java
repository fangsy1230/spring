package spring.annotation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import spring.annotation.bean.Person;

//@value
//1 直接赋值
//2 写SpEL #{}
//3 ${} 获取配置文件中的值
@Configuration
@PropertySource(value={"classpath:/person.properties"})
public class MainConfigOfPropertyValues {
	
	@Bean
	public Person person(){
		return new Person();
	}

}
