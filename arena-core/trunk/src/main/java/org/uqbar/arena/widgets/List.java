package org.uqbar.arena.widgets;

import org.uqbar.lacar.ui.model.ControlBuilder;
import org.uqbar.lacar.ui.model.PanelBuilder;


public class List<T> extends Selector<T> {
	
	public List(Container container) {
		super(container);
	}
	
	// ********************************************************
	// ** Rendering
	// ********************************************************

	protected ControlBuilder createBuilder(PanelBuilder container) {
		return container.addList().onSelection(this.onSelection);
	}

}
