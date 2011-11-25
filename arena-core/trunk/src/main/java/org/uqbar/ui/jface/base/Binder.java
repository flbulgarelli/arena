package org.uqbar.ui.jface.base;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;

public class Binder {
	private DataBindingContext dataBindingContext;
	
	public Binder(DataBindingContext context) {
		this.dataBindingContext = context;
	}
	
	public Binder() {
		this(new DataBindingContext());
	}
	
	public DataBindingContext getDataBindingContext() {
		return this.dataBindingContext;
	}
	
	// ********************************************************
	// ** Binding utilities
	// ********************************************************

	/**
	 * Vincula un control con una propiedad del modelo.
	 */
	public void bindText(Object model, String propertyName, Control control) {
		this.bind(SWTObservables.observeText(control, SWT.Modify), BeansObservables.observeValue(model, propertyName));
	}

	public void bind(Object model, UIProperty observableProperty, String propertyName, Control control) {
		this.bind(observableProperty.observeProperty(control), BeansObservables.observeValue(model, propertyName));
	}
	
	/**
	 * Vincula dos observables entre sí (uno visual de swt con uno del modelo).
	 */
	public void bind(ISWTObservableValue observableTarget, IObservableValue observableModel) {
		this.getDataBindingContext().bindValue(//
			observableTarget, //
			observableModel, //
			new BaseUpdateValueStrategy(), //
			new BaseUpdateValueStrategy());
	}
}
