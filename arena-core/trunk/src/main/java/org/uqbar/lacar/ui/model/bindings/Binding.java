package org.uqbar.lacar.ui.model.bindings;

import org.uqbar.lacar.ui.model.Adapter;
import org.uqbar.lacar.ui.model.BindingBuilder;
import org.uqbar.lacar.ui.model.ControlBuilder;
import org.uqbar.lacar.ui.model.adapter.NotEmptyAdapter;
import org.uqbar.lacar.ui.model.adapter.NotNullAdapter;

/**
 * Abstracción que vincula dos propiedades observables: una del modelo y otra de la vista. El binding se
 * completa con un {@link Adapter} que permite ajustar las diferencias entre los valores manejados por modelo
 * y vista.
 * 
 * @author npasserini
 */
public class Binding<C extends ControlBuilder> {
	/**
	 * Referencia a una característica observable del modelo.
	 */
	private final Observable model;

	/**
	 * Referencia a una característica observable de la vista.
	 */
	private final ViewObservable<C> view;

	private Adapter<Object, Object> adapter;

	/**
	 * Constructor con un adapter por default.
	 */
	public Binding(Observable model, ViewObservable<C> view) {
		this.model = model;
		this.view = view;
	}

	/**
	 * Asigna la estrategia que determina la forma de transformar los valores provenientes del modelo al
	 * formato requerido por la vista y viceversa.
	 * 
	 * @param adapter Un {@link Adapter}
	 * @return Este mismo {@link BindingBuilder}, para encadenar mensajes.
	 */
	// El sistema de tipos de Java no tiene una forma de evitar esto.
	@SuppressWarnings("unchecked")
	public <M, V> Binding<C> setAdapter(Adapter<M, V> adapter) {
		this.adapter = (Adapter<Object, Object>) adapter;
		return this;
	}

	public void execute(C control) {
		BindingBuilder binder = this.view.createBinding(control);
		this.model.configure(binder);

		if (this.adapter != null) {
			binder.setAdapter(this.adapter);
		}

		binder.build();
	}
	
	/**
	 * Specifies that the output of this binding will be a boolean telling
	 * if the string input value is not null or empty. 
	 */
	public Binding<C> notEmpty() {
		return this.setAdapter(new NotEmptyAdapter());
	}

	public Binding<C> notNull() {
		return this.setAdapter(new NotNullAdapter());
	}
	
}
