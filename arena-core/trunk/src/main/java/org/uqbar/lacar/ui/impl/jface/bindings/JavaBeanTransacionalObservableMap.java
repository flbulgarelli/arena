package org.uqbar.lacar.ui.impl.jface.bindings;

import java.beans.PropertyDescriptor;

import org.eclipse.core.databinding.observable.map.MapDiff;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.core.internal.databinding.beans.JavaBeanObservableMap;
import org.uqbar.arena.isolation.IsolationLevelEvents;

import com.uqbar.aop.AopConfig;
import com.uqbar.aop.transaction.ObjectTransactionManager;
import com.uqbar.common.transaction.ObjectTransaction;

public class JavaBeanTransacionalObservableMap extends JavaBeanObservableMap{
	
	private final String isolationKey = "framework.aop.opo.isolationLevel";
	private ObjectTransaction objectTransactionImpl = ObjectTransactionManager.getTransaction();
	private IsolationLevelEvents isolationLevelEvents = IsolationLevelEvents.valueOf(AopConfig.getProperty(isolationKey));

	
	public JavaBeanTransacionalObservableMap(IObservableSet domain, PropertyDescriptor propertyDescriptor) {
		  super(domain, propertyDescriptor);
	}
	
	@Override
	protected void fireMapChange(MapDiff diff) {
		if (this.isolationLevelEvents.check(this.objectTransactionImpl)) {
			super.fireMapChange(diff);
		}
	}
	
}
