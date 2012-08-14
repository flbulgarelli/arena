package org.uqbar.arena.aop.potm
import java.awt.Color

import scala.collection.JavaConverters._
import scala.collection.mutable.Buffer

import org.uqbar.arena.actions.MessageSend
import org.uqbar.arena.layout.VerticalLayout
import org.uqbar.arena.widgets.tables.Column
import org.uqbar.arena.widgets.tables.Table
import org.uqbar.arena.widgets.tree.Tree
import org.uqbar.arena.widgets.Button
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.windows.SimpleWindow
import org.uqbar.arena.windows.WindowOwner

import com.uqbar.aop.transaction.IdentityWrapper
import com.uqbar.aop.transaction.ObjectTransactionImpl

class PureObjectTransactionMonitorWindow(parent: WindowOwner, model: ObjectTransactionImplObservable)
  extends SimpleWindow[ObjectTransactionImplObservable](parent, model) with CollectionUtil {

  var ot: ObjectTransactionImpl = null

  override def createFormPanel(mainPanel: Panel) = {
    mainPanel.setLayout(new VerticalLayout())
    ot = getModelObject().getObjectTransaction()

    createTree(mainPanel)
    var panel = new Panel(mainPanel)
    panel.setLayout(new VerticalLayout)

    createList(panel)
    createTable(panel)
  }

  def createTree(panel: Panel) {
    var tree = new Tree(panel)
    tree.onClickItem(new Function[ObjectTransactionImplObservable](onSelectedItem))
    tree.bindContentsToProperty("parent", "children")
    tree.bindNodeToProperty("id");
    tree.setHeigth(200);
    tree.setWidth(700);

  }

  def createTable(mainPanel: Panel) {
    var table = new Table[Entry](mainPanel, classOf[Entry]);
    table.bindContentsToProperty("tableResult");
    table.setHeigth(200);
    table.setWidth(700);

    this.describeResultsGrid(table);
  }

  def describeResultsGrid(table: Table[Entry]) {
    new Column[Entry](table)
      .setTitle("property")
      .setFixedSize(200)
      .bindContentsToProperty("key");

    new Column[Entry](table)
      .setTitle("value")
      .setFixedSize(200)
      .bindContentsToProperty("value");

    new Column[Entry](table)
      .setTitle("original value")
      .setFixedSize(200)
      .bindContentsToProperty("fieldValue")
  }

  def createList(panel: Panel) = {
    var list = new org.uqbar.arena.widgets.List(panel);
    list.bindContentsToProperty("listResult")
    list.onSelect(new Function[IdentityWrapper](onSelectionInList))
    list.setWidth(500);
    list.setHeigth(200);
  }

  def onSelectedItem(tI: ObjectTransactionImplObservable*) = {
    tI.head.getObjectTransaction() match {
      case objectT: ObjectTransactionImpl => {
        getModelObject().setListResult(convertSetToList(objectT.getAttributeMap().keySet()))
        this.ot = objectT
        getModelObject().setTableResult(Buffer())
      }
    }
  }

  def onSelectionInList(objects: IdentityWrapper*) {
    if (ot != null) {
      var provider = convertSetToListOfEntry(ot.getAttributeMap().get(objects.head).entrySet(), objects.head.getKey())
      if (provider == null) {
        provider = Buffer()
      }
      getModelObject().setTableResult(provider)
    }
  }

  override def addActions(actionsPanel: Panel) = {
    var close = new Button(actionsPanel).setCaption("close")
    close.setFontSize(25).setWidth(100).setHeigth(50)
    close.onClick(new MessageSend(this, "close"))
  }
}