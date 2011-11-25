package org.uqbar.arena.windows;

import org.uqbar.commons.model.ObservableObject;
import org.uqbar.lacar.ui.model.Action;

import com.uqbar.renascent.common.transaction.ObjectTransaction;
import com.uqbar.renascent.common.transaction.TaskOwner;
import com.uqbar.renascent.framework.aop.transaction.ObjectTransactionManager;

public abstract class TransactionalDialog<T extends ObservableObject> extends Dialog<T> implements TaskOwner {
	private static final long serialVersionUID = 1L;
	
	private ObjectTransaction transaction;

	public TransactionalDialog(WindowOwner owner, T model) {
		super(owner, model);
		ObjectTransactionManager.begin(this);
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
		ObjectTransactionManager.commit(this);
	}
	
	@Override
	public void cancel() {
		ObjectTransactionManager.rollback(this);
		super.cancel();
	}
	
}
