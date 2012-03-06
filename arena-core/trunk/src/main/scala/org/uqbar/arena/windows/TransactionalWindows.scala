package org.uqbar.arena.windows

import org.uqbar.arena.windows.tratis.TransactionalWindowsTrait
import org.uqbar.arena.windows.SimpleWindow
import org.uqbar.arena.windows.WindowOwner
import tratis.DialogTrait

abstract class TransactionalWindows[T](parent:WindowOwner, model:T) extends SimpleWindow[T](parent, model) with DialogTrait[T] with TransactionalWindowsTrait[T] {


}