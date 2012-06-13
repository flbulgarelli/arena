package org.uqbar.arena.aop.poo;

import org.uqbar.commons.utils.TransactionalAndObservable;

@TransactionalAndObservable
public class ExampleObservableObject {
	
	private String name;
	private Integer id;
	
	public ExampleObservableObject(String name, Integer id) {
		super();
		this.name = name;
		this.id = id;
	}
	
	
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return "Nombre: " + name + " Id:" + id;
	}
	

}
