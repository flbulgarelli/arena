package org.uqbar.ui.jface.builder;

import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.Widget;
import org.uqbar.lacar.ui.model.PanelBuilder;

public class ErrorsPanel extends Widget {
	private String okMessage;

	public ErrorsPanel(Panel container, String okMessage) {
		super(container);
		this.okMessage = okMessage;
	}

	@Override
	public void showOn(PanelBuilder container) {
		container.addErrorPanel(this.okMessage);
	}

}
