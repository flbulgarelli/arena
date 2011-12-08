package org.uqbar.arena.widgets;

import org.uqbar.arena.bindings.ObservableProperty;
import org.uqbar.arena.widgets.lists.ObservableListContents;
import org.uqbar.arena.widgets.tree.Tree;
import org.uqbar.lacar.ui.model.Action;
import org.uqbar.lacar.ui.model.ControlBuilder;
import org.uqbar.lacar.ui.model.PanelBuilder;
import org.uqbar.lacar.ui.model.bindings.Binding;
import org.uqbar.lacar.ui.model.bindings.Observable;


public class List<T> extends Control {
	private static final long serialVersionUID = 1L;
	
	private Action onSelect;

	private String propertyElement;

	public List(Container container) {
		super(container);
	}
	
	
	// ********************************************************
	// ** Configuration
	// ********************************************************

	public List<T> bindContentsToProperty(String propertyName) {
		return this.bindContents(new ObservableProperty(propertyName));
	}

	public List<T> bindContents(Observable model) {
		this.addBinding(model, new ObservableListContents());
		return this;
	}
	
	public List<T> bindElementToProperty(String propertyName){
		this.propertyElement = propertyName;
		return this;
	}
	
	public <C extends ControlBuilder> Binding<C> bindSelection(String selected) {
		return this.bindValue(new ObservableProperty(selected));
	}


	@Override
	protected ControlBuilder createBuilder(PanelBuilder container) {
		return container.addList(onSelect, propertyElement,  this.getWidth(), this.getHeigth());
	}



	public List<T> onSelect(Action onSelect) {
		this.onSelect = onSelect;
		return this;
	}

}
