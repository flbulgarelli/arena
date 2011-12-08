package com.uqbar.apo;

import org.junit.Before;
import org.junit.Test;

import com.uqbar.apo.util.ExampleObject;
import com.uqbar.apo.util.IExampleObject;
import com.uqbar.apo.util.TestRealm;
import com.uqbar.renascent.framework.aop.transaction.ObjectTransactionManager;
import com.uqbar.renascent.framework.aop.transaction.utils.BasicTaskOwner;

/**
 * @author nny
 * 
 * -Djava.system.class.loader=org.uqbar.arena.aop.ArenaClassLoader 
 */
public class TestObservable  extends AbstractTestObsevable{

	private static final String VALUE2 = "nombre2";
	private static final String VALUE3 = "nombre3";
	private static final String VALUE4 = "nombre4";
	
	
	@Before
	public void setUp() {
		TestRealm.install();
	}


	@Test
	public void testListenerWithoutTransaction(){
		final IExampleObject observableObject = createSourceObject();
		final IExampleObject observer = createObserverObject();

		
		bindProperty(observableObject, observer, ExampleObject.NAME);
		
		observableObject.setName(VALUE2);
		
		assertValues(observableObject, observer, VALUE2, VALUE2, VALUE2, VALUE2);
		
	}
	
	@Test
	public void testListenerWithSipleTransaction(){
		BasicTaskOwner owner = createOwnerTransaction();
		ObjectTransactionManager.begin(owner);

		final IExampleObject observableObject = createSourceObject();
		final IExampleObject observer = createObserverObject();

		assertValues(observableObject, observer, VALUE1, null, VALUE1, null);

		this.bindProperty(observableObject, observer, "name");

		
		//Apenas creo una transaccion verifico los valores
		assertValues(observableObject, observer, VALUE1, VALUE1, VALUE1, VALUE1);
		
		//primer cambio
		observableObject.setName(VALUE2);
		assertValues(observableObject, observer, VALUE2, VALUE2, VALUE1, VALUE2);
		
		//segundo cambio cambio
		observableObject.setName(VALUE3);
		assertValues(observableObject, observer, VALUE3, VALUE3, VALUE1, VALUE3);
		
		
		ObjectTransactionManager.commit(owner);
		//verifico despues del comit
		assertValues(observableObject, observer, VALUE3, VALUE3, VALUE3, VALUE3);
	}

	
	@Test
	public void testListenerWithNestedTransaction(){
		BasicTaskOwner owner = createOwnerTransaction();
		
		//Primer transaccion
		ObjectTransactionManager.begin(owner);

		final IExampleObject observableObject = createSourceObject();
		final IExampleObject observer = createObserverObject();

		assertValues(observableObject, observer, VALUE1, null, VALUE1, null);

		this.bindProperty(observableObject, observer, "name");
		
		//Apenas creo una transaccion verifico los valores
		assertValues(observableObject, observer, VALUE1, VALUE1, VALUE1, VALUE1);
		
		//primer cambio
		observableObject.setName(VALUE2);
		assertValues(observableObject, observer, VALUE2, VALUE2, VALUE1, VALUE2);

		//Segunda transaccion
		ObjectTransactionManager.begin(owner);
		
		final IExampleObject observer2 = new ExampleObject("observer2", null, null);
		
		this.bindProperty(observableObject, observer2, "name");
		
		//segundo cambio 
		observableObject.setName(VALUE3);
		assertValues(observableObject, observer, VALUE3, VALUE2, VALUE1, VALUE2);
		assertValues(observableObject, observer2, VALUE3, VALUE3, VALUE1, VALUE3);
		
		//Tercera transaccion
		ObjectTransactionManager.begin(owner);
		observableObject.setName(VALUE4);
		assertValues(observableObject, observer, VALUE4, VALUE2, VALUE1, VALUE2);
		assertValues(observableObject, observer2, VALUE4, VALUE3, VALUE1, VALUE3);
		
		//Primer Commit
		ObjectTransactionManager.commit(owner);
		assertValues(observableObject, observer, VALUE4, VALUE2, VALUE1, VALUE2);
		assertValues(observableObject, observer2, VALUE4, VALUE3, VALUE1, VALUE3);

		//Segundo Commit
		ObjectTransactionManager.commit(owner);
		assertValues(observableObject, observer, VALUE4, VALUE2, VALUE1, VALUE2);
		assertValues(observableObject, observer2, VALUE4, VALUE3, VALUE1, VALUE3);
		
		//Tercer Commit
		ObjectTransactionManager.commit(owner);
		//verifico despues del ultimo comit
		assertValues(observableObject, observer, VALUE4, VALUE4, VALUE4, VALUE4);
		assertValues(observableObject, observer2, VALUE4, VALUE4, VALUE4, VALUE4);
		
		
	}


	protected BasicTaskOwner createOwnerTransaction() {
		return new BasicTaskOwner("Test");
	}
}

