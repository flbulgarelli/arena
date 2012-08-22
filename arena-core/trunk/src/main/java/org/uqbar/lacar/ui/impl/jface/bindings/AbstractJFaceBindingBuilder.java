package org.uqbar.lacar.ui.impl.jface.bindings;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.conversion.IConverter;
import org.uqbar.arena.bindings.Transformer;
import org.uqbar.lacar.ui.impl.jface.JFaceWidgetBuilder;
import org.uqbar.lacar.ui.model.BindingBuilder;

public abstract class AbstractJFaceBindingBuilder<Observable, Strategy> implements BindingBuilder {
	private final DataBindingContext dbc;
	private Observable model;
	private Observable view;
	private Strategy viewToModel;
	private Strategy modelToView;

	public AbstractJFaceBindingBuilder(DataBindingContext dbc) {
		this.dbc = dbc;
		this.viewToModel = this.createUpdateStrategy();
		this.modelToView = this.createUpdateStrategy();
	}

	public AbstractJFaceBindingBuilder(JFaceWidgetBuilder<?> widget, Observable view) {
		this(widget.getDataBindingContext());
		this.view = view;
	}

	public AbstractJFaceBindingBuilder(JFaceWidgetBuilder<?> widget, Observable view, Observable model) {
		this(widget, view);
		this.model = model;
	}

	protected abstract Strategy createUpdateStrategy();

	// ********************************************************
	// ** Configuration
	// ********************************************************

	@Override
	public void observeProperty(Object model, String propertyName) {
		this.model = this.observeValue(model, propertyName);
	}

	public abstract Observable observeValue(Object bean, String propertyName);

	protected abstract void setConverter(Strategy viewToModel, IConverter iConverter);

	@SuppressWarnings("unchecked")
	@Override
	public <M, V> BindingBuilder adaptWith(final Transformer<M, V> transformer) {
		this.setConverter(this.viewToModel, new IConverter() {
			@Override
			public Object getToType() {
				return transformer.getModelType();
			}

			@Override
			public Object getFromType() {
				return transformer.getViewType();
			}

			@Override
			public Object convert(Object value) {
				return transformer.viewToModel((V) value);
			}
		});

		this.setConverter(this.modelToView, new IConverter() {
			@Override
			public Object getToType() {
				return transformer.getViewType();
			}

			@Override
			public Object getFromType() {
				return transformer.getModelType();
			}

			@Override
			public Object convert(Object value) {
				return transformer.modelToView((M) value);
			}
		});

		return this;
	}

	// ********************************************************
	// ** Accessors
	// ********************************************************

	protected DataBindingContext getDataBindingContext() {
		return dbc;
	}

	public Observable getModel() {
		return model;
	}

	public void setModel(Observable model) {
		this.model = model;
	}

	public Observable getView() {
		return view;
	}

	public void setView(Observable view) {
		this.view = view;
	}

	public Strategy getViewToModel() {
		return viewToModel;
	}

	public void setViewToModel(Strategy viewToModel) {
		this.viewToModel = viewToModel;
	}

	public Strategy getModelToView() {
		return modelToView;
	}

	public void setModelToView(Strategy modelToView) {
		this.modelToView = modelToView;
	}
}