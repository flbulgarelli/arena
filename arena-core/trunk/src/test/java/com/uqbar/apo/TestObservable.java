package com.uqbar.apo;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import junit.framework.Assert;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.uqbar.commons.utils.ReflectionUtils;

import com.uqbar.renascent.framework.aop.transaction.ObjectTransactionManager;
import com.uqbar.renascent.framework.aop.transaction.utils.BasicTaskOwner;

/**
 * @author nny
 * 
 *  * -Djava.system.class.loader=org.unqbar.arena.aop.VideoclubClassLoader
 *
 */
public class TestObservable  {

	private static final int AGE = 123;
	private static final String VALUE1 = "nombre1";
	private static final String VALUE2 = "nombre2";
	private static final String VALUE3 = "nombre3";
	
	private Log logger = LogFactory.getLog(TestObservable.class);
	

	@Test
	public void testListenerWithoutTransaction(){
		final IExampleObject observableObject = new ExampleObservableObject(VALUE1, AGE);
		final IExampleObject observer = new ExampleObject();

		
		bindProperty(observableObject, observer, ExampleObject.NAME);
		
		observableObject.setName(VALUE2);
		
		assertValues(observableObject, observer, VALUE2, observableObject.getName(), VALUE2, VALUE2);
		
	}
	
	@Test
	public void testListenerWithSipleTransaction(){
		BasicTaskOwner owner = new BasicTaskOwner("Test");
		final IExampleObject observableObject = new ExampleObservableObject(VALUE1, AGE);
		final IExampleObject observer = new ExampleObject();
		
		this.bindProperty(observableObject, observer, "name");
		
		ObjectTransactionManager.begin(owner);
		//Apenas creo una transaccion verifico los valores
		assertValues(observableObject, observer, VALUE1, null, VALUE1, null);
		
		//primer cambio
		observableObject.setName(VALUE2);
		assertValues(observableObject, observer, VALUE2, observableObject.getName(), VALUE1, VALUE2);
		
		//segundo cambio cambio
		observableObject.setName(VALUE3);
		assertValues(observableObject, observer, VALUE3, observableObject.getName(), VALUE1, VALUE3);
		
		
		ObjectTransactionManager.commit(owner);
		//verifico despues del comit
		assertValues(observableObject, observer, VALUE3, observableObject.getName(), VALUE3, VALUE3);
	}

	protected void assertValues(IExampleObject observableObject, IExampleObject observer, Object observableGetValue,
			Object observerGetValue, Object observableFieldValue, Object observerFieldVale) {
		
		Assert.assertEquals(observableObject.getName(), observableGetValue);	
		Assert.assertEquals(observer.getName(), observerGetValue);
		
		//invoca por reflexcion para no pasar por el aspecto
		Assert.assertEquals(ReflectionUtils.readField(observableObject, ExampleObject.NAME), observableFieldValue);
		Assert.assertEquals(ReflectionUtils.readField(observer, ExampleObject.NAME), observerFieldVale);
	}

	private void bindProperty(Object observable, Object observer, String property) {
		ReflectionUtils.invokeMethod(observable, "addPropertyChangeListener", ExampleObject.NAME, createPropertyChangeListener(observer));
	}

	protected PropertyChangeListener createPropertyChangeListener(
			final Object observer) {
		return new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				StringBuffer message = new StringBuffer("firePropertyChange\n");
				message.append("\t source: " + evt.getSource() + "\n");
				message.append("\t property: " + evt.getPropertyName() + "\n");
				message.append("\t oldValue: " + evt.getOldValue() + "\n");
				message.append("\t newValue: " + evt.getNewValue() + "\n");
				
				logger.debug(message.toString());
				ReflectionUtils.invokeSetter(observer, evt.getPropertyName(), evt.getNewValue());
			}
		};
	}

}
