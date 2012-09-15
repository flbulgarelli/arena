package org.uqbar.lacar.ui.model;

/**
 * @author npasserini
 */
public interface ControlBuilder extends WidgetBuilder {

	// ********************************************************
	// ** Binding methods
	// ********************************************************

	/**
	 * Starts the creation of a binding to the 'value' property of this Control with an observable property of the model
	 * 
	 * @return A {@link BindingBuilder} associated to this control, which allows to further configure the
	 *         binding and has the ultimate responsibility of creating it.
	 */
	public BindingBuilder observeValue();

	/**
	 * Starts the creation of a binding to the 'enabled' property of this Control with an observable property of the model
	 * 
	 * @return A {@link BindingBuilder} associated to this control, which allows to further configure the
	 *         binding and has the ultimate responsibility of creating it.
	 */
	public BindingBuilder observeEnabled();
	
	/**
	 * Used to create a binding associated to the "visible" property of this control.
	 * Usually you'll need this to show/hide a control based on some other condition (binding).
	 */
	public BindingBuilder observeVisible();

	public void setWidth(int preferredSize);

	public void setHeigth(int preferredSize);
	
}
