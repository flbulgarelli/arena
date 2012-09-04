package org.uqbar.arena.tests.nestedCombos;

import org.uqbar.arena.actions.MessageSend;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.TextBox;
import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.WindowOwner;

public class EditProvinceDialog extends Dialog<Province> {

	public EditProvinceDialog(WindowOwner owner, Province model) {
		super(owner, model);
	}

	@Override
	protected void createFormPanel(Panel mainPanel) {
		new TextBox(mainPanel).bindValueToProperty("name");
	}

	@Override
	protected void addActions(Panel actionsPanel) {
		new Button(actionsPanel)
			.setCaption("Accept")
			.onClick(new MessageSend(this, ACCEPT))
			.setAsDefault()
			.disableOnError();

		new Button(actionsPanel).setCaption("Cancel").onClick(new MessageSend(this, CANCEL));
	}
}
