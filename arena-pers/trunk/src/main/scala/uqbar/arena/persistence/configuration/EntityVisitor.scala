package uqbar.arena.persistence.configuration

import uqbar.arena.persistence.annotations.PersistentClass
import scala.collection.mutable.Set
import scala.collection.mutable.HashSet
import uqbar.arena.persistence.Configuration

class EntityVisitor(configuration:Configuration) {
	var entity:EntityDescriptor = null
	
	def classAnotation(clazz:Class[_], annotation:PersistentClass){
	 	entity = new EntityDescriptor(clazz);
	}
}