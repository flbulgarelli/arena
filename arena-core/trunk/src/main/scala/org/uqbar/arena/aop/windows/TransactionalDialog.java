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
	public void cancel() {
		this.inTransaction = false;
		this.rollback();
		super.cancel();
	}
	
	@Override
	public void close() {
		super.close();
		if (this.inTransaction) {
			this.rollback();
		}
	}
	
	@Override
	public void accept() {
		try{
			ObjectTransactionManager.begin(this);
			this.inTransaction = false;
			super.accept();
			ObjectTransactionManager.commit(this);
			this.commit();
		}catch(Exception e){
			this.inTransaction = true;
			ObjectTransactionManager.rollback(this);
			throw new UserException(e.toString(), e);
		}

	}
	
	
	public MessageSend execute(Object target, String methodName){
		return new MessageSend(target, methodName){
			
			@Override
			public void execute() {
				try{
					ObjectTransactionManager.begin(TransactionalDialog.this);
					super.execute();
					ObjectTransactionManager.commit(TransactionalDialog.this);
				}catch(Exception e){
					ObjectTransactionManager.rollback(TransactionalDialog.this);
					throw new UserException(e.toString(), e);
				}
			}
		};
	}

	protected void rollback() {
		ObjectTransactionManager.rollback(this);
		this.inTransaction = false;
	}
	
	protected void commit() {
		ObjectTransactionManager.commit(this);
		this.inTransaction = false;
	}

	public void openMonitor() {
		new PureObjectTransactionMonitorWindow(this.getOwner(), new ObjectTransactionImplObservable()).open();
	}
	
}
