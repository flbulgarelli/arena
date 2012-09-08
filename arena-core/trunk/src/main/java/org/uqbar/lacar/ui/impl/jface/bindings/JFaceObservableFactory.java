package org.uqbar.lacar.ui.impl.jface.bindings;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.List;

import org.eclipse.core.databinding.BindingException;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.masterdetail.IObservableFactory;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.internal.databinding.beans.BeanObservableValueDecorator;

import scala.actors.threadpool.Arrays;

public class JFaceObservableFactory {

	public static IObservableValue observeProperty(Object bean, String propertyChain) {
		return observeProperty(bean, getChainParts(propertyChain));
	}

	private static IObservableValue observeProperty(Object bean, List<String> propertyChainParts) {
		String firstProperty = propertyChainParts.get(0);

		DetailTransacionalObservableValue observableValue;
		Class<?> propertyType;
		IObservableFactory valueFactory;

		final Realm realm = Realm.getDefault();
		IObservableValue detailObservable = new JavaBeanTransacionalObservableValue(realm, bean, getPropertyDescriptor(
			bean.getClass(), firstProperty));

		for (int i = 1; i < propertyChainParts.size(); i++) {
			valueFactory = BeansObservables.valueFactory(realm, propertyChainParts.get(i));
			propertyType = getPropertyDescriptor((Class<?>) detailObservable.getValueType(), propertyChainParts.get(i))
				.getPropertyType();
			observableValue = new DetailTransacionalObservableValue(detailObservable, valueFactory, propertyType);
			detailObservable = new BeanObservableValueDecorator(observableValue, detailObservable,
				getPropertyDescriptor((Class<?>) detailObservable.getValueType(), propertyChainParts.get(i)));
		}
		return detailObservable;
	}

	public static IObservableSet observeSet(Object bean, String propertyChain) {
		List<String> propertyChainParts = getChainParts(propertyChain);

		if (propertyChainParts.size() > 1) {
			IObservableValue master = observeProperty(bean,
				propertyChainParts.subList(0, propertyChainParts.size() - 1));

			return BeansObservables.observeDetailSet(Realm.getDefault(), master,
				propertyChainParts.get(propertyChainParts.size() - 1), null);
		}
		else {
			return BeansObservables.observeSet(Realm.getDefault(), bean, propertyChain, null);
		}
	}

	// public static IObservableSet observeSet(Object bean, String propertyName) {
	// String firstProperty = propertyName;
	// String[] parts = propertyName.split("\\.");
	// if (propertyName.contains(".")) {
	// firstProperty = parts[0];
	// }
	//
	// DetailObservableSet observableValue;
	// Class<?> propertyType;
	// IObservableFactory valueFactory;
	//
	// final Realm realm = Realm.getDefault();
	// IObservableSet detailObservable = BeansObservables.observeSet(realm, bean, firstProperty);
	//
	// for (int i = 1; i < parts.length; i++) {
	// valueFactory = BeansObservables.valueFactory(realm, parts[i]);
	// propertyType = getPropertyDescriptor((Class<?>) detailObservable.getElementType(), parts[i])
	// .getPropertyType();
	// observableValue = new DetailTransacionalObservableValue(detailObservable, valueFactory, propertyType);
	// detailObservable = new BeanObservableValueDecorator(observableValue, detailObservable,
	// getPropertyDescriptor((Class<?>) detailObservable.getValueType(), parts[i]));
	// }
	// return detailObservable;
	// }

	// public static IObservableMap[] observeMaps(IObservableSet domain, Class beanClass, String[]
	// propertyNames) {
	// IObservableMap[] result = new IObservableMap[propertyNames.length];
	// for (int i = 1; i < propertyNames.length; i++) {
	// final IObservableValue detail = observeProperty(beanClass, propertyNames[i]);
	// IObservableValue observeProperty = new DetailObservableValue(detail,
	// BeansObservables.mapPropertyFactory(Realm.getDefault(), propertyNames[i]), detail.getValueType());
	// result[i] = observeDetailMap(observeProperty, propertyNames[i]);
	// }
	// return result;
	// }

	public static IObservableList observeList(Object bean, String propertyName) {
		return BeansObservables.observeList(Realm.getDefault(), bean, propertyName);
	}

	public static IObservableMap observeDetailMap(IObservableValue master, String propertyName) {
		return BeansObservables.observeDetailMap(Realm.getDefault(), master, propertyName);
	}

	public static PropertyDescriptor getPropertyDescriptor(Class<?> beanClass, String propertyName) {
		BeanInfo beanInfo;
		try {
			beanInfo = Introspector.getBeanInfo(beanClass);
		}
		catch (IntrospectionException e) {
			return null;
		}
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			if (descriptor.getName().equals(propertyName)) {
				return descriptor;
			}
		}
		throw new BindingException("Could not find property with name " + propertyName + " in class " + beanClass); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@SuppressWarnings("unchecked")
	protected static List<String> getChainParts(String propertyChain) {
		return Arrays.asList(propertyChain.split("\\."));
	}

}
