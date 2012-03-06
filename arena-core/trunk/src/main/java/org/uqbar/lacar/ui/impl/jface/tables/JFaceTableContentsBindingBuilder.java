package org.uqbar.lacar.ui.impl.jface.tables;

import org.eclipse.jface.viewers.TableViewer;
import org.uqbar.lacar.ui.model.Adapter;
import org.uqbar.lacar.ui.model.BindingBuilder;
import org.uqbar.ui.jface.controller.OneToManyContentProvider;

public class JFaceTableContentsBindingBuilder implements BindingBuilder {

	private TableViewer tableViewer;

	public JFaceTableContentsBindingBuilder(JFaceTableBuilder<?> table) {
		this.tableViewer = table.getJFaceTableViewer();
	}

	@Override
	public void observeProperty(Object model, String propertyName) {
		// ATENCION, el content provider DEBE ser asignado ANTES que el input.
		this.tableViewer.setContentProvider(new OneToManyContentProvider(propertyName));
		this.tableViewer.setInput(model);
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
