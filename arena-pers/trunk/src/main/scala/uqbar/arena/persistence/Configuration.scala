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

object Configuration {

  var entities = new HashMap[String, EntityMapping];

  def clear() {
    entities = new HashMap[String, EntityMapping];
  }

  def configure() {
    try {
      val classDescriptor = new ClassDescriptor();
      val visitor = new EntityVisitor();

      val clazzes = new ClasspathCrawler(this.getClass().getClassLoader()).getClasses();

      for (clazz <- clazzes) {
        classDescriptor.describe(clazz, visitor);
      }
      
      SessionManager.startDB();
    } catch {
      case e: RuntimeException if e.getCause() != null && e.getCause().getClass() == classOf[ConfigurationException] => throw e.getCause()
      case e => throw e
    }
  }

  def mappingFor(clazz: Class[_]): EntityMapping = {
    mappingFor(clazz.getName())
  }

  def mappingFor(target: Object): EntityMapping = {
    mappingFor(target.getClass())
  }

  def mappingFor(className: String): EntityMapping = {
    this.entities.get(className).orNull
  }
}

class ConfigurationException(message: String, cause: Throwable) extends Exception(message, cause) {
  def this(message: String) = this(message, null)
}