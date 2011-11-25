package org.uqbar.lacar.ui.impl.jface.tables;

public class FixedColumnLayoutBuilder implements ColumnLayoutBuilder {

	private final int pixels;

	public FixedColumnLayoutBuilder(int pixels) {
		this.pixels = pixels;
	}

	@Override
	public void configure(JFaceTableLayoutBuilder tableLayoutBuilder) {
		tableLayoutBuilder.addFixedColumn(this.pixels);
	}

}
