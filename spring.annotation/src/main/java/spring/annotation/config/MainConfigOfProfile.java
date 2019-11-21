package spring.annotation.config;

import java.beans.PropertyVetoException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.StringValueResolver;

import spring.annotation.bean.Yellow;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * profile:
 *       spring为我们提供的可以根据当前环境，动态的激活和切换一系列组件的功能
 *       
 *       加了环境标识的bean，只有被环境被激活的时候才会调用，默认是default环境
 *       
 *       写在类上，只有是在指定环境的时候，整个配置类里面的所有配置才会生效
 *       没有标注环境标识的bean，任何环境下都会加载
 * @author fangshuyun
 *
 */
//@Profile("test")
@PropertySource("classpath:/db.properties")
@Configuration
public class MainConfigOfProfile implements EmbeddedValueResolverAware{
	
	private StringValueResolver resolver;
	
	@Value("${db.user}")
	private String user;
	
	@Value("${db.password}")
	private String password;
	
	@Value("${db.driver}")
	private String driver;
	
	/**
	 * 1.启动的时候使用命令行启动：-Dspring.profiles.active=test
	 * 2、利用代码激活
	 * @return
	 */
	@Bean
	public Yellow yellow(){
		return new Yellow();
	}
	
	@Profile("test")
	@Bean("testDataSource")
	public DataSource datasourceTest() throws PropertyVetoException{
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		dataSource.setUser(user);
		dataSource.setPassword(password);
		dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/shop");
		dataSource.setDriverClass(driver);
		return dataSource;
	}
	
	@Profile("dev")
	@Bean("devDataSource")
	public DataSource datasourceDev() throws PropertyVetoException{
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		dataSource.setUser(user);
		dataSource.setPassword(password);
		dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/user");
		dataSource.setDriverClass(driver);
		return dataSource;
	}
	
	@Profile("prod")
	@Bean("prodDataSource")
	public DataSource datasourceProd() throws PropertyVetoException{
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		dataSource.setUser(user);
		dataSource.setPassword(password);
		dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/message");
		String diverString = resolver.resolveStringValue("${db.driver}");
		dataSource.setDriverClass(diverString);
		return dataSource;
	}

	public void setEmbeddedValueResolver(StringValueResolver resolver) {
		// TODO Auto-generated method stub
		this.resolver = resolver;
	}

}
