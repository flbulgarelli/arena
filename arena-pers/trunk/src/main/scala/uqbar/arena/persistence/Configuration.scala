package uqbar.arena.persistence

import com.uqbar.commons.descriptor.ClassDescriptor
import uqbar.arena.persistence.reflection.ClasspathCrawler
import uqbar.arena.persistence.configuration.EntityVisitor
import scala.collection.JavaConversions._
import scala.collection.mutable.HashMap
import uqbar.arena.persistence.mapping.EntityMapping
import scala.util.control.Exception
import uqbar.arena.persistence.mapping.EntityMapping
import uqbar.arena.persistence.mapping.EntityMapping
import org.uqbar.commons.model.Entity
import uqbar.arena.persistence.mapping.EntityMapping

object Configuration {

  var entities = new HashMap[String, EntityMapping[_]];
  protected var initialized = false;

  def clear() {
    entities = new HashMap[String, EntityMapping[_]];
  }

  def checkStarted() {
    if (!initialized)
      throw new ConfigurationException("Se debe inicializar la configuración de la persistencia llamando a Configure.configure() antes de realizar cualquier cosa.")
  }

  def configure() {
    try {
      val classDescriptor = new ClassDescriptor();

      val clazzes = new ClasspathCrawler(this.getClass().getClassLoader()).getClasses();

      for (clazz <- clazzes) {
        val visitor = new EntityVisitor(clazz.asInstanceOf[Class[Entity]]);
        classDescriptor.describe(clazz, visitor);
      }

      SessionManager.startDB();
      initialized = true;
    } catch {
      case e: RuntimeException if e.getCause() != null && e.getCause().getClass() == classOf[ConfigurationException] => throw e.getCause()
      case e => throw e
    }
  }

  def mappingFor[T <: Entity](clazz: Class[T]): EntityMapping[T] = {
    mappingByName(clazz.getName()).asInstanceOf[EntityMapping[T]]
  }

  def mappingFor[T <: Entity](target: T): EntityMapping[T] = {
    mappingFor(target.getClass()).asInstanceOf[EntityMapping[T]]
  }

  def mappingByName(className: String): EntityMapping[_] = {
    checkStarted();
    this.entities.get(className).orNull
  }
}

class ConfigurationException(message: String, cause: Throwable) extends Exception(message, cause) {
  def this(message: String) = this(message, null)
}