package spring.annotation.bean;

import javax.annotation.*;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class Dog implements ApplicationContextAware{
	
	private ApplicationContext applicationContext;
	
	public Dog(){
		System.out.println("Dog constructor...");
	}
	
	@SuppressWarnings("restriction")
	@PostConstruct
	public void init(){
		System.out.println("Dog..@PostConstruct...");
	}
	
	@SuppressWarnings("restriction")
	@PreDestroy
	public void destory(){
		System.out.println("Dog..@PreDestroy...");
	}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		// TODO Auto-generated method stub
		this.applicationContext = applicationContext;
	}

}
