package org.uqbar.lacar.ui.impl.jface.tables;

import com.uqbar.commons.collections.Transformer;

public interface LabelProviderBuilder<R> {

	public abstract void addPropertyMappedColumn(String propertyName);

	public abstract void addCalculatedColumn(Transformer<R, ?> transformer);

}