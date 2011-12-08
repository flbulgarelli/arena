package com.uqbar.apo;

import static junit.framework.Assert.assertEquals;

import org.eclipse.core.databinding.DataBindingContext;
import org.uqbar.commons.utils.ReflectionUtils;
import org.uqbar.lacar.ui.impl.jface.bindings.JFaceBindingBuilder;

import com.uqbar.apo.util.ExampleObject;
import com.uqbar.apo.util.ExampleObservableObject;
import com.uqbar.apo.util.IExampleObject;

public class AbstractTestObsevable {

	protected static final int AGE = 123;
	protected static final String VALUE1 = "nombre1";

	public AbstractTestObsevable() {
		super();
	}

	protected Object getFieldValue(IExampleObject observer) {
		return ReflectionUtils.readField(observer, ExampleObject.NAME);
	}

	protected void bindProperty(Object observable, Object observer,
			String property) {
		JFaceBindingBuilder builder = new JFaceBindingBuilder(
				new DataBindingContext());
		builder.setModel(builder.observeValue(observable, property));
		builder.setView(builder.observeValue(observer, property));
		builder.build();
	}

	protected IExampleObject createObserverObject() {
		return new ExampleObject("observer", null, null);
	}

	protected IExampleObject createSourceObject() {
		return new ExampleObservableObject("source", VALUE1, AGE);
	}

	// **************************************************************
	// ** Auxiliar methods
	// **************************************************************

	protected void assertValues(IExampleObject observableObject,
			IExampleObject observer, Object observableGetValue,
			Object observerGetValue, Object observableFieldValue,
			Object observerFieldVale) {

		assertEquals("La getter del observalbe no tiene el valor esperado",
				observableGetValue, observableObject.getName());
		assertEquals("El getter del observer no tiene el valor esperado",
				observerGetValue, observer.getName());

		// invoca por reflection para no pasar por el aspecto
		assertEquals("El field del observable no tiene el valor esperado",
				observableFieldValue, getFieldValue(observableObject));
		assertEquals("El field del observer no tiene el valor esperado",
				observerFieldVale, getFieldValue(observer));
	}

}