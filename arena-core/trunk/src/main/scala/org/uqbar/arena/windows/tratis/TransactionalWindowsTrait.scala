package org.uqbar.arena.windows.tratis
import org.uqbar.arena.actions.MessageSend
import org.uqbar.arena.potm.PureObjectTransactionMonitorPanel
import org.uqbar.arena.widgets.Button
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.windows.tratis.DialogTrait
import com.uqbar.renascent.common.transaction.ObjectTransaction
import com.uqbar.renascent.common.transaction.TaskOwner
import org.uqbar.arena.potm.ObjectTransactionImplObservable
import com.uqbar.renascent.framework.aop.transaction.ObjectTransactionManager

trait TransactionalWindowsTrait[T] extends DialogTrait[T] with TaskOwner {
	
	var transaction:ObjectTransaction = null;
	var inTransaction = true;
	
	ObjectTransactionManager.begin(this);
	
	override def createMainTemplate(mainPanel:Panel) = {
		new Button(mainPanel).setCaption("Open Monitor").onClick(new MessageSend(this, "openMonitor"));
		super.createMainTemplate(mainPanel);
	}
	
	
	override def getName():String = this.getTitle();
	
	override def getTransaction():ObjectTransaction  = this.transaction;

	override def isTransactional():Boolean = true;

	override def setTransaction(transaction:ObjectTransaction) = {this.transaction = transaction};
	
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
	
	override def cancelTask()= {
		super.cancelTask();
		if (this.inTransaction) {
			ObjectTransactionManager.rollback(this);
		}
	}
	
	def openMonitor() = new PureObjectTransactionMonitorPanel(this, new ObjectTransactionImplObservable()).open();
}