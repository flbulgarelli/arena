package org.uqbar.arena.potm

import org.uqbar.arena.windows.Window
import org.uqbar.arena.Application

class PureObjectTransactionMonitorApplication(var model:ObjectTransactionImplObservable) extends Application {
  
  protected  def createMainWindow():Window[ObjectTransactionImplObservable] = {
	  return new PureObjectTransactionMonitorPanel(this, model) 
  }
  
}