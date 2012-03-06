package org.uqbar.lacar.ui.model;

import java.util.List;

import org.uqbar.arena.widgets.tree.Tree;


/**
 * 
 * 
 * @author npasserini
 */
public interface PanelBuilder {

	// ********************************************************
	// ** Components
	// ********************************************************

	public LabelBuilder addLabel();

	public SkineableBuilder addTextBox();
	
	public ControlBuilder addSpinner(Integer minValue, Integer maxValue);

	public ControlBuilder addCheckBox();

	public ButtonBuilder addButton(String caption, Action action);

	public SelectorBuilder addSelector(List options, String descriptionProperty, boolean nullValue, Action onSelection);

	public <R> TableBuilder<R> addTable(Class<R> itemType);

	public <T>ListBuilder<T> addList(Action onSelect, String propertyElement);
	
	public <T>ControlBuilder addTree(Tree<T> tree, String propertyNode);
	
	// ********************************************************
	// ** Panels
	// ********************************************************

	public PanelBuilder addChildPanel();

	public void addErrorPanel(String okMessage);

	// ********************************************************
	// ** Layout
	// ********************************************************

	public void setHorizontalLayout();

	public void setVerticalLayout();

	public void setLayoutInColumns(int columnCount);

	public void setPreferredWidth(int width);



}
