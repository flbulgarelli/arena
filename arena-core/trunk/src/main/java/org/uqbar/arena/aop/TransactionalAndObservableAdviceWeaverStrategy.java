package org.uqbar.arena.aop;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.expr.ExprEditor;

import org.uqbar.commons.utils.Observable;
import org.uqbar.commons.utils.Transactional;
import org.uqbar.commons.utils.TransactionalAndObservable;

import com.uqbar.poo.aop.ObservableBehaviorAdviceWeaverStrategy;
import com.uqbar.pot.aop.TransactionalBehaviorAdviceWeaverStrategy;
import com.uqbar.renascent.aop.pointcut.predicate.APredicate;
import com.uqbar.renascent.aop.pointcut.predicate.HasAnnotationPredicate;
import com.uqbar.renascent.aop.pointcut.predicate.OrPredicate;
import com.uqbar.renascent.aop.pointcut.predicate.SuperClassPredicate;
import com.uqbar.renascent.framework.aop.IBehaviorAdviceWeaverStrategy;
import com.uqbar.renascent.framework.aop.WeavingInstrumentor;

/**
 */
public class TransactionalAndObservableAdviceWeaverStrategy  implements IBehaviorAdviceWeaverStrategy{
	
	private ObservableBehaviorAdviceWeaverStrategy observableBehaviorAdviceWeaverStrategy = new ObservableBehaviorAdviceWeaverStrategy();
	private TransactionalBehaviorAdviceWeaverStrategy transactionalBehaviorAdviceWeaverStrategy = new TransactionalBehaviorAdviceWeaverStrategy();
	

	public void addInstrumentors(ClassPool classPool,
			Map<APredicate, ExprEditor> weavingInstrumentors) {
		
		WeavingInstrumentor observableWI = new WeavingInstrumentor();
		
		WeavingInstrumentor transactionalWI = new WeavingInstrumentor();
		
		WeavingInstrumentor ObservableAndTransactionalWI = new WeavingInstrumentor();
		
		observableBehaviorAdviceWeaverStrategy.configureInstrumentor(observableWI);
		transactionalBehaviorAdviceWeaverStrategy.configureInstrumentor(transactionalWI);
		
		observableBehaviorAdviceWeaverStrategy.configureInstrumentor(ObservableAndTransactionalWI);
		transactionalBehaviorAdviceWeaverStrategy.configureInstrumentor(ObservableAndTransactionalWI);

		
		// transactional
		weavingInstrumentors.put(new HasAnnotationPredicate(classPool, Transactional.class.getName()),
				transactionalWI);

		// Obbservable 
		weavingInstrumentors.put(new HasAnnotationPredicate(classPool, Observable.class.getName()),
				observableWI);
		
		// transactional and observable
		weavingInstrumentors.put(new HasAnnotationPredicate(classPool, TransactionalAndObservable.class.getName()),
			ObservableAndTransactionalWI);
	}

	@Override
	public void applyAdviceToCtClass(CtClass ctClass,
			Entry<APredicate, ExprEditor> entry)
			throws CannotCompileException {
		transactionalBehaviorAdviceWeaverStrategy.applyAdviceToCtClass(ctClass, entry);
		observableBehaviorAdviceWeaverStrategy.applyAdviceToCtClass(ctClass, entry);
		
	}

	@Override
	public void configureInstrumentor(WeavingInstrumentor instrumentor) {
	}
	
}