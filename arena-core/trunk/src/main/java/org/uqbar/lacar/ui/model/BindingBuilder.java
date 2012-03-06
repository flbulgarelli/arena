package org.uqbar.lacar.ui.model;

import org.uqbar.commons.model.IModel;

/**
 * Colabora en la construcción de un binding.
 * 
 * @author npasserini
 */
public interface BindingBuilder {

	// ********************************************************
	// ** Configuracion
	// ********************************************************

	/**
	 * Observa una propiedad cualquiera de un objeto, por nombre.
	 * 
	 * @param model El objeto que posee la propiedad a observar.
	 * @param propertyName El nombre de la propiedad a observar.
	 */
	public void observeProperty(Object model, String propertyName);

	/**
	 * Agrega un adapter. Los adapters se ejecutan en el orden recibido cuando se transforma de vista a modelo
	 * y en el orden inverso para transformar de modelo a vista.
	 * 
	 * @param adapter Un nuevo adapter que se agrega al final de la lista.
	 * @return Este mismo {@link BindingBuilder}, para encadenar mensajes.
	 */
	public <M,V> BindingBuilder setAdapter(Adapter<M,V> adapter);

	// ********************************************************
	// ** Build
	// ********************************************************

	/**
	 * Indicates that configuration of this builder has ended and gives place to the work it needs to do to
	 * finalize de construction.
	 */
	public void build();

}
