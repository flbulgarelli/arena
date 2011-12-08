package com.uqbar.apo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;

import com.uqbar.apo.util.ExampleObject;
import com.uqbar.apo.util.IExampleObject;
import com.uqbar.apo.util.TestRealm;

/**
 * @author nny
 * 
 * -Djava.system.class.loader=org.unqbar.arena.aop.ArenaClassLoader
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
		assertValues(observableObject, observer, VALUE1, null, VALUE1, null);
		
		bindProperty(observableObject, observer, ExampleObject.NAME);
		
		//Se syncroniza
		assertValues(observableObject, observer, VALUE1, VALUE1, VALUE1, VALUE1);
		
		observableObject.setName(VALUE2);

		//se actualiza
		assertValues(observableObject, observer, VALUE2, VALUE2, VALUE2, VALUE2);
	}

}
