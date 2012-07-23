package org.uqbar.arena.aop;

import java.util.List;

import javassist.ClassPool;
import javassist.CtClass;

import org.uqbar.commons.utils.Observable;
import org.uqbar.commons.utils.Transactional;
import org.uqbar.commons.utils.TransactionalAndObservable;

import com.uqbar.aop.Advice;
import com.uqbar.aop.BehaviorAdviceWeaverStrategy;
import com.uqbar.aop.WeavingInstrumentor;
import com.uqbar.aop.pointcut.predicate.HasAnnotationPredicate;
import com.uqbar.poo.aop.ObservableBehaviorAdviceWeaverStrategy;
import com.uqbar.pot.aop.TransactionalBehaviorAdviceWeaverStrategy;

/**
 */
public class TransactionalAndObservableAdviceWeaverStrategy  implements BehaviorAdviceWeaverStrategy{
	
	private ObservableBehaviorAdviceWeaverStrategy observableBehaviorAdviceWeaverStrategy = new ObservableBehaviorAdviceWeaverStrategy();
	private TransactionalBehaviorAdviceWeaverStrategy transactionalBehaviorAdviceWeaverStrategy = new TransactionalBehaviorAdviceWeaverStrategy();
	

	public void addAdvices(ClassPool classPool, List<Advice> advices) {
		
		WeavingInstrumentor observableWI = new WeavingInstrumentor();
		
		WeavingInstrumentor transactionalWI = new WeavingInstrumentor();
		
		WeavingInstrumentor ObservableAndTransactionalWI = new WeavingInstrumentor();
		
		observableBehaviorAdviceWeaverStrategy.configureInstrumentor(observableWI);
		transactionalBehaviorAdviceWeaverStrategy.configureInstrumentor(transactionalWI);
		
		observableBehaviorAdviceWeaverStrategy.configureInstrumentor(ObservableAndTransactionalWI);
		transactionalBehaviorAdviceWeaverStrategy.configureInstrumentor(ObservableAndTransactionalWI);

		
		// transactional
		advices.add(new Advice(new HasAnnotationPredicate(classPool, Transactional.class.getName()), transactionalWI));

		// Obbservable 
		advices.add(new Advice(new HasAnnotationPredicate(classPool, Observable.class.getName()),
				observableWI));
		
		// transactional and observable
		advices.add(new Advice(new HasAnnotationPredicate(classPool, TransactionalAndObservable.class.getName()),
			ObservableAndTransactionalWI));
	}

	@Override
	public void applyAdviceToCtClass(CtClass ctClass, Advice advice)	{
		transactionalBehaviorAdviceWeaverStrategy.applyAdviceToCtClass(ctClass, advice);
		observableBehaviorAdviceWeaverStrategy.applyAdviceToCtClass(ctClass, advice);
	}

	@Override
	public void configureInstrumentor(WeavingInstrumentor instrumentor) {
	}
}