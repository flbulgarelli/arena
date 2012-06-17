package org.uqbar.arena.isolation;

import com.uqbar.renascent.common.transaction.ObjectTransaction;
import com.uqbar.renascent.framework.aop.transaction.ObjectTransactionManager;

public enum IsolationLevelEvents {
	
	FIRE_ALL(""){

		@Override
		public boolean check(ObjectTransaction objectTransaction) {
			return true;
		}
		
	},
	
	FIRE_COMMITTED(""){

		@Override
		public boolean check(ObjectTransaction objectTransaction) {
			return objectTransaction.getId() >= ObjectTransactionManager.getTransaction().getId();
		}
		
	},
	
	FIRE_OlNLY_IN_MY_TRANSACTION(""){

		@Override
		public boolean check(ObjectTransaction objectTransaction) {
			return objectTransaction.getId() == ObjectTransactionManager.getTransaction().getId();
		}
		
	};
	

	private IsolationLevelEvents(final String code) {
//		super(code);
	}
	public abstract boolean check(ObjectTransaction objectTransaction);

}
