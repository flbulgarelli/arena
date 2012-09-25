package com.uqbar.apo;

import org.junit.Before;
import org.junit.Test;

import com.uqbar.apo.util.ExampleObject;
import com.uqbar.apo.util.IExampleObject;
import com.uqbar.apo.util.TestRealm;

/**
 * @author nny
 * 
 * -Djava.system.class.loader=org.uqbar.arena.aop.ArenaClassLoader
 * 
 */
public class TestArenaObservableValue extends AbstractTestObsevable {

	private static final String VALUE2 = "nombre2";

	@Before
	public void setUp() {
		TestRealm.install();
	}

	@Test
	public void testListenerWithoutTransaction() {
		final IExampleObject observableObject = createSourceObject();
		final IExampleObject observer = createObserverObject();

		//Los valores sin cambios
		assertGetterValue(observableObject, VALUE1);
		assertGetterValue(observer, null);
		assertFieldValue(observableObject, VALUE1);
		assertFieldValue(observer, null);		
		
		
		bindProperty(observer, observableObject, ExampleObject.NAME);
		
		//Se syncroniza
		assertGetterValue(observableObject, VALUE1);
		assertGetterValue(observer, VALUE1);
		assertFieldValue(observableObject, VALUE1);
		assertFieldValue(observer, VALUE1);
		
		observableObject.setName(VALUE2);

		//se actualiza
		assertGetterValue(observableObject, VALUE2);
		assertGetterValue(observer, VALUE2);
		assertFieldValue(observableObject, VALUE2);
		assertFieldValue(observer, VALUE2);
	}

}
