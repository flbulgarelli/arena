package org.uqbar.arena.aop.windows

import org.uqbar.arena.windows.SimpleWindow
import org.uqbar.arena.windows.WindowOwner
import org.uqbar.arena.aop.windows.traits.DialogTrait
import org.uqbar.arena.aop.windows.traits.TransactionalWindowTrait

abstract class TransactionalWindow[T](parent:WindowOwner, model:T) extends SimpleWindow[T](parent, model) with DialogTrait[T] with TransactionalWindowTrait[T] {


}