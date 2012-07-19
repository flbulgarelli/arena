package org.uqbar.lacar.ui.impl.jface.tables;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.uqbar.lacar.ui.impl.jface.bindings.JFaceBeansBeansObservables;

import com.uqbar.commons.collections.Transformer;

/**
 * @param <R> El tipo de las columnas de la tabla a configurar.
 * 
 * @author npasserini
 */
public class JFaceLabelProviderBuilder<R> implements LabelProviderBuilder<R> {
	private List<String> columnPropertyNames = new ArrayList<String>();
	private int columnIndex = 0;
	private int delegatedColumnIndex = 0;
	private Map<Integer, Transformer<R, ?>> calculatedColumns = new HashMap<Integer, Transformer<R, ?>>();
	private final JFaceTableBuilder<R> table;
	private ColumnsLabelProvider<R> columnsLabelProvider;

	public JFaceLabelProviderBuilder(JFaceTableBuilder<R> table) {
		this.table = table;
		this.columnsLabelProvider = new ColumnsLabelProvider<R>();
	}

	public IBaseLabelProvider createLabelProvider() {
		for (JFaceColumnBuilder<R> column : this.table.getColumns()) {
			column.getLabelProvider().configure(this);
		}

		IObservableMap[] attributeMaps = BeansObservables.observeMaps(
			new ObservableListContentProvider().getKnownElements(), this.table.getItemType(),
			this.columnPropertyNames.toArray(new String[this.columnPropertyNames.size()]));

		this.columnsLabelProvider.initialize(new ObservableMapLabelProvider(attributeMaps), this.calculatedColumns);
		return this.columnsLabelProvider;
	}

	// ********************************************************
	// ** Configuration
	// ********************************************************

	@Override
	public void addPropertyMappedColumn(String propertyName) {
		this.columnPropertyNames.add(propertyName);

		final int index = this.delegatedColumnIndex++;

		this.addCalculatedColumn(this.columnsLabelProvider.getDelegatingTransformer(index));
	}

	@Override
	public void addCalculatedColumn(Transformer<R, ?> transformer) {
		this.calculatedColumns.put(this.columnIndex++, transformer);
	}

}