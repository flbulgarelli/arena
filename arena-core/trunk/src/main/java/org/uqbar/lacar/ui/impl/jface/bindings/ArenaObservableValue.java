package org.uqbar.lacar.ui.impl.jface.bindings;

import java.beans.PropertyDescriptor;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.ValueDiff;
import org.eclipse.core.internal.databinding.beans.JavaBeanObservableValue;

public class ArenaObservableValue extends JavaBeanObservableValue {

	public ArenaObservableValue(Realm realm, Object object,
			PropertyDescriptor descriptor) {
		super(realm, object, descriptor);
	}
	
	@Override
	protected void fireValueChange(ValueDiff diff) {
		super.fireValueChange(diff);
	}

}
