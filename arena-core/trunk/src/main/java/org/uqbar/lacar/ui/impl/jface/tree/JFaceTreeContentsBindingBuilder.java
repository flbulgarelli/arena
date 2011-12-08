package org.uqbar.lacar.ui.impl.jface.tree;

import org.eclipse.jface.viewers.TreeViewer;
import org.uqbar.commons.model.ObservableObject;
import org.uqbar.lacar.ui.model.Adapter;
import org.uqbar.lacar.ui.model.BindingBuilder;
import org.uqbar.ui.jface.controller.OneToManyContentProvider;


public class JFaceTreeContentsBindingBuilder implements BindingBuilder {

	private TreeViewer treeViewer;

	public JFaceTreeContentsBindingBuilder(JFaceTreeBuilder<?> list) {
		this.treeViewer = list.getJFaceTreeViewer();
	}

	public void observeProperty(ObservableObject model, String parentPropertyName, String childPropertyName) {
		// ATENCION, el content provider DEBE ser asignado ANTES que el input.
		this.treeViewer.setContentProvider(new TreeContentProvider(parentPropertyName, childPropertyName));
		this.treeViewer.setInput(model);
	}

	@Override
	public <M, V> BindingBuilder setAdapter(Adapter<M, V> adapter) {
		throw new UnsupportedOperationException("No está preparado para tener adapters.");
	}

	@Override
	public void observeProperty(ObservableObject model, String propertyName) {
		throw new UnsupportedOperationException("No se puede usar este binding, utilize " +
				" observeProperty(ObservableObject model, String parentPropertyName, String childPropertyName)");
	}
	
	@Override
	public void build() {
	}

}
