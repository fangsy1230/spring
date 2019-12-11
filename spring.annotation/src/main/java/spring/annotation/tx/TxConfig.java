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
 *   5.配置事务管理器来控制事务
 *   
 *   原理：
 *   1）@EnableTransactionManagement
 *    利用TransactionManagementConfigurationSelector给容器中导入组件
 *       AutoProxyRegistrar
 *       ProxyTransactionManagementConfiguration
 *  AutoProxyRegistrar
 *     给容器中注册InfrastructureAdvisorAutoProxyCreator组件：
 *       利用后置处理器机制在对象创建以后，包装对象，返回一个代理对象（增强器），代理对象执行方法利用拦截器链进行调用
 *     
 *  ProxyTransactionManagementConfiguration
 *     1、给容器中注册事务增强器
 *        1）事务增强器要用事务注解的信息，AnnotationTransactionAttributeSource解析事务注解
 *        2）事务拦截器 TransactionInterceptor，保存了事务属性信息，事务管理器（是一个方法拦截器）
 *           在目标方法执行的时候：
 *             执行拦截器链
 *             事务拦截器：
 *               1）现获取事务相关属性
 *               2）再获取PlatformTransactionManager，如果事先没有添加指定任何transacationManager
 *                  最终会从容器中按类型获取一个PlatformTransactionManager
 *               3）执行目标方法
 *                  如果异常，获取到事务管理器，利用事务管理器回滚
 *                  如果正常，利用事务管理器提交事务
 *               
 *       
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
