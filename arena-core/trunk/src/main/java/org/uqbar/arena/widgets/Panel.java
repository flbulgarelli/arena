package org.uqbar.arena.widgets;

import java.util.ArrayList;
import java.util.List;

import org.uqbar.arena.layout.Layout;
import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.commons.model.IModel;
import org.uqbar.commons.model.Model;
import org.uqbar.lacar.ui.model.PanelBuilder;

import com.uqbar.commons.exceptions.ProgramException;
import com.uqbar.commons.loggeable.HierarchicalLogger;

public class Panel extends Widget implements Container {
	protected IModel<?> model;

	private int width = 250;

	/**
	 * Los componentes contenidos en este {@link Panel}
	 */
	private List<Widget> children = new ArrayList<Widget>();
	private Layout layout = new VerticalLayout();

	// ********************************************************
	// ** Panel creation
	// ********************************************************

	/**
	 * Creates a panel which by default inherits the model from its container.
	 * 
	 * @param container
	 */
	public Panel(Container container) {
		super(container);
	}

	public Panel(Container container, IModel<?> model) {
		this(container);
		this.model = model;
	}

	public Panel(Container container, Object model) {
		this(container, new Model<Object>(model));
	}

	// ********************************************************
	// ** Binding
	// ********************************************************

	/**
	 * @deprecated Use {@link #bindContentsToProperty(String)} instead
	 */
	@Deprecated
	public Panel bindContents(String propertyName) {
		return bindContentsToProperty(propertyName);
	}

	/**
	 * Vincula el contenido de este panel con una propiedad del panel padre.
	 * 
	 * TODO WARNING: Este no es exactamente un "binding", se asigna el modelo correspondiente pero no quedan
	 * "vinculados", en caso de modificaciones posteriores al modelo el panel no se entera.
	 */
	public Panel bindContentsToProperty(String propertyName) {
		Object propertyModel = this.getModel().getProperty(propertyName);
		if (propertyModel instanceof IModel) {
			this.model = (IModel<?>) propertyModel;
		}
		else {
			this.model = new Model<Object>(propertyModel);
		}
		return this;
	}

	// ********************************************************
	// ** Configuration
	// ********************************************************

	/**
	 * Creates the contents of the panel. Default behavior is to create no contents (i.e. an empty Panel).
	 * Subclasses can use this hook to configure specific contents.
	 */
	protected void createContents() {
	}

	public Panel setLayout(Layout layout) {
		this.layout = layout;
		return this;
	}

	// ********************************************************
	// ** Container interface
	// ********************************************************

	@Override
	public void addChild(Widget child) {
		this.children.add(child);
	}

	/**
	 * Returns the model associated to this Panel.
	 * 
	 * If no model has been associated explicitly to the panel, the container's model is used.
	 */
	@Override
	public IModel<?> getModel() {
		return this.model != null ? this.model : this.getContainer().getModel();
	}

	public Object getModelObject() {
		return this.getModel().getSource();
	}

	// ********************************************************
	// ** Compilation into the real stuff (technology dependent components)
	// ********************************************************

	@Override
	public void showOn(PanelBuilder container) {
		this.createContents();
		if (this.layout == null) {
			throw new ProgramException("No se especificó un layout para el Panel");
		}

		PanelBuilder me = container.addChildPanel();

		this.layout.configure(me);

		for (Widget child : this.children) {
			child.showOn(me);
		}
	}

	// ********************************************************
	// ** Internal communication between Arena components
	// ********************************************************

	public Panel setWidth(int panelWidth) {
		this.width = panelWidth;
		return this;
	}

	public int getWidth() {
		return this.width;
	}

	// ********************************************************
	// ** Utilities
	// ********************************************************

	@Override
	public void appendYourselfTo(HierarchicalLogger visitor) {
		super.appendYourselfTo(visitor);
		visitor.append("model", this.getModel());
		visitor.append("layout", this.layout);
		visitor.append("children", this.children);
	}

}