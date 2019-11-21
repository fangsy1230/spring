package spring.annotation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;

import spring.annotation.bean.Color;
import spring.annotation.bean.ColorFactoryBean;
import spring.annotation.bean.Person;
import spring.annotation.bean.Red;
import spring.annotation.condition.LinuxCondition;
import spring.annotation.condition.MyImportBeanDefinitionRegister;
import spring.annotation.condition.MyImportSelector;
import spring.annotation.condition.WindowsCondition;

//也可以放在类上面，满足当前条件，这个类中配置的所有bean注册才能生效
@Conditional({WindowsCondition.class})
@Configuration
@Import({Color.class,Red.class,MyImportSelector.class,MyImportBeanDefinitionRegister.class})//导入组件，id默认为组件的全类名
public class MainConfig2 {
	
	//默认是单实例的
	/**
	 * prototype:多实例的，ioc容器启动不会调用方法创建对象放在容器中，每次getbean的时候才会创建
	 * singleton：单实例的（默认）:ioc启动会调用方法创建对象放到容器中，以后每次获取之间从容器中拿
	 * request：同一次请求创建一个实例
	 * session：同一次session创建一个实例
	 * 
	 * 
	 * @Lazy
	 * 懒加载：单实例bean：默认在容器启动的时候创建对象
	 *       懒加载就是容器启动的时候不创建bean，获取的时候再创建
	 */
	//@Scope("prototype")
	@Lazy
	@Bean("person")
	public Person person(){
		System.out.println("给容器中添加person。。。。");
		return new Person("张三",25,"aa");
	}
	
	/**
	 * @Conditional,按照一定条件的条件进行判断，满足条件给容器中注册bean
	 * 
	 * 如果系统是linux，给容器中注册（“bill”），如果是win，则注册("linus")
	 */
	@Conditional({WindowsCondition.class})
	@Bean("bill")
	public Person person01(){
		return new Person("Bill",62,"aa");
	}
	
	@Conditional({LinuxCondition.class})
	@Bean("linus")
	public Person person02(){
		return new Person("linus",48,"aa");
	}
	
	/**
	 * 给容器中注册组件：
	 *    1、包扫描+组件标注注解（@Controller，@service，@Repository,@Component） 局限于自己写的类
	 *    2、@Bean（导入第三方包）
	 *    3、@Import[快速给容器中导入一个组件]
	 *        1）@Import(导入的组件)：容器中就会自动注册这个组件。id默认为全类名
	 *        2）ImportSelector：返回需要导入的组件的全类名的数组
	 *        3）ImportBeanDefinitionRegistrar:手动注册bean到容器中
	 *    4、使用spring提供的FactoryBean(工厂bean)
	 *       1）默认获取到的是工厂bean调用getObject创建的方法
	 *       2）要获取工厂bean本身，获取的时候要在id前面加&
	 */
	
	@Bean
	public ColorFactoryBean colorFactoryBean(){
		return new ColorFactoryBean();
	}

}
