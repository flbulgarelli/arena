package org.uqbar.lacar.ui.impl.jface.lists;

import org.eclipse.jface.viewers.ListViewer;
import org.uqbar.lacar.ui.model.Adapter;
import org.uqbar.lacar.ui.model.BindingBuilder;
import org.uqbar.ui.jface.controller.OneToManyContentProvider;


public class JFaceListContentsBindingBuilder implements BindingBuilder {

	private ListViewer listViewer;

	public JFaceListContentsBindingBuilder(JFaceListBuilder<?> list) {
		this.listViewer = list.getJFaceListViewer();
	}

	@Override
	public void observeProperty(Object model, String propertyName) {
		// ATENCION, el content provider DEBE ser asignado ANTES que el input.
		this.listViewer.setContentProvider(new OneToManyContentProvider(propertyName));
		this.listViewer.setInput(model);
	}

	@Override
	public <M, V> BindingBuilder setAdapter(Adapter<M, V> adapter) {
		throw new UnsupportedOperationException("No está preparado para tener adapters.");
	}

	@Override
	public void build() {
		// El trabajo ya fue hecho en #observeProperty, no queda nada por hacer.
	}
}
