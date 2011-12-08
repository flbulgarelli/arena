package org.uqbar.lacar.ui.impl.jface.lists;

import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;
import org.uqbar.lacar.ui.impl.jface.JFaceContainer;
import org.uqbar.lacar.ui.impl.jface.JFaceControlBuilder;
import org.uqbar.lacar.ui.impl.jface.tree.ReflectionLabelProvider;
import org.uqbar.lacar.ui.model.Action;
import org.uqbar.lacar.ui.model.BindingBuilder;
import org.uqbar.lacar.ui.model.ListBuilder;

public class JFaceListBuilder<T> extends JFaceControlBuilder<List> implements ListBuilder<T>{
	
	private ListViewer listViewer;
	private Action onSelection;
	private final String propertyElement;

	public JFaceListBuilder(JFaceContainer container, Action onSelect, String propertyElement, int width, int heigth) {
		super(container);
		this.propertyElement = propertyElement;
		this.setWidth( width);
		this.setHeigth(heigth);
		onSelection = onSelect;
		
		this.listViewer = createListViewer(container.getJFaceComposite());
		this.initialize(this.listViewer.getList());
	}
	
	
	private ListViewer createListViewer(Composite jFaceComposite) {
		ListViewer viewer = new ListViewer(jFaceComposite, SWT.SINGLE | SWT.FULL_SELECTION);
		viewer.setLabelProvider(new ReflectionLabelProvider(propertyElement));
		viewer.addSelectionChangedListener(new SelectionChangeListener(getOnSelection()));
		RowData rowLayout = new RowData(getWidth(), getHeigth());
//
		viewer.getList().setLayoutData(rowLayout);
		
		return viewer;
	}
	
	@Override
	public BindingBuilder observeContents() {
		return new JFaceListContentsBindingBuilder(this);
	}

	@Override
	public BindingBuilder observeValue() {
		return null;
	}


	public Action getOnSelection() {
		return onSelection;
	}
	
	// ********************************************************
		// ** Internal accessors
		// ********************************************************

		public ListViewer getJFaceListViewer() {
			return this.listViewer;
		}


}
