package org.uqbar.lacar.ui.impl.jface.tables;

public class WeightColumnLayoutBuilder implements ColumnLayoutBuilder {

	private final int weight;

	public WeightColumnLayoutBuilder(int weight) {
		this.weight = weight;
	}

	@Override
	public void configure(JFaceTableLayoutBuilder tableLayoutBuilder) {
		tableLayoutBuilder.addColumnWithWieght(this.weight);
	}

}
