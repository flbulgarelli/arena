package org.uqbar.arena.widgets.lists;

import org.uqbar.lacar.ui.model.BindingBuilder;
import org.uqbar.lacar.ui.model.ListBuilder;
import org.uqbar.lacar.ui.model.bindings.ViewObservable;

public class ObservableListContents implements ViewObservable<ListBuilder<?>> {

	@Override
	public BindingBuilder createBinding(ListBuilder<?> list) {
		return list.observeContents();
	}

}
