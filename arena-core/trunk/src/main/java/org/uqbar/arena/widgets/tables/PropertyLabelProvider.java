package org.uqbar.arena.widgets.tables;

import org.uqbar.lacar.ui.impl.jface.tables.LabelProviderBuilder;
import org.uqbar.lacar.ui.model.LabelProvider;

public class PropertyLabelProvider<T> implements LabelProvider<T> {
	private final String propertyName;

	public PropertyLabelProvider(String propertyName) {
		this.propertyName = propertyName;
	}

	@Override
	public void configure(LabelProviderBuilder<T> configurator) {
		configurator.addPropertyMappedColumn(this.propertyName);
	}

}
