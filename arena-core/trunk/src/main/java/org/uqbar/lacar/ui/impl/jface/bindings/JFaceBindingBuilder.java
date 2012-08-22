package org.uqbar.lacar.ui.impl.jface.bindings;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.uqbar.lacar.ui.impl.jface.JFaceWidgetBuilder;
import org.uqbar.ui.jface.base.BaseUpdateValueStrategy;

public class JFaceBindingBuilder extends AbstractJFaceBindingBuilder<IObservableValue, UpdateValueStrategy> {

	public JFaceBindingBuilder(DataBindingContext dbc) {
		super(dbc);
	}

	public JFaceBindingBuilder(JFaceWidgetBuilder<?> widget, IObservableValue view) {
		super(widget, view);
	}

	public JFaceBindingBuilder(JFaceWidgetBuilder<?> widget, IObservableValue view, IObservableValue model) {
		super(widget, view, model);
	}

	// ********************************************************
	// ** Configuration
	// ********************************************************

	protected UpdateValueStrategy createUpdateStrategy() {
		return new BaseUpdateValueStrategy();
	}

	// Necesito traits!!
	public IObservableValue observeValue(Object bean, String propertyName) {
		return JFaceBeansBeansObservables.observeProperty(bean, propertyName);
	}

	@Override
	protected void setConverter(UpdateValueStrategy viewToModel, IConverter converter) {
		viewToModel.setConverter(converter);
	}

	// ********************************************************
	// ** Building
	// ********************************************************

	@Override
	public void build() {
		this.getDataBindingContext().bindValue(this.getView(), this.getModel(), this.getViewToModel(),
			this.getModelToView());
	}
}
