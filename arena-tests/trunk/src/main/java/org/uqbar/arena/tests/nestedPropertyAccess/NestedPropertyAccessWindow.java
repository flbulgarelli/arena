package org.uqbar.arena.tests.nestedPropertyAccess;

import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.tables.Column;
import org.uqbar.arena.widgets.tables.Table;
import org.uqbar.arena.windows.MainWindow;

public class NestedPropertyAccessWindow extends MainWindow<NestedPropertyAccessDomain> {

	// ****************************************************************
	// ** STATICS
	// ****************************************************************

	public static void main(String[] args) {
		new NestedPropertyAccessWindow(new NestedPropertyAccessDomain(new Country("Argentina"))).startApplication();
	}

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public NestedPropertyAccessWindow(NestedPropertyAccessDomain model) {
		super(model);
	}

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	@Override
	public void createContents(Panel mainPanel) {

		Table<Province> table = new Table<Province>(mainPanel, Province.class);
		table.bindContentsToProperty("country.provinces");

		Column<Province> nameColumn = new Column<Province>(table);
		nameColumn.setTitle("Location");
		nameColumn.bindContentsToProperty("name");
	}
}