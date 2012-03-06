package org.uqbar.arena.potm;

import java.util.ArrayList;
import java.util.List;

import org.uqbar.commons.model.ObservableObject;
import org.uqbar.commons.utils.Observable;
import org.uqbar.commons.utils.Transactional;

import com.uqbar.renascent.common.transaction.ObjectTransaction;
import com.uqbar.renascent.framework.aop.transaction.IdentityWrapper;
import com.uqbar.renascent.framework.aop.transaction.ObjectTransactionImpl;
import com.uqbar.renascent.framework.aop.transaction.ObjectTransactionManager;

@Observable
public class ObjectTransactionImplObservable{
	
	private  transient ObjectTransactionImpl objectTransaction;
	private  List<Entry> tableResult = new ArrayList<Entry>();
	private  List<?> listResult = new ArrayList<Object>();
	

	public ObjectTransactionImplObservable() {
		this((ObjectTransactionImpl) ObjectTransactionManager.getTransaction());
	}
	
	public ObjectTransactionImplObservable(ObjectTransaction objectTransaction) {
		this((ObjectTransactionImpl)objectTransaction);
	}

	public ObjectTransactionImplObservable(ObjectTransactionImpl objectTransaction) {
		this.setObjectTransaction(objectTransaction);
	}
	
	
	public ObjectTransactionImpl getObjectTransaction() {
		return objectTransaction;
	}

	public void setObjectTransaction(ObjectTransactionImpl objectTransaction) {
		this.objectTransaction = objectTransaction;
	}
	
	public List<IdentityWrapper> getIdentityWrapper(){
		return new ArrayList<IdentityWrapper>(this.objectTransaction.getAttributeMap().keySet());
	}

	public List<Entry> getTableResult() {
		return tableResult;
	}

	public void setTableResult(List<Entry> tableResult) {
		this.tableResult = tableResult;
	}

	public List<?> getListResult() {
		return listResult;
	}

	public void setListResult(List<?> listResult) {
		this.listResult = listResult;
	}
	
	public List<Object> getChildren(){
		List<Object> result = new ArrayList<Object>();
		if(objectTransaction != null){
			result.add(new ObjectTransactionImplObservable(objectTransaction));	
			objectTransaction = (ObjectTransactionImpl) objectTransaction.getParent();
		}
		return result;
				
	}
	
	public Long getId(){
		return objectTransaction.getId();
	}
	
	public Object getParent(){
		return objectTransaction.getParent();
	}


}
