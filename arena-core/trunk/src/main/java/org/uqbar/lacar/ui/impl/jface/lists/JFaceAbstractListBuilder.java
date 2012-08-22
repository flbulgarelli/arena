package org.uqbar.lacar.ui.impl.jface.lists;

import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.viewers.AbstractListViewer;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.uqbar.lacar.ui.impl.jface.JFaceContainer;
import org.uqbar.lacar.ui.impl.jface.JFaceControlBuilder;
import org.uqbar.lacar.ui.impl.jface.bindings.JFaceBindingBuilder;
import org.uqbar.lacar.ui.model.Action;
import org.uqbar.lacar.ui.model.BindingBuilder;
import org.uqbar.lacar.ui.model.ListBuilder;

public abstract class JFaceAbstractListBuilder<T, Viewer extends AbstractListViewer, C extends Control> //
		extends JFaceControlBuilder<C> //
		implements ListBuilder<T> {

	protected String itemsPropertyName;
	protected Viewer viewer;

	public JFaceAbstractListBuilder(JFaceContainer container) {
		super(container);
		this.viewer = this.createViewer(container.getJFaceComposite());
		this.viewer.setContentProvider(new ArrayContentProvider());
	}

	/**
	 * It is mandatory to call {@link #initialize(org.eclipse.swt.widgets.Widget)} when creating the viewer.
	 */
	protected abstract Viewer createViewer(Composite jFaceComposite);

	@Override
	public BindingBuilder observeValue() {
		return new JFaceBindingBuilder(this, ViewersObservables.observeSingleSelection(this.getViewer()));
	}

	@Override
	public BindingBuilder observeItems() {
		return new JFaceListItemsBindingBuilder(this);
	}

	@Override
	public ListBuilder<T> onSelection(Action action) {
		this.viewer.addSelectionChangedListener(new SelectionChangeListener(action));
		return this;
	}
	
	// ********************************************************
	// ** Internal accessors
	// ********************************************************

	protected Viewer getViewer() {
		return this.viewer;
	}

	@Override
	protected Control getControlLayout() {
		return this.viewer.getControl();
	}

	public Viewer getJFaceListViewer() {
		return this.viewer;
	}

}