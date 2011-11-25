package org.uqbar.lacar.ui.impl.jface;

import java.util.List;

import org.eclipse.core.databinding.AggregateValidationStatus;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.uqbar.lacar.ui.impl.jface.bindings.ObservableErrorPanelForegroundColor;
import org.uqbar.lacar.ui.impl.jface.bindings.ObservableStatusMessage;
import org.uqbar.lacar.ui.impl.jface.tables.JFaceTableBuilder;
import org.uqbar.lacar.ui.model.Action;
import org.uqbar.lacar.ui.model.ButtonBuilder;
import org.uqbar.lacar.ui.model.ControlBuilder;
import org.uqbar.lacar.ui.model.LabelBuilder;
import org.uqbar.lacar.ui.model.PanelBuilder;
import org.uqbar.lacar.ui.model.SelectorBuilder;
import org.uqbar.lacar.ui.model.SkineableBuilder;
import org.uqbar.lacar.ui.model.TableBuilder;
import org.uqbar.lacar.ui.model.WidgetBuilder;
import org.uqbar.ui.jface.view.ErrorViewer;

import com.uqbar.commons.collections.CollectionFactory;

public class JFacePanelBuilder extends JFaceWidgetBuilder<Composite> implements PanelBuilder, JFaceContainer {
	private List<WidgetBuilder> children = CollectionFactory.createList();

	public JFacePanelBuilder(JFaceContainer container) {
		super(container, new Composite(container.getJFaceComposite(), SWT.NONE));
	}

	// ********************************************************
	// ** Components
	// ********************************************************

	@Override
	public LabelBuilder addLabel() {
		return new JFaceLabelBuilder(this);
	}

	@Override
	public SkineableBuilder addTextBox() {
		return new JFaceTextBuilder(this);
	}
	
	@Override
	public ControlBuilder addSpinner(Integer minValue, Integer maxValue) {
		return new JFaceSpinnerBuilder(this, minValue, maxValue);
	}

	@Override
	public SelectorBuilder addSelector(List options, String descriptionProperty, boolean nullValue, Action onSelection) {
		Combo jfaceCombo = new Combo(this.getWidget(), SWT.LEFT | SWT.BORDER | SWT.SINGLE | SWT.READ_ONLY);
		return new JFaceSelectorBuilder(jfaceCombo, this, options, descriptionProperty,	nullValue).onSelection(onSelection);
	}

	@Override
	public ControlBuilder addCheckBox() {
		Button jfaceWidget = new Button(this.getWidget(), SWT.CHECK);

		// TODO Esto estaba hardcodeado y ahora lo saqué, ¿se puede volarlo del todo?
		// check.setLayoutData(new GridData(150, SWT.DEFAULT));

		JFaceCheckBoxBuilder widget = new JFaceCheckBoxBuilder(jfaceWidget, this);

		return widget;
	}

	@Override
	public ButtonBuilder addButton(String caption, Action action) {
		return new JFaceButtonBuilder(this).setCaption(caption).onClick(action);
	}

	@Override
	public <R> TableBuilder<R> addTable(Class<R> itemType) {
		return new JFaceTableBuilder<R>(this, itemType);
	}

	// ********************************************************
	// ** Panel
	// ********************************************************

	@Override
	public PanelBuilder addChildPanel() {
		return new JFacePanelBuilder(this);
	}

	@Override
	public void addErrorPanel(String okMessage) {
		// TODO Usar el framework para configurar el label en lugar de hacerlo manualmente.
		Label errorLabel = new Label(this.getWidget(), SWT.WRAP);

		errorLabel.setLayoutData(new RowData(250, 50));

		// fija el background del label. por default es blanco, al igual que el de eclipse
		errorLabel.setBackground(this.getWidget().getDisplay().getSystemColor(SWT.COLOR_WHITE));

		JFaceControlBuilder<Label> labelBuilder = new JFaceLabelBuilder(this, errorLabel);
		labelBuilder.bind(//
			new ObservableStatusMessage(this.getStatus(), okMessage), //
			SWTObservables.observeText(errorLabel));

		labelBuilder.bind(//
			SWTObservables.observeForeground(errorLabel),//
			new ObservableErrorPanelForegroundColor(this.getStatus()));

	}

	@Override
	public void setHorizontalLayout() {
		RowLayout layout = new RowLayout(SWT.HORIZONTAL);

		this.getWidget().setLayout(layout);
	}

	@Override
	public void setVerticalLayout() {
		RowLayout layout = new RowLayout(SWT.VERTICAL);
		layout.fill = true;

		this.getWidget().setLayout(layout);
	}

	@Override
	public void setLayoutInColumns(int columnCount) {
		this.getWidget().setLayout(new GridLayout(columnCount, false));
	}

	@Override
	public void setPreferredWidth(int width) {
		// TODO Hacer una abstracción más genérica que permita manejar distintos tipos de Layout Data.
		// this.getWidget().setLayoutData(new RowData(width , 500));
	}

	// ********************************************************
	// ** JFaceContainer
	// ********************************************************

	@Override
	public ErrorViewer getErrorViewer() {
		return this.getContainer().getErrorViewer();
	}

	@Override
	public AggregateValidationStatus getStatus() {
		return this.getContainer().getStatus();
	}

	@Override
	public Composite getJFaceComposite() {
		return this.getWidget();
	}

	@Override
	public JFaceContainer addChild(WidgetBuilder child) {
		this.children.add(child);
		return this;
	}
	

	@Override
	public void pack() {
		for (WidgetBuilder child : this.children) {
			child.pack();
		}
	}
}
