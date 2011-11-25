package org.uqbar.arena.widgets;

import java.util.List;

import org.uqbar.lacar.ui.model.Action;
import org.uqbar.lacar.ui.model.ControlBuilder;
import org.uqbar.lacar.ui.model.PanelBuilder;

import com.uqbar.commons.exceptions.ProgramException;

/**
 * 
 * 
 * @author npasserini
 */
public class Selector extends Control {
	private List options;
	private String descriptionProperty;
	private boolean nullValue;
	private Action onSelection;

	public Selector(Container container) {
		super(container);
	}

	// ********************************************************
	// ** Configuration
	// ********************************************************

	public Selector setContents(List options, String descriptionProperty) {
		this.options = options;
		this.descriptionProperty = descriptionProperty;
		return this;
	}

	public Selector allowNull(boolean nullValue) {
		this.nullValue = nullValue;
		return this;
	}
	
	public Selector onClick(Action onSelection) {
		this.onSelection = onSelection;
		return this;
	}

	// ********************************************************
	// ** Rendering
	// ********************************************************

	@Override
	protected ControlBuilder createBuilder(PanelBuilder container) {
		ProgramException.assertNotNull(this.options, "Tried to create a selector without setting the options!");
		return container.addSelector(this.options, this.descriptionProperty, this.nullValue, this.onSelection);
	}

}
