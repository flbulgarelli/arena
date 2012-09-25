package uqbar.arena.persistence

import org.uqbar.commons.model.Entity
import org.uqbar.commons.model.Home
import scala.collection.JavaConversions._
import java.util.List

class PersistentHomeImpl[T <: Entity] {
  
	def searchByExample(example:T):List[T] = {
	  SessionManager.runInSession( {s:Session => s.searchByExample(example)})
	}
	
	def allInstances(home:Home[T]):List[T] = {
	  SessionManager.runInSession( {s:Session => s.getAll(home.getEntityType())})
	}

	def searchById(id:Int,home:Home[T]):T={
  	  SessionManager.runInSession( {s:Session => s.get(home.getEntityType(), id)})
	}
	
	def create(entity:T){
	  SessionManager.runInSession( {s:Session => s.save(entity)})
	}

	def update(entity:T){
	  SessionManager.runInSession( {s:Session => s.save(entity)})
	}

	def delete(entity:T){
	  SessionManager.runInSession( {s:Session => s.delete(entity)})
	}
}