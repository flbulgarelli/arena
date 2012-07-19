package org.uqbar.arena.aop.windows;

import org.uqbar.arena.actions.MessageSend;
import org.uqbar.arena.aop.potm.ObjectTransactionImplObservable;
import org.uqbar.arena.aop.potm.PureObjectTransactionMonitorWindow;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.Window;
import org.uqbar.arena.windows.WindowOwner;
import org.uqbar.commons.model.UserException;

import com.uqbar.aop.transaction.ObjectTransactionManager;
import com.uqbar.common.transaction.ObjectTransaction;
import com.uqbar.common.transaction.TaskOwner;

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
		new Button(mainPanel).setCaption("Open Monitor").onClick(new MessageSend(this, "openMonitor")).setWidth(100).setHeigth(25);
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
		this.commit();
	}

	protected void commit() {
		ObjectTransactionManager.commit(this);
		this.inTransaction = false;
	}
	
	@Override
	public void cancel() {
		this.rollback();
		super.cancel();
	}
	
	
	public MessageSend execute(Object target, String methodName){
		return new MessageSend(target, methodName){
			
			@Override
			public void execute() {
				try{
					ObjectTransactionManager.begin(TransactionalDialog.this);
					ObjectTransactionManager.commit(TransactionalDialog.this);
				}catch(Exception e){
					ObjectTransactionManager.rollback(TransactionalDialog.this);
					throw new UserException(e.toString(), e);
				}
				super.execute();
			}
		};
	}

	protected void rollback() {
		ObjectTransactionManager.rollback(this);
		this.inTransaction = false;
	}
	
	@Override
	public void cancelTask() {
		super.cancelTask();
		if (this.inTransaction) {
			this.rollback();
		}
	}
	
	public void openMonitor() {
		new PureObjectTransactionMonitorWindow(((Window)this), new ObjectTransactionImplObservable()).open();
	}
	
}
