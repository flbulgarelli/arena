package org.uqbar.lacar.ui.model;



public interface LabelBuilder extends SkineableBuilder{

	/**
	 * Se asigna un valor fijo sin binding.
	 * 
	 * TODO ¿Es necesario el {@link LabelBuilder#setText(String)}? ¿No quedaría mejor con un binding?
	 */
	public void setText(String text);


}
