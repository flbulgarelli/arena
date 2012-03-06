package org.uqbar.lacar.ui.impl.jface.bindings;

import java.beans.PropertyDescriptor;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.ValueDiff;
import org.eclipse.core.internal.databinding.beans.JavaBeanObservableValue;
import org.uqbar.arena.isolation.IsolationLevelEvents;

import com.uqbar.renascent.common.transaction.ObjectTransaction;
import com.uqbar.renascent.framework.aop.transaction.ObjectTransactionManager;

public class ArenaObservableValue extends JavaBeanObservableValue {

	private ObjectTransaction objectTransactionImpl;
	//TODO: Buscar otro lugar donde se pueda cambiar facilmente
	private IsolationLevelEvents isolationLevelEvents = IsolationLevelEvents.FIRE_OlNLY_IN_MY_TRANSACTION;

	public ArenaObservableValue(Realm realm, Object object,
			PropertyDescriptor descriptor) {
		super(realm, object, descriptor);
		this.objectTransactionImpl = ObjectTransactionManager.getTransaction();
	}
	
	@Override
	protected void fireValueChange(ValueDiff diff) {
		if(this.isolationLevelEvents.check(this.objectTransactionImpl)){
			super.fireValueChange(diff);
		}
	}

}
