package org.uqbar.arena.bindings;

import org.uqbar.lacar.ui.model.BindingBuilder;
import org.uqbar.lacar.ui.model.adapter.NotNullAdapter;

/**
 * 
 * 
 * @author jfernandes
 */
public class NotNullObservable extends ObservableProperty {

	public NotNullObservable(String propertyName) {
		super(propertyName);
	}

	@Override
	public void configure(BindingBuilder binder) {
		super.configure(binder);
		binder.setAdapter(new NotNullAdapter());
	}


}