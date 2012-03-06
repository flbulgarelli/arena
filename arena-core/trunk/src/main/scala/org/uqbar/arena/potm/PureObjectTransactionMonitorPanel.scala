package org.uqbar.arena.potm
import scala.collection.JavaConverters.asScalaSetConverter
import scala.collection.JavaConverters.bufferAsJavaListConverter
import scala.collection.mutable.Buffer

import org.uqbar.arena.layout.VerticalLayout
import org.uqbar.arena.widgets.tables.Column
import org.uqbar.arena.widgets.tables.Table
import org.uqbar.arena.widgets.tree.Tree
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.windows.SimpleWindow
import org.uqbar.arena.windows.WindowOwner

import com.uqbar.renascent.framework.aop.transaction.IdentityWrapper
import com.uqbar.renascent.framework.aop.transaction.ObjectTransactionImpl

class PureObjectTransactionMonitorPanel(parent: WindowOwner, model: ObjectTransactionImplObservable) extends SimpleWindow[ObjectTransactionImplObservable](parent, model) {

  var ot: ObjectTransactionImpl = null

  override def createFormPanel(mainPanel: Panel) = {
    mainPanel.setLayout(new VerticalLayout())
    ot = getModelObject().getObjectTransaction()

    createTree(mainPanel)
    var panel = new Panel(mainPanel)
    panel.setLayout(new VerticalLayout)

    createList(panel)
    createTable(panel)
    
    mainPanel.setWidth(800);

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
    var keyColumn = new Column[Entry](table);
    keyColumn.setTitle("property");
    keyColumn.setFixedSize(200);
    keyColumn.bindContentsToProperty("key");

    var valueColumn = new Column[Entry](table);
    valueColumn.setTitle("value");
    valueColumn.setFixedSize(200);
    valueColumn.bindContentsToProperty("value");
    
    var fieldColumn = new Column[Entry](table);
    fieldColumn.setTitle("original value");
    fieldColumn.setFixedSize(200);
    fieldColumn.bindContentsToProperty("fieldValue");
  }

  def createList(panel: Panel) = {
    var list = new org.uqbar.arena.widgets.List(panel);
    list.bindContentsToProperty("listResult")
    list.onSelect(new Function[IdentityWrapper](onSelectionInList))
    list.setWidth(500);
    list.setHeigth(200);
  }

  def onSelectedItem(tI: ObjectTransactionImplObservable*) = {
    tI.first.getObjectTransaction() match {
      case objectT: ObjectTransactionImpl => {
        getModelObject().setListResult(convertSetToList(objectT.getAttributeMap().keySet()).asJava)
        this.ot = objectT
        getModelObject().setTableResult(Buffer().asJava)
      }
    }
  }

  def onSelectionInList(objects: IdentityWrapper*) {
    if (ot != null) {
      var provider = convertSetToListOfEntry(ot.getAttributeMap().get(objects.first).entrySet(), objects.first.getKey())
      if (provider == null) {
        provider = Buffer()
      }
      getModelObject().setTableResult(provider.asJava)
    }
  }

  def convertSetToList[T](set: java.util.Set[T]): Buffer[T] = {
    var b = Buffer[T]()
    set.asScala.foreach(p => b.append(p))
    return b;
  }

  def convertSetToListOfEntry[T](set: java.util.Set[T], source:Any): Buffer[Entry] = {
    var buffer = Buffer[Entry]()
    set.asScala.foreach(p => p match {
      case entry: { def getKey(): T; def getValue(): T } => buffer.append(new Entry(entry.getKey(), entry.getValue(), source))
    })
    return buffer
  }

  override def addActions(actionsPanel: Panel) = {}
}