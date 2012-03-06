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
		
		assertGetterValue(observableObject, VALUE2);
		assertGetterValue(observer, VALUE2);
		
		assertFieldValue(observableObject, VALUE2);
		assertFieldValue(observer, VALUE2);	
		
	}
	
	@Test
	public void testListenerWithSipleTransaction(){
		BasicTaskOwner owner = createOwnerTransaction();
		ObjectTransactionManager.begin(owner);

		final IExampleObject observableObject = createSourceObject();
		final IExampleObject observer = createObserverObject();

		assertGetterValue(observableObject, VALUE1);
		assertGetterValue(observer, null);
		
		assertFieldValue(observableObject, VALUE1);
		assertFieldValue(observer, null);	

		this.bindProperty(observableObject, observer, "name");

		
		//Apenas creo una transaccion verifico los valores
		assertGetterValue(observableObject, VALUE1);
		assertGetterValue(observer, VALUE1);
		
		assertFieldValue(observableObject, VALUE1);
		assertFieldValue(observer, VALUE1);	
		
		//primer cambio
		observableObject.setName(VALUE2);
		
		assertGetterValue(observableObject, VALUE2);
		assertGetterValue(observer, VALUE2);
		
		assertFieldValue(observableObject, VALUE1);
		assertFieldValue(observer, VALUE2);	
		
		//segundo cambio cambio
		observableObject.setName(VALUE3);
		
		assertGetterValue(observableObject, VALUE3);
		assertGetterValue(observer, VALUE3);
		
		assertFieldValue(observableObject, VALUE1);
		assertFieldValue(observer, VALUE3);	
		
		
		ObjectTransactionManager.commit(owner);
		
		//verifico despues del comit
		assertGetterValue(observableObject, VALUE3);
		assertGetterValue(observer, VALUE3);
		
		assertFieldValue(observableObject, VALUE3);
		assertFieldValue(observer, VALUE3);	
	}

	
	@Test
	public void testListenerWithNestedTransaction(){
		BasicTaskOwner owner = createOwnerTransaction();
		
		//Primer transaccion
		ObjectTransactionManager.begin(owner);

		final IExampleObject observableObject = createSourceObject();
		final IExampleObject observer = createObserverObject();

		assertGetterValue(observableObject, VALUE1);
		assertGetterValue(observer, null);
		
		assertFieldValue(observableObject, VALUE1);
		assertFieldValue(observer, null);	

		this.bindProperty(observableObject, observer, "name");
		
		//Apenas creo una transaccion verifico los valores
		assertGetterValue(observableObject, VALUE1);
		assertGetterValue(observer, VALUE1);
		
		assertFieldValue(observableObject, VALUE1);
		assertFieldValue(observer, VALUE1);	
		
		//primer cambio
		observableObject.setName(VALUE2);
		
		assertGetterValue(observableObject, VALUE2);
		assertGetterValue(observer, VALUE2);
		
		assertFieldValue(observableObject, VALUE1);
		assertFieldValue(observer, VALUE2);	

		//Segunda transaccion
		ObjectTransactionManager.begin(owner);
		
		final IExampleObject observer2 = new ExampleObject("observer2", null, null);
		
		this.bindProperty(observableObject, observer2, "name");
		
		//segundo cambio 
		observableObject.setName(VALUE3);
		
		assertGetterValue(observableObject, VALUE3);
		assertGetterValue(observer, VALUE2);
		assertGetterValue(observer2, VALUE3);
		
		assertFieldValue(observableObject, VALUE1);
		assertFieldValue(observer, VALUE2);
		assertFieldValue(observer2, VALUE3);	
		
		
		//Tercera transaccion
		ObjectTransactionManager.begin(owner);
		observableObject.setName(VALUE4);
		
		assertGetterValue(observableObject, VALUE4);
		assertGetterValue(observer, VALUE2);
		assertGetterValue(observer2, VALUE3);
		
		assertFieldValue(observableObject, VALUE1);
		assertFieldValue(observer, VALUE2);
		assertFieldValue(observer2, VALUE3);
		
		//Primer Commit
		ObjectTransactionManager.commit(owner);
		
		assertGetterValue(observableObject, VALUE4);
		assertGetterValue(observer, VALUE2);
		assertGetterValue(observer2, VALUE4);
		
		assertFieldValue(observableObject, VALUE1);
		assertFieldValue(observer, VALUE2);
		assertFieldValue(observer2, VALUE4);

		//Segundo Commit
		ObjectTransactionManager.commit(owner);
		
		assertGetterValue(observableObject, VALUE4);
		assertGetterValue(observer, VALUE4);
		assertGetterValue(observer2, VALUE4);
		
		assertFieldValue(observableObject, VALUE1);
		assertFieldValue(observer, VALUE4);
		assertFieldValue(observer2, VALUE4);
		
		//Tercer Commit
		ObjectTransactionManager.commit(owner);
		
		//verifico despues del ultimo comit
		assertGetterValue(observableObject, VALUE4);
		assertGetterValue(observer, VALUE4);
		assertGetterValue(observer2, VALUE4);
		
		assertFieldValue(observableObject, VALUE4);
		assertFieldValue(observer, VALUE4);
		assertFieldValue(observer2, VALUE4);
		
		
	}


	protected BasicTaskOwner createOwnerTransaction() {
		return new BasicTaskOwner("Test");
	}
}

