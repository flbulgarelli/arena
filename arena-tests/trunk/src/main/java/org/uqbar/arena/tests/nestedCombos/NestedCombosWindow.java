package org.uqbar.arena.tests.nestedCombos;

import org.uqbar.arena.bindings.PropertyAdapter;
import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.List;
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

		Selector<Country> countries = new Selector<Country>(mainPanel);
		// countries.setContents(this.getModelObject().getPossibleCountries(), "name");

		countries.bindItemsToProperty("possibleCountries") //
			.setAdapter(new PropertyAdapter(Country.class, "name"));

		countries.bindValueToProperty("country");

		// Label country = new Label(mainPanel);
		// country.bindValueToProperty("country");

		List<Province> provincesList = new List<Province>(mainPanel);
		provincesList.bindItemsToProperty("possibleProvinces") //
			.setAdapter(new PropertyAdapter(Province.class, "name"));
		provincesList.setHeigth(100);
		provincesList.setWidth(100);

		Selector<Province> provinces = new Selector<Province>(mainPanel);
		provinces.bindItemsToProperty("possibleProvinces").setAdapter(new PropertyAdapter(Province.class, "name"));
		// provinces.bindItemsToProperty("country.provinces").setAdapter(new PropertyAdapter(Province.class,
		// "name"));
		provinces.bindValueToProperty("province");
	}
}
