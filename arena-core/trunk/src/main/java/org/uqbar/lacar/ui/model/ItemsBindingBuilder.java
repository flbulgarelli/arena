package org.uqbar.lacar.ui.model;

public interface ItemsBindingBuilder extends BindingBuilder {

	/**
	 * Sets an adapter for the items of this binding, such as the elements of a {@link ListBuilder} or the
	 * contents of a {@link ColumnBuilder}.
	 * 
	 * @param modelType The class of the elements in the model.
	 * @param propertyName The name of the property to use to transform.
	 * @return this
	 */
	public <M, V> BindingBuilder adaptUsingProperty(Class<?> modelType, String propertyName);

}
