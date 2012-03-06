package org.uqbar.arena.windows;

import org.uqbar.arena.actions.MessageSend;
import org.uqbar.arena.potm.ObjectTransactionImplObservable;
import org.uqbar.arena.potm.PureObjectTransactionMonitorPanel;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.commons.model.ObservableObject;

import com.uqbar.renascent.common.transaction.ObjectTransaction;
import com.uqbar.renascent.common.transaction.TaskOwner;
import com.uqbar.renascent.framework.aop.transaction.ObjectTransactionManager;

public abstract class TransactionalDialog<T> extends Dialog<T> implements TaskOwner {
	private static final long serialVersionUID = 1L;
	
	private ObjectTransaction transaction;
	private Boolean inTransaction;

	public TransactionalDialog(WindowOwner owner, T model) {
		super(owner, model);
		ObjectTransactionManager.begin(this);
		this.inTransaction = true;
	}
	
	@Override
	protected void createMainTemplate(Panel mainPanel) {
		new Button(mainPanel).setCaption("Open Monitor").onClick(new MessageSend(this, "openMonitor"));
		super.createMainTemplate(mainPanel);
	}
	
	
	@Override
	public String getName() {
		return this.getTitle();
	}
	
	@Override
	public ObjectTransaction getTransaction() {
		return this.transaction;
	}

	@Override
	public boolean isTransactional() {
		return true;
	}

	@Override
	public void setTransaction(ObjectTransaction transaction) {
		this.transaction = transaction;
	}
	
	@Override
	protected void executeTask() {
		super.executeTask();
		ObjectTransactionManager.commit(this);
		this.inTransaction = false;
	}
	
	@Override
	public void cancel() {
		ObjectTransactionManager.rollback(this);
		this.inTransaction = false;
		super.cancel();
	}
	
	@Override
	public void cancelTask() {
		super.cancelTask();
		if (this.inTransaction) {
			ObjectTransactionManager.rollback(this);
		}
	}
	
	public void openMonitor() {
		new PureObjectTransactionMonitorPanel(this, new ObjectTransactionImplObservable()).open();
	}
	
}
