package org.uqbar.arena.aop.potm
import com.uqbar.renascent.framework.aop.transaction.ObjectTransactionManager
import com.uqbar.renascent.common.transaction.ObjectTransaction
import com.uqbar.renascent.framework.aop.transaction.ObjectTransactionImpl
import scala.collection.mutable.Buffer
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import org.uqbar.commons.utils.Observable

@Observable
class ObjectTransactionImplObservable(var objectTransaction:ObjectTransactionImpl){
	
	var tableResult = Buffer[Entry]()
	var listResult = List[Any]()
	

	def this() {
		this(ObjectTransactionManager.getTransaction() match{
		  case obTransactionImp:ObjectTransactionImpl => obTransactionImp
		});
	}
	
	def this (objectTransaction:ObjectTransaction) {
		this(ObjectTransactionManager.getTransaction() match{
		  case obTransactionImp:ObjectTransactionImpl => obTransactionImp
		});
	}

	
	def getObjectTransaction() = objectTransaction

	def setObjectTransaction(objectTransaction:ObjectTransactionImpl ) = this.objectTransaction = objectTransaction
	
	def getIdentityWrapper() = this.objectTransaction.getAttributeMap().keySet().toList

	def getTableResult() = tableResult.asJava

	def setTableResult(tableResult:Buffer[Entry]) = this.tableResult = tableResult

	def getListResult() = listResult.asJava

	def setListResult[A](listResult:List[A]) = this.listResult = listResult.toList;
	
	def getChildren():java.util.List[Any] = {
		var result = Buffer[Any]();
		if(objectTransaction != null){
			result.append(new ObjectTransactionImplObservable(objectTransaction));
			objectTransaction.getParent() match {
			  case transactionImp:ObjectTransactionImpl => this.objectTransaction = transactionImp 
			  case _ => this.objectTransaction = null
			};
		}
		return result;
				
	}
	
	def getId() = objectTransaction.getId()
	
	def getParent() = objectTransaction.getParent()

}
