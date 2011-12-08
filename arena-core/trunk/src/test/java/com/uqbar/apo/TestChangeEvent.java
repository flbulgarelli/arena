package com.uqbar.apo;

import java.beans.PropertyChangeEvent;

import java.beans.PropertyChangeListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import static junit.framework.Assert.*;
import org.junit.Test;
import org.uqbar.commons.utils.ReflectionUtils;

import com.uqbar.apo.util.ExampleObject;
import com.uqbar.apo.util.ExampleObservableObject;
import com.uqbar.apo.util.IExampleObject;

/**
 * @author nny
 * 
 *  * -Djava.system.class.loader=org.unqbar.arena.aop.VideoclubClassLoader
 *
 */
public class TestChangeEvent extends AbstractTestObsevable{

	private static final String VALUE2 = "nombre2";
	private Log logger = LogFactory.getLog(TestObservable.class);
	private IExampleObject sourceObject;
	

	@Test
	public void testListenerWithoutTransaction(){
		sourceObject = createSourceObject();
		bindProperty(sourceObject, ExampleObject.NAME);
		
		sourceObject.setName(VALUE2);
	}
	
	protected void bindProperty(Object observable, String property) {
		ReflectionUtils.invokeMethod(observable, "addPropertyChangeListener", ExampleObject.NAME, createPropertyChangeListener());
	}

	protected PropertyChangeListener createPropertyChangeListener() {
		return new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				assertEquals(sourceObject, evt.getSource());
				assertEquals(ExampleObject.NAME, evt.getPropertyName());
				assertEquals(VALUE1, evt.getOldValue());
				assertEquals(VALUE2, evt.getNewValue());
				
				assertEquals(VALUE2, sourceObject.getName());
				assertEquals(VALUE2, getFieldValue(sourceObject));
				
				StringBuffer message = new StringBuffer("firePropertyChange\n");
				message.append("\t source: " + evt.getSource() + "\n");
				message.append("\t property: " + evt.getPropertyName() + "\n");
				message.append("\t oldValue: " + evt.getOldValue() + "\n");
				message.append("\t newValue: " + evt.getNewValue() + "\n");
				
				logger.debug(message.toString());
			}
		};
	}

}
