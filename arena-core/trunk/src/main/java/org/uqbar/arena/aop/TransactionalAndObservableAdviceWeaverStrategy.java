package org.uqbar.arena.aop;

import java.util.Map;
import java.util.Map.Entry;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.expr.ExprEditor;

import org.uqbar.commons.utils.Observable;
import org.uqbar.commons.utils.Transactional;
import org.uqbar.commons.utils.TransactionalAandObservable;

import com.uqbar.commons.collections.Predicate;
import com.uqbar.poo.aop.ObservableBehaviorAdviceWeaverStrategy;
import com.uqbar.pot.aop.TransactionalBehaviorAdviceWeaverStrategy;
import com.uqbar.renascent.aop.pointcut.predicate.HasAnnotationPredicate;
import com.uqbar.renascent.aop.pointcut.predicate.OrPredicate;
import com.uqbar.renascent.aop.pointcut.predicate.SuperClassPredicate;
import com.uqbar.renascent.framework.aop.AdviceWeaver;
import com.uqbar.renascent.framework.aop.IBehaviorAdviceWeaverStrategy;
import com.uqbar.renascent.framework.aop.WeavingInstrumentor;

/**
 */
public class TransactionalAndObservableAdviceWeaverStrategy  implements IBehaviorAdviceWeaverStrategy{
	
	private ObservableBehaviorAdviceWeaverStrategy observableBehaviorAdviceWeaverStrategy = new ObservableBehaviorAdviceWeaverStrategy();
	private TransactionalBehaviorAdviceWeaverStrategy transactionalBehaviorAdviceWeaverStrategy = new TransactionalBehaviorAdviceWeaverStrategy();
	

	@SuppressWarnings("unchecked")
	public void addInstrumentors(ClassPool classPool,
			Map<Predicate<CtClass>, ExprEditor> weavingInstrumentors) {
		
		WeavingInstrumentor weavingInstrumentor = new WeavingInstrumentor();
		
		observableBehaviorAdviceWeaverStrategy.configureInstrumentor(weavingInstrumentor);
		transactionalBehaviorAdviceWeaverStrategy.configureInstrumentor(weavingInstrumentor);
		
		// transactions - 
		weavingInstrumentors.put(new OrPredicate<CtClass>(
				new HasAnnotationPredicate(classPool, Transactional.class.getName()),
				new HasAnnotationPredicate(classPool, Observable.class.getName()),
			new HasAnnotationPredicate(classPool, TransactionalAandObservable.class.getName())),
			weavingInstrumentor);
		
		// observable - 
//		weavingInstrumentors.put(new OrPredicate<CtClass>(
//			new HasAnnotationPredicate(classPool, TransactionalAandObservable.class.getName())),
//			observableWeavingInstrumentor);

		
	}

	@Override
	public void applyAdviceToCtClass(CtClass ctClass,
			Entry<Predicate<CtClass>, ExprEditor> entry)
			throws CannotCompileException {
		transactionalBehaviorAdviceWeaverStrategy.applyAdviceToCtClass(ctClass, entry);
		observableBehaviorAdviceWeaverStrategy.applyAdviceToCtClass(ctClass, entry);
		
	}

	@Override
	public void configureInstrumentor(WeavingInstrumentor instrumentor) {
	}
	
}