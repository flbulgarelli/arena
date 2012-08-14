package org.uqbar.arena.aop.potm

import org.uqbar.lacar.ui.model.Action;
import org.uqbar.commons.utils.ReflectionUtils

class Function[A](var method: (A*) => Unit) extends Action {
  override def execute() = method(Seq())
  override def execute[B](objects: B*) = objects.head match { case p: A => method(Seq(p)) }
  def execute(a: A) = method(Seq(a))
}