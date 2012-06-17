package org.uqbar.lacar.ui.impl.jface.bindings;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import org.eclipse.core.databinding.BindingException;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.uqbar.commons.model.IModel;
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

	public IObservableValue observeValue(Object bean, String propertyName) {
		String firstProperty = propertyName;
		String[] parts = propertyName.split("\\."); 
		if(propertyName.contains(".")){
			firstProperty = parts[0];
		}
		
		IObservableValue detailObservable = BeansObservables.observeValue(bean, firstProperty);
		for (int i = 1; i < parts.length; i++) {
			detailObservable = BeansObservables.observeDetailValue(Realm.getDefault(), detailObservable, parts[i] , getPropertyDescriptor(detailObservable.getValue().getClass(), parts[i]).getPropertyType());
		}
		
		return detailObservable; 
	}

	public IObservableValue observeValue(Realm realm, Object bean, String propertyName) {
		PropertyDescriptor descriptor = getPropertyDescriptor(bean.getClass(), propertyName);
		return new ArenaObservableValue(realm, bean, descriptor);
	}

	public PropertyDescriptor getPropertyDescriptor(Class<?> beanClass, String propertyName) {
		BeanInfo beanInfo;
		try {
			beanInfo = Introspector.getBeanInfo(beanClass);
		} catch (IntrospectionException e) {
			// cannot introspect, give up
			return null;
		}
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			if (descriptor.getName().equals(propertyName)) {
				return descriptor;
			}
		}
		throw new BindingException(
				"Could not find property with name " + propertyName + " in class " + beanClass); //$NON-NLS-1$ //$NON-NLS-2$
	}
}
