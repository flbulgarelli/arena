package org.unqbar.arena.aop;

import java.util.Map;
import java.util.Map.Entry;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.expr.ExprEditor;

import com.uqbar.commons.collections.Predicate;
import com.uqbar.poo.aop.ObservableBehaviorAdviceWeaverStrategy;
import com.uqbar.renascent.aop.pointcut.predicate.HasAnnotationPredicate;
import com.uqbar.renascent.aop.pointcut.predicate.OrPredicate;
import com.uqbar.renascent.aop.pointcut.predicate.SuperClassPredicate;
import com.uqbar.renascent.framework.aop.AdviceWeaver;
import com.uqbar.renascent.framework.aop.IBehaviorAdviceWeaverStrategy;
import com.uqbar.renascent.framework.aop.TransactionalBehaviorAdviceWeaverStrategy;
import com.uqbar.renascent.framework.aop.WeavingInstrumentor;

/**
 */
public class TransactionalAndObservableAdviceWeaverStrategy  implements IBehaviorAdviceWeaverStrategy{
	
	private ObservableBehaviorAdviceWeaverStrategy observableBehaviorAdviceWeaverStrategy = new ObservableBehaviorAdviceWeaverStrategy();
	private TransactionalBehaviorAdviceWeaverStrategy transactionalBehaviorAdviceWeaverStrategy = new TransactionalBehaviorAdviceWeaverStrategy();
	

	@SuppressWarnings("unchecked")
	public void addInstrumentors(ClassPool classPool,
			Map<Predicate<CtClass>, ExprEditor> weavingInstrumentors) {
		
		WeavingInstrumentor weavingInstrumentorSuperclassPredicate = new WeavingInstrumentor();

		observableBehaviorAdviceWeaverStrategy.configureInstrumentor(weavingInstrumentorSuperclassPredicate);
		transactionalBehaviorAdviceWeaverStrategy.configureInstrumentor(weavingInstrumentorSuperclassPredicate);
		
		weavingInstrumentors.put(new SuperClassPredicate(classPool, AdviceWeaver.CLASSNAME_PERSISTIBLE),
				weavingInstrumentorSuperclassPredicate);
					// persistence
		
		WeavingInstrumentor weavingInstrumentorOrPredicate = new WeavingInstrumentor();
		
		observableBehaviorAdviceWeaverStrategy.configureInstrumentor(weavingInstrumentorOrPredicate);
		transactionalBehaviorAdviceWeaverStrategy.configureInstrumentor(weavingInstrumentorOrPredicate);
		// transactions - collections
		weavingInstrumentors.put(new OrPredicate<CtClass>(
			new SuperClassPredicate(classPool, "com.uqbar.renascent.common.collections.Itr"),
			new SuperClassPredicate(classPool, "com.uqbar.renascent.common.collections.AbstractCollection"),
			new SuperClassPredicate(classPool, "com.uqbar.renascent.common.collections.SubListListIterator"),
			new SuperClassPredicate(classPool, "com.uqbar.renascent.common.collections.BasicList"),
			new HasAnnotationPredicate(classPool, "org.uqbar.commons.utils.Transactional")),
			weavingInstrumentorOrPredicate);

		
	}

	@Override
	public void applyAdviceToCtClass(CtClass ctClass,
			Entry<Predicate<CtClass>, ExprEditor> entry)
			throws CannotCompileException {
		observableBehaviorAdviceWeaverStrategy.applyAdviceToCtClass(ctClass, entry);
		transactionalBehaviorAdviceWeaverStrategy.applyAdviceToCtClass(ctClass, entry);
		
	}

	@Override
	public void configureInstrumentor(WeavingInstrumentor instrumentor) {
	}
	
}