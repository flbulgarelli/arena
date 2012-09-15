package org.uqbar.arena.examples.controls.binding.enable;

import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.Selector;
import org.uqbar.arena.widgets.TextBox;
import org.uqbar.arena.windows.MainWindow;

/**
 * 
 * @author jfernandes
 */
public class BindEnabledExampleWindow extends MainWindow<Address> {

	public BindEnabledExampleWindow() {
		super(new Address());
	}

	@Override
	public void createContents(Panel mainPanel) {
		mainPanel.setLayout(new VerticalLayout());
		
		new Label(mainPanel).setText("Country:");
		new Selector<Country>(mainPanel)
			.setContents(Address.createCountries(), "name")
			.bindValueToProperty("country");
		
		new Label(mainPanel).setText("State:");
		TextBox state = new TextBox(mainPanel);
		state.bindValueToProperty("state");
		state.bindEnabledToProperty("country").notNull();
		
		new Label(mainPanel).setText("Street:");
		TextBox street = new TextBox(mainPanel);
		street.bindValueToProperty("street");
		street.bindEnabledToProperty("state").notEmpty();
	}
	
	public static void main(String[] args) {
		new BindEnabledExampleWindow().startApplication();
	}

}
