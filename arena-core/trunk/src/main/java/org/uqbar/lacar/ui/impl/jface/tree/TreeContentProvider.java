package org.uqbar.lacar.ui.impl.jface.tree;

import java.util.Collection;

import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.IValueChangeListener;
import org.eclipse.core.databinding.observable.value.ValueChangeEvent;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

class TreeContentProvider  implements ITreeContentProvider {
	
	private IObservableValue observableParentValue;
	private IObservableValue observableChildrenValue;
	private String parentPropertyName;
	private String childrenPropertyName;
	

	public TreeContentProvider(String parentPropertyName, String childrenPropertyName) {
		this.parentPropertyName = parentPropertyName;
		this.childrenPropertyName = childrenPropertyName;
	}


	public Object[] getChildren(Object parentElement) {
		Collection<?> value = (Collection<?>) this.observableChildrenValue.getValue();
		return value != null ? value.toArray() : new Object[]{};
	}

	public Object getParent(Object element) {
		return observableParentValue.getValue();
	}

	public boolean hasChildren(Object element) {
		return true;
	}


	@Override
	public void dispose() {
		if (this.observableParentValue != null) {
			this.observableParentValue.dispose();
		}
		if (this.observableChildrenValue != null) {
			this.observableChildrenValue.dispose();
		}
	}
	@Override
	public void inputChanged(final Viewer viewer, Object oldInput, Object newInput) {
		// Dispose old value observer
				if (this.observableChildrenValue != null) {
					this.observableChildrenValue.dispose();
				}

				// Create new observer and listen to its changes.
				if (newInput != null) {
					this.observableChildrenValue = BeansObservables.observeValue(newInput, this.childrenPropertyName);
					this.observableChildrenValue.addValueChangeListener(new IValueChangeListener() {
						@Override
						public void handleValueChange(ValueChangeEvent event) {
							viewer.refresh();
						}
					});
					this.observableParentValue = BeansObservables.observeValue(newInput, this.parentPropertyName);
					this.observableParentValue.addValueChangeListener(new IValueChangeListener() {
						@Override
						public void handleValueChange(ValueChangeEvent event) {
							viewer.refresh();
						}
					});
				}
				else {
					this.observableChildrenValue = null;
				}
	}


	@Override
	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}
}