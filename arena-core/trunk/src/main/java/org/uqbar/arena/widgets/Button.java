package org.uqbar.arena.widgets;

import com.uqbar.commons.collections.Closure;

import org.uqbar.lacar.ui.model.Action;
import org.uqbar.lacar.ui.model.ButtonBuilder;
import org.uqbar.lacar.ui.model.ControlBuilder;
import org.uqbar.lacar.ui.model.PanelBuilder;

/**
 * 
 * @author npasserini
 */
public class Button extends Control {
	private String caption = this.nextCaption();
	private Action onClick;

	public Button(Container container) {
		super(container);
	}

	// ********************************************************
	// ** Configurations
	// ********************************************************

	public Button setCaption(String caption) {
		this.caption = caption;
		return this;
	}

	public Button onClick(Action onClick) {
		this.onClick = onClick;
		return this;
	}

	public Button setAsDefault() {
		this.addConfiguration(new Closure<ButtonBuilder>() {
			@Override
			public void execute(ButtonBuilder builder) {
				builder.setAsDefault();
			}
		});
		return this;
	}

	public Button disableOnError() {
		this.addConfiguration(new Closure<ButtonBuilder>() {
			@Override
			public void execute(ButtonBuilder builder) {
				builder.disableOnError();
			}
		});
		return this;
	}

	// ********************************************************
	// ** Rendering
	// ********************************************************

	@Override
	protected ControlBuilder createBuilder(PanelBuilder container) {
		return container.addButton(this.caption, this.onClick);
	}
}
