package spring.annotation.tx;

import java.beans.PropertyVetoException;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.mchange.v2.c3p0.ComboPooledDataSource;
/**
 * 
 * 声明式事务
 * 环境搭建：
 *   1.导入相关依赖：
 *      数据源，数据库驱动，spring-jdbc
 *   2.配置数据源JdbcTempalte操作数据库
 *   3.方法上加上@Transactional
 *   4.@EnableTransactionManagement开启基于注解的事物管理功能
 *
 */
@EnableTransactionManagement
@ComponentScan("spring.annotation.tx")
@Configuration
public class TxConfig {
	
	@Bean
	public DataSource datasource() throws PropertyVetoException{
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		dataSource.setUser("root");
		dataSource.setPassword("sa");
		dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/user");
		dataSource.setDriverClass("com.mysql.jdbc.Driver");
		return dataSource;
	}
	
	//
	@Bean
	public JdbcTemplate jdbcTemplate() throws PropertyVetoException{
		//spring对Configuration类会特殊处理，给容器中加组件的方法，多次调用都只是从容器中找组件
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource());
		return jdbcTemplate;
	}
	
	//注册事务管理器在容器中
	@Bean
	public PlatformTransactionManager transactionManager() throws PropertyVetoException{
		return new DataSourceTransactionManager(datasource());
	}
	

}
