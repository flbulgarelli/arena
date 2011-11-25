package org.uqbar.lacar.ui.impl.jface;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.swt.widgets.Widget;

import org.uqbar.lacar.ui.model.WidgetBuilder;

/**
 * Clase base para todos los objetos que saben construir {@link Widget} de JFace
 * 
 * @param <T> El tipo de Widget que construye este builder.
 * @author npasserini
 */
public abstract class JFaceWidgetBuilder<T extends Widget> implements WidgetBuilder {
	private T widget;
	private JFaceContainer container;

	/**
	 * En caso de utilizar este constructor, el widget debe ser asignado posteriormente.
	 * @param container Otro widget en el que este está embebido.
	 */
	public JFaceWidgetBuilder(JFaceContainer container) {
		this.container = container;
		this.container.addChild(this);
	}

	public JFaceWidgetBuilder(JFaceContainer container, T jFaceWidget) {
		this(container);
		this.initialize(jFaceWidget);
	}

	/**
	 * Es obligatorio que las subclases llamen siempre a este método en caso de postergar la construcción del
	 * widget.
	 * 
	 * @param jFaceWidget
	 */
	protected void initialize(T jFaceWidget) {
		this.widget = jFaceWidget;
	}

	// ********************************************************
	// ** Accessors
	// ********************************************************

	public T getWidget() {
		return this.widget;
	}

	public JFaceContainer getContainer() {
		return this.container;
	}

	public DataBindingContext getDataBindingContext() {
		return this.getContainer().getDataBindingContext();
	}

	protected String getDescription() {
		return this.getClass().getSimpleName();
	}

	@Override
	public void pack() {
	}
}
