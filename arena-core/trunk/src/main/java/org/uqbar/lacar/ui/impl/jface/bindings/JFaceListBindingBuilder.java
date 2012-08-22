package org.uqbar.lacar.ui.impl.jface.bindings;

import org.eclipse.core.databinding.UpdateListStrategy;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.uqbar.lacar.ui.impl.jface.JFaceWidgetBuilder;

public class JFaceListBindingBuilder extends AbstractJFaceBindingBuilder<IObservableList, UpdateListStrategy> {

	public JFaceListBindingBuilder(JFaceWidgetBuilder<?> widget, IObservableList view) {
		super(widget, view);
	}

	// ********************************************************
	// ** Configuration
	// ********************************************************

	protected UpdateListStrategy createUpdateStrategy() {
		return new UpdateListStrategy();
	}

	// Necesito traits!!
	public IObservableList observeValue(Object bean, String propertyName) {
		return JFaceBeansBeansObservables.observeList(bean, propertyName);
	}

	@Override
	protected void setConverter(UpdateListStrategy viewToModel, IConverter converter) {
		viewToModel.setConverter(converter);
	}
	
	
	// ********************************************************
	// ** Building
	// ********************************************************
	
	@Override
	public void build() {
		this.getDataBindingContext().bindList(this.getView(), this.getModel(), this.getViewToModel(), this.getModelToView());
	}

}
