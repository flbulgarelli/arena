package org.uqbar.arena.widgets;

import org.uqbar.lacar.ui.model.ControlBuilder;
import org.uqbar.lacar.ui.model.PanelBuilder;
import org.uqbar.lacar.ui.model.SkineableBuilder;

/**
 * Control que permite editar texto simple.
 * 
 * @author npasserini
 */
public class TextBox extends SkineableControl {
	
	public TextBox(Panel container) {
		super(container);
	}

	@Override
	protected ControlBuilder createBuilder(PanelBuilder container) {
		SkineableBuilder textBox = container.addTextBox();
		this.configureSkineableBuilder(textBox);
		return textBox;
	}

}
