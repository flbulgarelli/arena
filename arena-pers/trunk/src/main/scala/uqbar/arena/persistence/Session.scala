package uqbar.arena.persistence

import scala.collection.mutable.HashMap
import scala.collection.mutable.Map
import org.neo4j.graphdb.GraphDatabaseService
import org.uqbar.commons.model.Entity
import org.neo4j.graphdb.Transaction

class Session(graphDB: GraphDatabaseService) {
  protected var entities: Map[String, Object] = new HashMap[String, Object]()
  protected var transaction: Transaction = null

  def graphDB(): GraphDatabaseService = graphDB

  def beginTransaction() = { transaction = graphDB.beginTx() }
  
  def commit() = if(transaction!=null)transaction.success()
  def closeTransaction() = if(transaction!=null)transaction.finish()
  def rollback() = if(transaction!=null)transaction.failure()

  def registerEntity(e: Entity) {
    entities.put(e.getClass().getCanonicalName() + "#" + e.getId(), e)
  }
  
  def evictAll(){
    entities.clear()
  }

  def findEntity(clazzName: String, id: Int): Option[Object] = {
    entities.get(clazzName + "#" + id)
  }

  def findOrCreate(clazzName: String, id: Int): Object = {
    findEntity(clazzName, id) match {
      case Some(x) => return x
      case None => createEntity(clazzName, id)
    }
  }

  def get[T](clazzName: String, id: Int): T = {
    return findOrCreate(clazzName, id).asInstanceOf[T]
  }
  
  def get[T](clazz: Class[T], id: Int): T = {
    return findOrCreate(clazz.getName, id).asInstanceOf[T]
  }

  def save(obj:Entity){
    Configuration.mappingFor(obj).persist(this, obj);
  }
  
  def createEntity(clazzName: String, id: Int): Object = {
    val clazz = Class.forName(clazzName);
    val obj = clazz.newInstance().asInstanceOf[Entity]
    obj.setId(id)

    this.registerEntity(obj)

    Configuration.mappingFor(obj).hidrate(this, obj)
    
    return obj
  }
}