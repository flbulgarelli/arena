package org.uqbar.arena.aop.potm

import org.uqbar.arena.windows.Window
import org.uqbar.arena.Application
import org.uqbar.commons.utils.Observable

class PureObjectTransactionMonitorApplication(var model:ObjectTransactionImplObservable) extends Application {
  
  protected  def createMainWindow():Window[ObjectTransactionImplObservable] = {
	  return new PureObjectTransactionMonitorWindow(this, model) 
  }
  
}