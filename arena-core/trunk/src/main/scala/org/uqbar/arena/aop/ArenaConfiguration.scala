package org.uqbar.arena.aop
import org.uqbar.commons.utils.TransactionalAndObservable
import com.uqbar.aop.pointcut.AnnotationPointCut
import com.uqbar.aop.pointcut.FieldPointCut
import com.uqbar.aop.pointcut.PointCut
import com.uqbar.aop.Advice
import com.uqbar.poo.aop.ObservableConfiguration
import javassist.ClassPool
import com.uqbar.aop.AdviceWeaver
import com.uqbar.pot.aop.TransactionalConfiguration
import javassist.CtClass
/**
 *
 * @author nny
 */

class ArenaConfiguration extends ObservableConfiguration with TransactionalConfiguration {

  override def createAdvices(): List[Advice] = {
    val pointCut = new PointCut with AnnotationPointCut with FieldPointCut {
      hasAnnotation(classOf[TransactionalAndObservable].getName())
    }

    val OandTAdvice = new Advice(pointCut) {
      override def apply(ctClass: CtClass) {
        observableBehavior.addBehavior(ctClass)
        super.apply(ctClass)
      }

    }

    OandTAdvice
      .addInterceptor(transactionInterceptor)
      .addInterceptor(observableFieldInterceptor)

    super.createAdvices().::(OandTAdvice)
  }

}
