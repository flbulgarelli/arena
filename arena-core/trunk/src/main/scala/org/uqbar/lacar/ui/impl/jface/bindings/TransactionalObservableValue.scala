package org.uqbar.lacar.ui.impl.jface.bindings

import org.eclipse.core.databinding.observable.masterdetail.IObservableFactory
import org.eclipse.core.databinding.observable.value.IObservableValue
import org.eclipse.core.databinding.observable.value.ValueDiff
import org.eclipse.core.internal.databinding.observable.masterdetail.DetailObservableValue
import org.uqbar.arena.isolation.IsolationLevelEvents
import com.uqbar.common.transaction.ObjectTransaction
import com.uqbar.aop.AopConfig
import com.uqbar.aop.transaction.ObjectTransactionManager
import org.eclipse.core.databinding.observable.value.AbstractObservableValue
import org.eclipse.core.internal.databinding.beans.JavaBeanObservableValue
import org.eclipse.core.databinding.observable.Realm
import java.beans.PropertyDescriptor
import org.eclipse.core.internal.databinding.beans.JavaBeanObservableSet
import org.eclipse.core.databinding.observable.set.AbstractObservableSet
import org.eclipse.core.databinding.observable.set.SetDiff
import org.eclipse.core.databinding.observable.AbstractObservable
import org.eclipse.core.databinding.observable.set.SetChangeEvent
import org.eclipse.core.databinding.observable.set.IObservableSet

trait ObservableEvents {
  val isolationKey = "framework.aop.opo.isolationLevel";
  var objectTransactionImpl = ObjectTransactionManager.getTransaction()
  val isolationLevelEvents = IsolationLevelEvents.valueOf(AopConfig.getProperty(isolationKey))

}

trait TransactionalObservableValue extends AbstractObservableValue with ObservableEvents {
  override def fireValueChange(diff: ValueDiff) {
    if (this.isolationLevelEvents.check(this.objectTransactionImpl)) {
      super.fireValueChange(diff);
    }
  }
}

class DetailTransacionalObservableValue(outerObservableValue: IObservableValue, factory: IObservableFactory, detailType: Any)
  extends DetailObservableValue(outerObservableValue, factory, detailType) with TransactionalObservableValue

class JavaBeanTransacionalObservableValue(realm: Realm, any: Any, descriptor: PropertyDescriptor)
  extends JavaBeanObservableValue(realm, any, descriptor) with TransactionalObservableValue


