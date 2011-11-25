package org.uqbar.lacar.ui.model;

public interface ColumnBuilder<Row> extends WidgetBuilder {

	public void setTitle(String title);

	// ********************************************************
	// ** Column size specification
	// ********************************************************

	public void setWeight(int preferredSize);

	public void setFixedSize(int pixels);

}
