package org.uqbar.lacar.ui.impl.jface.lists;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.uqbar.lacar.ui.model.Action;

public class SelectionChangeListener implements ISelectionChangedListener {

	private Action onSelection;
	
	public SelectionChangeListener(Action onAction) {
		onSelection = onAction;
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		if (onSelection != null) {
			StructuredSelection selection = (StructuredSelection) event
					.getSelection();
			if (!selection.isEmpty()) {
				onSelection.execute(selection.getFirstElement());
			}
		}
	}
	
	public Action getOnSelection() {
		return onSelection;
	}
}
