package org.uqbar.lacar.ui.impl.jface.bindings;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.uqbar.lacar.ui.impl.jface.JFaceWidgetBuilder;
import org.uqbar.lacar.ui.model.Adapter;
import org.uqbar.lacar.ui.model.BindingBuilder;
import org.uqbar.ui.jface.base.BaseUpdateValueStrategy;

public class JFaceBindingBuilder implements BindingBuilder {

	private final DataBindingContext dbc;
	private IObservableValue model;
	private IObservableValue view;

	private UpdateValueStrategy viewToModel = new BaseUpdateValueStrategy();
	private UpdateValueStrategy modelToView = new BaseUpdateValueStrategy();

	public JFaceBindingBuilder(DataBindingContext dbc) {
		this.dbc = dbc;
	}

	public JFaceBindingBuilder(JFaceWidgetBuilder<?> widget, IObservableValue view) {
		this(widget.getDataBindingContext());
		this.view = view;
	}

	public JFaceBindingBuilder(JFaceWidgetBuilder<?> widget, IObservableValue view, IObservableValue model) {
		this(widget, view);
		this.model = model;
	}

	// ********************************************************
	// ** Configuration
	// ********************************************************

	@Override
	public void observeProperty(Object model, String propertyName) {
		this.model = this.observeValue(model, propertyName);
	}

	// Se pierden los tipos de los adapters, Java no tiene una forma de evitar
	// esto.
	@SuppressWarnings("unchecked")
	@Override
	public <M, V> BindingBuilder setAdapter(final Adapter<M, V> adapter) {
		this.viewToModel.setConverter(new IConverter() {
			@Override
			public Object getToType() {
				return adapter.getModelType();
			}

			@Override
			public Object getFromType() {
				return adapter.getViewType();
			}

			@Override
			public Object convert(Object value) {
				return adapter.viewToModel((V) value);
			}
		});

		this.modelToView.setConverter(new IConverter() {
			@Override
			public Object getToType() {
				return adapter.getViewType();
			}

			@Override
			public Object getFromType() {
				return adapter.getModelType();
			}

			@Override
			public Object convert(Object value) {
				return adapter.modelToView((M) value);
			}
		});

		return this;
	}

	public void setModel(IObservableValue model) {
		this.model = model;
	}

	public void setView(IObservableValue view) {
		this.view = view;
	}

	// ********************************************************
	// ** Building
	// ********************************************************

	@Override
	public void build() {
		this.dbc.bindValue(this.view, this.model, this.viewToModel, this.modelToView);
	}

	/**
	 * Necesito traits!!
	 */
	public IObservableValue observeValue(Object bean, String propertyName) {
		return JFaceBeansBeansObservables.observeProperty(bean, propertyName);
	}
}
