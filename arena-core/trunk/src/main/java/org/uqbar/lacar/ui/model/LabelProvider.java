package org.uqbar.lacar.ui.model;

import org.uqbar.lacar.ui.impl.jface.tables.LabelProviderBuilder;

public interface LabelProvider<T> {
	public void configure(LabelProviderBuilder<T> configurator);
}
