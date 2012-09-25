package uqbar.arena.persistence.configuration

import java.lang.reflect.Method

import org.apache.commons.lang.StringUtils
import org.uqbar.commons.model.Entity

import uqbar.arena.persistence.Configuration
import uqbar.arena.persistence.ConfigurationException
import uqbar.arena.persistence.annotations.PersistentClass
import uqbar.arena.persistence.annotations.PersistentField
import uqbar.arena.persistence.annotations.Relation
import uqbar.arena.persistence.mapping.EntityMapping
import uqbar.arena.persistence.mapping.FieldMapping
import uqbar.arena.persistence.mapping.RelationMapping

class EntityVisitor() {
  var entity: EntityMapping[_] = null

  def classAnotation[T <: Entity](clazz: Class[T], annotation: PersistentClass) {
    entity = new EntityMapping(clazz)
    Configuration.entities.put(clazz.getCanonicalName(), entity)
  }

  def methodAnnotation(clazz: Class[_], method: Method, annotation: PersistentField) {
	val name = extractName(method,annotation.annotationType().getName())
	val fieldType = method.getGenericReturnType();
	this.entity.mappings += FieldMapping.create(name, fieldType)
  }

  def methodAnnotation(clazz: Class[_], method: Method, annotation: Relation) {
	val name = extractName(method,annotation.annotationType().getName())
	val fieldType = method.getGenericReturnType();
	this.entity.mappings += RelationMapping.create(name, fieldType)
  }
  
  def extractName(method:Method, annotationName:String):String = {
    if(!method.getName().startsWith("get")){
      throw new ConfigurationException("La annotation " + annotationName + " solo es válida anotando un getter");
    }
    return StringUtils.uncapitalize(method.getName().substring(3));
  }
}