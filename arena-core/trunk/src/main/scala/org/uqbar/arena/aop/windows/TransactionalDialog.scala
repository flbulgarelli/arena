package org.uqbar.arena.aop.windows;

import org.uqbar.arena.actions.MessageSend
import org.uqbar.arena.aop.potm.ObjectTransactionImplObservable
import org.uqbar.arena.aop.potm.PureObjectTransactionMonitorWindow
import org.uqbar.arena.windows.Dialog
import org.uqbar.arena.windows.WindowOwner
import org.uqbar.commons.model.UserException
import com.uqbar.aop.transaction.ObjectTransactionManager
import com.uqbar.common.transaction.ObjectTransaction
import com.uqbar.common.transaction.TaskOwner
import org.uqbar.arena.widgets.Button
import org.uqbar.arena.widgets.Panel
import org.uqbar.lacar.ui.model.Action

abstract class TransactionalDialog[T](owner: WindowOwner, model: T) extends Dialog[T](owner, model) with TaskOwner {
	var transaction: ObjectTransaction = null

	ObjectTransactionManager.begin(this)
	var inTransaction: Boolean = true

	//	protected override def createMainTemplate(mainPanel: Panel) = {
	//		new Button(mainPanel)
	//			.setCaption("Open Monitor")
	//			.onClick(new MessageSend(this, "openMonitor"))
	//			.setWidth(100)
	//			.setHeigth(25);
	//		super.createMainTemplate(mainPanel);
	//	}

	override def getName(): String = this.getTitle()

	override def getTransaction(): ObjectTransaction = this.transaction

	override def isTransactional(): Boolean = true

	override def setTransaction(transaction: ObjectTransaction): Unit = this.transaction = transaction

	override def cancel(): Unit = {
		this.inTransaction = false
		this.rollback()
		super.cancel()
	}

	override def close(): Unit = {
		super.close()
		if (this.inTransaction) this.rollback()
	}

	override def accept(): Unit = doTransactionally (super.accept())

	def execute(target: Object, methodName: String): MessageSend =
		new MessageSend(target, methodName) {
			override def execute(): Unit = doTransactionally({
				return super.execute()
			})
		}

	def doTransactionally[A](transactionBody: => A): A = {
		try {
			ObjectTransactionManager.begin(this)
			val result = transactionBody
			ObjectTransactionManager.commit(this)
			return result
		} catch {
			case e: Exception =>
				ObjectTransactionManager.rollback(TransactionalDialog.this)
				throw new UserException(e.getMessage(), e)
		}
	}

	def rollback(): Unit = {
		ObjectTransactionManager.rollback(this)
		this.inTransaction = false
	}

	def commit(): Unit = {
		ObjectTransactionManager.commit(this)
		this.inTransaction = false
	}

	def openMonitor(): Unit =
		new PureObjectTransactionMonitorWindow(this.getOwner(), new ObjectTransactionImplObservable()).open();

}