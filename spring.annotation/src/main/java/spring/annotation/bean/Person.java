package spring.annotation.bean;

import org.springframework.beans.factory.annotation.Value;

public class Person {
	


	@Value("zhansan")
	private String name;
	@Value("#{20-2}")
	private Integer age;
	
	@Value("${person.nickname}")
	private String nickName;
	
	
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}

	
	public Person(String name, Integer age, String nickName) {
		super();
		this.name = name;
		this.age = age;
		this.nickName = nickName;
	}
	@Override
	public String toString() {
		return "Person [name=" + name + ", age=" + age + ", nickName="
				+ nickName + "]";
	}
	public Person() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
