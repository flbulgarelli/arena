package org.uqbar.arena.aop.potm

import org.uqbar.arena.windows.Window
import org.uqbar.arena.Application
import org.uqbar.arena.aop.poo.ObjectTransactionImplObservable

class PureObjectTransactionMonitorApplication(var model:ObjectTransactionImplObservable) extends Application {
  
  protected  def createMainWindow():Window[ObjectTransactionImplObservable] = {
	  return new PureObjectTransactionMonitorPanel(this, model) 
  }
  
}