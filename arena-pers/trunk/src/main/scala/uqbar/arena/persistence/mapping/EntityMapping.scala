package uqbar.arena.persistence.mapping

import java.lang.reflect.Type
import scala.collection.mutable.HashSet
import scala.collection.mutable.Set
import uqbar.arena.persistence.Session
import org.uqbar.commons.model.Entity
import org.neo4j.graphdb.Node

class EntityMapping(clazz: Class[_]) {
  var mappings = new HashSet[Mapping]();

  def persist(session: Session, target: Object) {
    val entity = target.asInstanceOf[Entity];
    val graphDB = session.graphDB

    var node: Node = null;

    if (entity.isNew)
    	node = graphDB.createNode()
    else
    	node = graphDB.getNodeById(entity.getId().longValue)

    entity.setId(node.getId().toInt)
    
    node.setProperty("clazzName", entity.getClass().getCanonicalName())
    
    session.registerEntity(entity)
    
    for (m <- mappings) {
      m.persist(session, node, target);
    }
  }

  def hidrate(session: Session, target: Object) {
    val entity = target.asInstanceOf[Entity];
    val graphDB = session.graphDB
    
    val node = graphDB.getNodeById(entity.getId().longValue)
    
    for (m <- mappings) {
      m.hidrate(session, node, target);
    }
  }

  def getAllFields(): Set[Mapping] = {
    this.mappings.filter({ x =>
      x match {
        case y: FieldMapping =>
          true
        case _ => false
      }
    })
  }
}

