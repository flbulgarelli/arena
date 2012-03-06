package org.uqbar.arena.windows

import org.uqbar.arena.windows.tratis.TransactionalWindowsTrait
import org.uqbar.arena.windows.tratis.DialogTrait
import org.uqbar.arena.windows.SimpleWindow
import org.uqbar.arena.windows.WindowOwner

abstract class STransactionalDialog[T](owner:WindowOwner, model:T ) extends SimpleWindow[T](owner, model) with DialogTrait[T] with TransactionalWindowsTrait[T] {


}