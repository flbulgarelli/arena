package org.uqbar.arena.tests.nestedCombos;

import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.Selector;
import org.uqbar.arena.windows.MainWindow;

public class NestedCombosWindow extends MainWindow<NestedCombosDomain> {
	public static void main(String[] args) {
		new NestedCombosWindow().startApplication();
	}

	public NestedCombosWindow() {
		super(new NestedCombosDomain());
	}

	@Override
	public void createContents(Panel mainPanel) {
		mainPanel.setLayout(new VerticalLayout());

		new Selector(mainPanel)// Contries
			.setContents(this.getModelObject().getPossibleCountries(), "name")
			.bindValueToProperty("country");

		new Selector(mainPanel) // Provinces
			.setContents(this.getModelObject().getPossibleProvinces(), "name")
			.bindValueToProperty("province");

	}
}
