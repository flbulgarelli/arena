package com.uqbar.apo.util;

import org.uqbar.commons.utils.ReflectionUtils;
import org.uqbar.commons.utils.Transactional;
import org.uqbar.commons.utils.TransactionalAndObservable;


@TransactionalAndObservable
class ExampleObservableSObject(var testRole:String, var name:String, var age:Integer ) extends IExampleObject {


	def setAge(age:Integer) {
		this.age = age;
	}

	def getAge() = age;

	def setName(name:String) {
		this.name = name;
	}

	override def getName() =  name;

	/**
	 * Shows the values in the current transaction, reflection should be used if
	 * field values are needed.
	 */
	override def toString():String ={
		var fieldValue = ReflectionUtils.readField(this, ExampleObject.NAME)
		return this.getTestRole() + " (transaction = " + this.name + ", field = " + fieldValue + ")";
	}

	override def getTestRole() = testRole

}
