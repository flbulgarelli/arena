package org.uqbar.arena.widgets;

import org.uqbar.lacar.ui.model.ControlBuilder;
import org.uqbar.lacar.ui.model.PanelBuilder;
import org.uqbar.lacar.ui.model.SkineableBuilder;
import org.uqbar.lacar.ui.model.TextControlBuilder;

import com.uqbar.commons.collections.Closure;

/**
 * Control que permite editar texto simple.
 * 
 * @author npasserini
 */
public class TextBox extends SkineableControl {
	private static final long serialVersionUID = 1L;

	public TextBox(Panel container) {
		super(container);
	}

	@Override
	protected ControlBuilder createBuilder(PanelBuilder container) {
		SkineableBuilder textBox = container.addTextBox();
		this.configureSkineableBuilder(textBox);
		return textBox;
	}
	
	public void withFilter(final TextFilter filter) {
		this.addConfiguration(new Closure<TextControlBuilder>() {
			@Override
			public void execute(TextControlBuilder builder) {
				builder.addTextFilter(filter);
			}
		});
	}

}
