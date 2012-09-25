package com.uqbar.apo;

import static junit.framework.Assert.assertEquals;

import org.eclipse.core.databinding.DataBindingContext;
import org.uqbar.commons.utils.ReflectionUtils;
import org.uqbar.lacar.ui.impl.jface.bindings.JFaceObservableFactory;
import org.uqbar.lacar.ui.impl.jface.bindings.JFaceBindingBuilder;

import com.uqbar.apo.util.ExampleObject;
import com.uqbar.apo.util.ExampleObservableObject;
import com.uqbar.apo.util.IExampleObject;

public class AbstractTestObsevable {

	protected static final int AGE = 123;
	protected static final String VALUE1 = "VALUE1";

	public AbstractTestObsevable() {
		super();
	}

	protected Object getFieldValue(IExampleObject observer) {
		return ReflectionUtils.readField(observer, ExampleObject.NAME);
	}

	protected void bindProperty(Object observable, Object observer, String propertyName) {
		JFaceBindingBuilder builder = new JFaceBindingBuilder(new DataBindingContext(), //
			JFaceObservableFactory.observeProperty(observable, propertyName),
			JFaceObservableFactory.observeProperty(observer, propertyName));

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

	protected void assertGetterValue(IExampleObject object, Object value) {
		assertEquals("La getter del " + object.getTestRole() + " no tiene el valor esperado", value, object.getName());
	}

	protected void assertFieldValue(IExampleObject object, Object value) {
		assertEquals("El field del " + object.getTestRole() + " no tiene el valor esperado", value,
			getFieldValue(object));
	}

}