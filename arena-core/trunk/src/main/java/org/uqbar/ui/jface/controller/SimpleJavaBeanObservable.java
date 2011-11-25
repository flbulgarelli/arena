package org.uqbar.ui.jface.controller;

import java.beans.BeanInfo;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import org.eclipse.core.databinding.BindingException;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.internal.databinding.beans.JavaBeanObservableValue;

/**
 * 
 * @author npasserini
 */
public class SimpleJavaBeanObservable extends JavaBeanObservableValue {

	protected static PropertyDescriptor getPropertyDescriptor(Class<?> beanClass, String propertyName) {
		BeanInfo beanInfo;
		try {
			beanInfo = Introspector.getBeanInfo(beanClass);
		}
		catch (IntrospectionException e) {
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
		throw new BindingException("Could not find property with name " + propertyName + " in class " + beanClass);
	}

	public SimpleJavaBeanObservable(Realm realm, Object object, PropertyDescriptor descriptor) {
		super(realm, object, descriptor);
	}

	public SimpleJavaBeanObservable(Realm realm, Object object, PropertyDescriptor descriptor, boolean attachListeners) {
		super(realm, object, descriptor, attachListeners);
	}

}