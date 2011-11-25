package org.uqbar.arena.bindings;

import org.uqbar.arena.widgets.Container;
import org.uqbar.commons.model.ObservableObject;
import org.uqbar.lacar.ui.model.BindingBuilder;
import org.uqbar.lacar.ui.model.bindings.Observable;

/**
 * Observa una propiedad de un modelo.
 * 
 * @author npasserini
 */
public class ObservableProperty implements Observable {
	private final String propertyName;
	private ObservableObject model;

	public ObservableProperty(String propertyName) {
		this.propertyName = propertyName;
	}

	public ObservableProperty(ObservableObject model, String propertyName) {
		this.propertyName = propertyName;
	}

	// ********************************************************
	// ** Configuration
	// ********************************************************

	/**
	 * Si a este {@link ObservableProperty} no se le ha asignado un modelo, tomará como base el modelo del
	 * contenedor.
	 */
	@Override
	public void setContainer(Container container) {
		if (this.model == null) {
			this.setModel(container.getModel());
		}
	}

	/**
	 * Antes de registrar el modelo se lo valida, en caso de fallar la validación el modelo no se asigna.
	 * Widget
	 * @param model El modelo contra el que bindear
	 * @return Este mismo {@link ObservableProperty}, comodidad para enviar mensajes anidados.
	 */
	public ObservableProperty setModel(ObservableObject model) {
		// Validar que el contenedor tiene la propiedad que nos interesa.
		model.getGetter(this.propertyName);

		// Una vez validado se guarda el modelo recibido, es el modelo contra el que vamos a bindear.
		this.model = model;
		return this;
	}

	// ********************************************************
	// ** Building
	// ********************************************************

	@Override
	public void configure(BindingBuilder binder) {
		binder.observeProperty(this.model, this.propertyName);
	}
	
}
