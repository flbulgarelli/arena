package com.uqbar.apo;

import org.uqbar.commons.utils.Transactional;

@Transactional
public class ExampleObservableObject implements IExampleObject{
	
	private String name;
	private Integer age;
	
	public ExampleObservableObject(String name, Integer age) {
		super();
		this.name = name;
		this.age = age;
	}
	
	
	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getAge() {
		return age;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}
	

}
