package org.uqbar.lacar.ui.impl.jface;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.databinding.BindingException;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.widgets.Combo;
import org.uqbar.commons.model.IModel;
import org.uqbar.commons.model.Model;
import org.uqbar.commons.model.ObservableObject;
import org.uqbar.lacar.ui.impl.jface.actions.JFaceActionAdapter;
import org.uqbar.lacar.ui.impl.jface.bindings.ArenaObservableValue;
import org.uqbar.lacar.ui.impl.jface.bindings.JFaceBindingBuilder;
import org.uqbar.lacar.ui.model.Action;
import org.uqbar.lacar.ui.model.Adapter;
import org.uqbar.lacar.ui.model.BindingBuilder;
import org.uqbar.lacar.ui.model.ButtonBuilder;
import org.uqbar.lacar.ui.model.SelectorBuilder;

import com.uqbar.commons.exceptions.ProgramException;

/**
 * @param <T> El tipo de objetos que contendrá el combo.
 */
public class JFaceSelectorBuilder extends JFaceControlBuilder<Combo> implements SelectorBuilder, Adapter<Object, String> {
	private Map<String, Object> map;
	private final String descriptionProperty;

	public JFaceSelectorBuilder(Combo jFaceWidget, JFaceContainer context, List options, String descriptionProperty, boolean nullValue) {
		super(context, jFaceWidget);
		this.descriptionProperty = descriptionProperty;

		this.map = new HashMap<String, Object>();
		String[] itemValues = new String[options.size() + (nullValue ? 1 : 0)];
		int i = 0;

		if (nullValue) {
			this.map.put("", null);
			itemValues[i++] = "";
		}

		for (Object option : options) {
			String optionValueString = this.asString(option);
			this.map.put(optionValueString, option);
			itemValues[i++] = optionValueString;
		}

		this.getWidget().setItems(itemValues);
	}

	@Override
	public BindingBuilder observeValue() {
		return new JFaceBindingBuilder(this, SWTObservables.observeSelection(this.getWidget())).setAdapter(this);
	}
	
	@Override
	public JFaceSelectorBuilder onSelection(Action action) {
		if(action != null){
			this.getWidget().addSelectionListener(new JFaceActionAdapter(this.getContainer(), action));
		}
		return this;
	}
	
	// ********************************************************
	// ** Adapter Interface
	// ********************************************************

	@Override
	public Object viewToModel(String valueFromView) {
		return this.map.get(valueFromView);
	}

	@Override
	public String modelToView(Object valueFromModel) {
		return this.asString(valueFromModel);
	}

	private String asString(Object valueFromModel) {
		if (valueFromModel == null) {
			return "";
		}
		return this.descriptionProperty == null ? "" : observeValue(valueFromModel, this.descriptionProperty).getValue()+"";
	}
	
	protected String getProperty(String propertyName, Object object) {
		Object value;
		if (object instanceof IModel) {
			value = ((IModel) object).getProperty(propertyName);
		}
		else {
			value = Model.getPropertyValue(object, propertyName);
		}
		return value == null ? "" : value.toString();
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

	

	@Override
	public Class<Object> getModelType() {
		return Object.class;
	}

	@Override
	public Class<String> getViewType() {
		return String.class;
	}
}
