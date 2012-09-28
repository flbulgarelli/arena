package org.uqbar.lacar.ui.impl.jface.tables;

import java.awt.Color;

import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Control;
import org.uqbar.lacar.ui.model.AbstractWidgetBuilder;
import org.uqbar.lacar.ui.model.ColumnBuilder;
import org.uqbar.lacar.ui.model.LabelProvider;

public class JFaceColumnBuilder<Row> extends AbstractWidgetBuilder implements ColumnBuilder<Row> {
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

	@Override
	public void setForeground(Color color) {
		org.eclipse.swt.graphics.Color swtColor = getSWTColor(color);
		this.getControl().setForeground(swtColor);
	}

	@Override
	public void setBackground(Color color) {
		org.eclipse.swt.graphics.Color swtColor = getSWTColor(color);
		this.getControl().setBackground(swtColor);
	}

	@Override
	public void setFontSize(int size) {
		FontData[] fontData = this.getControl().getFont().getFontData();
		for (int i = 0; i < fontData.length; ++i)
			fontData[i].setHeight(size);

		final Font newFont = new Font(this.getControl().getDisplay(), fontData);
		this.getControl().setFont(newFont);

		this.getControl().getFont();
	}

	protected org.eclipse.swt.graphics.Color getSWTColor(Color color) {
		int blue = color.getBlue();
		int green = color.getGreen();
		int red = color.getRed();
		org.eclipse.swt.graphics.Color swtColor = new org.eclipse.swt.graphics.Color(getControl().getDisplay(), red,
			green, blue);
		return swtColor;
	}

	// ********************************************************
	// ** Pack
	// ********************************************************

	@Override
	public void pack() {
		super.pack();
		this.tableViewerColumn.getColumn().pack();
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

	protected Control getControl() {
		return this.tableViewerColumn.getViewer().getControl();
	}
}
