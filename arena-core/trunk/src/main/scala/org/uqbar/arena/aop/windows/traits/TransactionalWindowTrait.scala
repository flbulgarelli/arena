package org.uqbar.arena.aop.windows.traits

import org.uqbar.arena.actions.MessageSend
import org.uqbar.arena.widgets.Button
import org.uqbar.arena.widgets.Panel
import com.uqbar.common.transaction.ObjectTransaction
import com.uqbar.common.transaction.TaskOwner
import com.uqbar.aop.transaction.ObjectTransactionManager
import org.uqbar.arena.windows.SimpleWindow
import org.uqbar.arena.aop.potm.PureObjectTransactionMonitorWindow
import org.uqbar.arena.aop.potm.ObjectTransactionImplObservable
import org.uqbar.commons.model.UserException

trait TransactionalWindowTrait[T] extends DialogTrait[T] with TaskOwner {

  var transaction: ObjectTransaction = _;
  var inTransaction = true;

  ObjectTransactionManager.begin(this);

  override def createMainTemplate(mainPanel: Panel) = {
    new Button(mainPanel).setCaption("Open Monitor").onClick(new MessageSend(this, "openMonitor"));
    super.createMainTemplate(mainPanel);
  }

  override def getName(): String = this.getTitle();

  override def getTransaction(): ObjectTransaction = this.transaction;

  override def isTransactional(): Boolean = true;

  override def setTransaction(transaction: ObjectTransaction) = { this.transaction = transaction };

  def execute(target: Any, methodName: String): MessageSend = {
    return new MessageSend(target, methodName) {

      override def execute() {
        try {
          ObjectTransactionManager.begin(TransactionalWindowTrait.this)
          ObjectTransactionManager.commit(TransactionalWindowTrait.this)
        } catch {
          case e: Exception => {
            ObjectTransactionManager.rollback(TransactionalWindowTrait.this)
            throw new UserException(e.toString(), e)
          }
        }
        super.execute();

      }
    }
  }

  override def executeTask() = {
    super.executeTask();
    ObjectTransactionManager.commit(this);
    this.inTransaction = false;
  }

  override def cancel() = {
    ObjectTransactionManager.rollback(this);
    this.inTransaction = false;
    super.cancel();
  }

  override def cancelTask() = {
    super.cancelTask();
    if (this.inTransaction) {
      ObjectTransactionManager.rollback(this);
    }
  }

  def openMonitor() = new PureObjectTransactionMonitorWindow(this, new ObjectTransactionImplObservable()).open();
}