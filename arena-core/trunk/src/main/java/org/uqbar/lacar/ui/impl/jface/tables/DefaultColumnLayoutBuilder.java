package org.uqbar.lacar.ui.impl.jface.tables;

public class DefaultColumnLayoutBuilder implements ColumnLayoutBuilder {

	@Override
	public void configure(JFaceTableLayoutBuilder tableLayoutBuilder) {
		tableLayoutBuilder.addColumnWithDefaultWeight();
	}

}
