package org.uqbar.lacar.ui.impl.jface.tables;

import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;

import org.uqbar.lacar.ui.model.ColumnBuilder;
import org.uqbar.lacar.ui.model.LabelProvider;

public class JFaceColumnBuilder<Row> implements ColumnBuilder<Row> {
	/**
	 * El componente de JFace asociado.
	 */
	private final TableViewerColumn tableViewerColumn;

	/**
	 * Información de layout, que luego se traduce a un layout de JFace.
	 */
	private ColumnLayoutBuilder layoutBuilder = new DefaultColumnLayoutBuilder();

	/**
	 * Proveedor de los contenidos de esta columna, que luego se utiliza para construir el
	 * {@link org.eclipse.jface.viewers.LabelProvider} de JFace.
	 */
	private final LabelProvider<Row> labelProvider;

	// ********************************************************
	// ** Constructors
	// ********************************************************

	/**
	 * @param table La tabla a la que pertenece esta columna
	 * @param labelProvider La estrategia para obtener el contenido de la columna a partir del modelo (de la
	 *            fila).
	 */
	public JFaceColumnBuilder(JFaceTableBuilder<Row> table, LabelProvider<Row> labelProvider) {
		this.labelProvider = labelProvider;
		this.tableViewerColumn = new TableViewerColumn(table.getJFaceTableViewer(), SWT.NONE);
	}

	// ********************************************************
	// ** User configuration
	// ********************************************************

	@Override
	public void setTitle(String title) {
		this.tableViewerColumn.getColumn().setText(title);
	}

	@Override
	public void setWeight(int weight) {
		this.layoutBuilder = new WeightColumnLayoutBuilder(weight);
	}

	@Override
	public void setFixedSize(int pixels) {
		this.layoutBuilder = new FixedColumnLayoutBuilder(pixels);
	}

	// ********************************************************
	// ** Internal accessors
	// ********************************************************

	protected ColumnLayoutBuilder getLayoutBuilder() {
		return this.layoutBuilder;
	}

	protected LabelProvider<Row> getLabelProvider() {
		return this.labelProvider;
	}

	@Override
	public void pack() {
		// TODO No estoy seguro de que esto sea necesario.
		this.tableViewerColumn.getColumn().pack();
	}
}
