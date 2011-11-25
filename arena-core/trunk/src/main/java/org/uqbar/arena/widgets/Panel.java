package org.uqbar.arena.widgets;

import java.util.ArrayList;
import java.util.List;

import com.uqbar.commons.exceptions.ProgramException;
import com.uqbar.commons.loggeable.HierarchicalLogger;

import org.uqbar.arena.layout.ColumnLayout;
import org.uqbar.arena.layout.HorizontalLayout;
import org.uqbar.arena.layout.Layout;
import org.uqbar.commons.model.ObservableObject;
import org.uqbar.lacar.ui.model.PanelBuilder;

/**
 * Un Panel es básicamente un {@link Widget} que puede contener otros {@link Widget}.
 * 
 * @author npasserini
 */
public class Panel extends Widget implements Container {
	private ObservableObject model;
	private int width = 250;

	/**
	 * Los componentes contenidos en este {@link Panel}
	 */
	private List<Widget> children = new ArrayList<Widget>();
	private Layout layout;

	// ********************************************************
	// ** Panel creation
	// ********************************************************

	public Panel(Container container, ObservableObject model) {
		super(container);
		this.model = model;
	}

	public Panel(Container container) {
		this(container, container.getModel());
	}

	// ********************************************************
	// ** Binding
	// ********************************************************

	/**
	 * Vincula el contenido de este panel con una propiedad del panel padre.
	 * 
	 * TODO WARNING: Este no es exactamente un "binding", se asigna el modelo correspondiente pero no quedan
	 * "vinculados", en caso de modificaciones posteriores al modelo el panel no se entera.
	 */
	public Panel bindContents(String propertyName) {
		this.model = (ObservableObject) this.model.getProperty(propertyName);
		return this;
	}

	// ********************************************************
	// ** Layout (DEPRECATED)
	// ********************************************************

	public Panel setLayout(Layout layout) {
		this.layout = layout;
		return this;
	}

	public void setHorizontalLayout() {
		this.setLayout(new HorizontalLayout());
	}

	public void setLayoutInColumns(int columnCount) {
		this.setLayout(new ColumnLayout(columnCount));
	}

	// ********************************************************
	// ** Container interface
	// ********************************************************

	@Override
	public void addChild(Widget child) {
		this.children.add(child);
	}

	@Override
	public ObservableObject getModel() {
		return this.model;
	}

	// ********************************************************
	// ** Compilation into the real stuff (technology dependent components)
	// ********************************************************

	@Override
	public void showOn(PanelBuilder container) {
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
