package spring.annotation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import spring.annotation.bean.Person;
import spring.annotation.service.BookService;

//配置类=配置文件
@Configuration
//扫描包，指定排除规则excludeFilters，包含规则includeFilters(禁用默认规则)
//@ComponentScan(value="spring.annotation",includeFilters = {
//		@Filter(type=FilterType.ANNOTATION,classes={Controller.class,Service.class})
//},useDefaultFilters=false)
@ComponentScans(value={
		@ComponentScan(value="spring.annotation",includeFilters = {
				//@Filter(type=FilterType.ANNOTATION,classes={Controller.class}),
				//@Filter(type=FilterType.ASSIGNABLE_TYPE,classes={BookService.class}),
				@Filter(type=FilterType.CUSTOM,classes={MyTypeFilter.class})
		},useDefaultFilters=false)
})
//FilterType.ANNOTATION:按注解
//FilterType.ASSIGNABLE_TYPE：按照给定的类型
//FilterType.REGEX:正则
//FilterType.CUSTOM：自定义规则
public class MainConfig {
	
	//在容器中注册一个bean，类型即为返回值的类型，id默认为方法名
	@Bean("person")
	public Person person01(){
		return new Person("lisi",20,"aa");
	}

}
