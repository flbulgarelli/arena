package com.uqbar.apo;

public class ExampleObject implements IExampleObject {

	public static final String NAME = "name";
	public static final String AGE = "age";

	
	private String name;
	private int age;

	public ExampleObject() {
		super();
	}
	
	public ExampleObject(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}


	public void setAge(int age) {
		this.age = age;
	}

	public int getAge() {
		return age;
	}

	/* (non-Javadoc)
	 * @see com.uqbar.objecttransactions.observable.IExmapleObject#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see com.uqbar.objecttransactions.observable.IExmapleObject#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

}