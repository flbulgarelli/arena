package uqbar.arena.persistence

import com.uqbar.commons.descriptor.ClassDescriptor
import uqbar.arena.persistence.reflection.ClasspathCrawler
import uqbar.arena.persistence.configuration.EntityVisitor
import scala.collection.JavaConversions._

class Configuration {
	
	def configure(){
		val classDescriptor = new ClassDescriptor();
		val visitor = new EntityVisitor(this);
		
		val clazzes = new ClasspathCrawler(this.getClass().getClassLoader()).getClasses();

		for (clazz <- clazzes) {
			classDescriptor.describe(clazz, visitor);
		}
	}

}