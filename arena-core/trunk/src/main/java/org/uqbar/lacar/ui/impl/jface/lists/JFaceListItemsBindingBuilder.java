package org.uqbar.lacar.ui.impl.jface.lists;

import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.viewers.AbstractListViewer;
import org.uqbar.lacar.ui.impl.jface.bindings.JFaceBindingBuilder;
import org.uqbar.lacar.ui.model.BindingBuilder;
import org.uqbar.lacar.ui.model.ItemsBindingBuilder;

public class JFaceListItemsBindingBuilder extends JFaceBindingBuilder implements ItemsBindingBuilder {
	private AbstractListViewer listViewer;
	private IObservableSet itemsObservableSet;

	public JFaceListItemsBindingBuilder(JFaceAbstractListBuilder<?, ?, ?> list) {
		super(list, ViewersObservables.observeInput(list.getJFaceListViewer()));
		this.listViewer = list.getJFaceListViewer();
	}

	@Override
	public void observeProperty(Object modelObject, String propertyName) {
		super.observeProperty(modelObject, propertyName);
		this.itemsObservableSet = BeansObservables.observeSet(Realm.getDefault(), modelObject, propertyName);
	}

	@Override
	public <M, V> BindingBuilder adaptUsingProperty(Class<?> modelType, String propertyName) {
		IObservableMap labelProviderMap = BeansObservables.observeMap(itemsObservableSet, modelType, propertyName);
		this.listViewer.setLabelProvider(new ObservableMapLabelProvider(labelProviderMap));
		return this;
	}
}
