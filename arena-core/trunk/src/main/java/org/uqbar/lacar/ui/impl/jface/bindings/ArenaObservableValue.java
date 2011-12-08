package org.uqbar.lacar.ui.impl.jface.bindings;

import java.beans.PropertyDescriptor;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.ValueDiff;
import org.eclipse.core.internal.databinding.beans.JavaBeanObservableValue;

import com.uqbar.renascent.common.transaction.ObjectTransaction;
import com.uqbar.renascent.framework.aop.transaction.ObjectTransactionImpl;
import com.uqbar.renascent.framework.aop.transaction.ObjectTransactionManager;

public class ArenaObservableValue extends JavaBeanObservableValue {

	private ObjectTransaction objectTransactionImpl;

	public ArenaObservableValue(Realm realm, Object object,
			PropertyDescriptor descriptor) {
		super(realm, object, descriptor);
		this.objectTransactionImpl = ObjectTransactionManager.getTransaction();
	}
	
	@Override
	protected void fireValueChange(ValueDiff diff) {
		if(this.objectTransactionImpl.getId() >= ObjectTransactionManager.getTransaction().getId()){
			super.fireValueChange(diff);
		}
	}

}
