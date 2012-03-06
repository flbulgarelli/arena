package org.uqbar.arena.windows.tratis

import java.util.ArrayList
import java.util.List

import org.uqbar.arena.layout.VerticalLayout
import org.uqbar.arena.widgets.tables.Table
import org.uqbar.arena.widgets.Label
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.windows.Dialog
import org.uqbar.arena.windows.WindowOwner
import org.uqbar.arena.Application
import org.uqbar.commons.model.Search
//import ar.com.tadp.examples.presentacion.jface.simple.SearchWindows

object PruebaTraits {

    def main(args: Array[String]) {
      App.start()
	}

}

object App extends Application{
  
	override def createMainWindow():Window = {
		return new Window(this)
	}
  
}

class Model extends Search[String](classOf[String]){
  
  	override def doSearch(): List[String] = new ArrayList()

	override def clear() = {}

	override def removeSelected() = {}
  
}

class Window(owner:WindowOwner) extends SearchWindow[String, Model](owner, new Model()) with DialogTrait[Model] with TransactionalWindowsTrait[Model]{

  
	override def addActions(mainPanel:Panel )= {}
	override def createFormPanel(mainPanel:Panel )= {}
	
	override def describeResultsGrid(builder:Table[String]) = {}
	override def createEditor(selected:String):Dialog[Any] =  null

	override def createContents(mainPanel:Panel ) = {
		mainPanel.setLayout(new VerticalLayout());
		
		new Label(mainPanel).setText("State:");
		
		new Label(mainPanel).setText("Street:");
	}
	

}
