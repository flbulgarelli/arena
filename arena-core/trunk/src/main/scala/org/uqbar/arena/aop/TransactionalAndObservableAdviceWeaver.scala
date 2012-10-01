package org.uqbar.arena.aop
import org.uqbar.commons.utils.Observable
import org.uqbar.commons.utils.Transactional
import org.uqbar.commons.utils.TransactionalAndObservable

import com.uqbar.aop.pointcut.predicate.AnnotationPointCut
import com.uqbar.aop.pointcut.predicate.FieldPointCut
import com.uqbar.aop.pointcut.predicate.PointCut
import com.uqbar.aop.Advice
import com.uqbar.aop.AdviceWeaver
import com.uqbar.aop.JoinPoint
import com.uqbar.aop.MethodInterceptor
import com.uqbar.poo.aop.TObservableAdviceWeaver
import com.uqbar.pot.aop.TTransactionalAdviceWeaver

import javassist.ClassPool

/**
 * 
 * @author ?
 */
class PrintMethodInterceptor extends MethodInterceptor {

  before((method) => "System.out.println('" + "antes1->" + method.getName() + "');")
  before((method) => "System.out.println('" + "antes2->" + method.getName() + "');")
  after((method) => "System.out.println('" + "despues->" + method.getName() + "');")

  override def getSpecificPropertyKey() = "ObservableFieldAccessInterceptor"
}

trait TTransactionalAndObservableAdviceWeaver extends TObservableAdviceWeaver with TTransactionalAdviceWeaver {
  val methodInterceptor = new PrintMethodInterceptor()

  override def configureAdvices() {
    // Transactional
    var transactionalWI = new JoinPoint();
    transactionalWI.addInterceptor(transactionInterceptor);
    advices.append(new Advice(new PointCut with AnnotationPointCut with FieldPointCut {
      hasAnnotation(classOf[Transactional].getName())
    }, transactionalWI));
    
    // Observable
    var observableWI = new JoinPoint();
    observableWI.addInterceptor(observableFieldInterceptor);
    advices.append(new Advice(new PointCut with AnnotationPointCut with FieldPointCut {
      hasAnnotation(classOf[Observable].getName())
    }, observableWI));

    // Transactional and observable
    var observableAndTransactionalWI = new JoinPoint();
    this.configureJoinPoint(observableAndTransactionalWI)
    advices.append(new Advice(new PointCut with AnnotationPointCut with FieldPointCut {
      hasAnnotation(classOf[TransactionalAndObservable].getName())
    }, observableAndTransactionalWI));

    //    advices.append(new Advice(
    //      new PointCut with MatchPointCut with ClassPointCut with MethodPointCut {
    //        matchClassName(_.contains("FakeObject"))
    //        matchMethodName(_.toLowerCase().contains("age")
    //            )
    //        //        matchReturnType((typ) => typ.isInstanceOf[Void])
    //      },
    //      new JoinPoint().addInterceptor(methodInterceptor)))

  }

  override def configureJoinPoint(joinPoint: JoinPoint) {
    super.configureJoinPoint(joinPoint)
    //    joinPoint.addAccessInterceptor(methodInterceptor)
  }

}

class TransactionalAndObservableAdviceWeaver(cp: ClassPool) extends AdviceWeaver(cp) with TTransactionalAndObservableAdviceWeaver {
}
