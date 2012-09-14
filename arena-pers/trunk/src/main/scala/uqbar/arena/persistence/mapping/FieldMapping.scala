package uqbar.arena.persistence.mapping 

import java.lang.reflect.Type
import org.neo4j.graphdb.Node
import uqbar.arena.persistence.ConfigurationException
import uqbar.arena.persistence.reflection.TypeWrapper
import org.uqbar.commons.utils.ReflectionUtils
import java.util.Date
import java.lang.Long
import uqbar.arena.persistence.Session

class FieldMapping(name: String, fieldType: Type) extends Mapping {
  val wrappedType = new TypeWrapper(fieldType)
  checkNativeOrEnum();

  def checkNativeOrEnum() {
    if (!wrappedType.isNative && !wrappedType.isEnum && !wrappedType.isBuiltinType) {
      throw new ConfigurationException("La annotation PersistentField es solo aplicable a tipos nativos, Enum, String o java.util.Date:" + wrappedType.name);
    }
  }

  def persist(session:Session, node: Node, target: Object) = {
    var v = ReflectionUtils.invokeGetter(target, this.name);

    if(wrappedType.isEnum){
      v = if(v==null) null else v.toString()
    }
    
    if(wrappedType.isDate){
      v = if(v==null) null else v.asInstanceOf[Date].getTime().asInstanceOf[java.lang.Long];
    }
    
    if(v == null){
    	node.removeProperty(this.name);
    }else{
    	node.setProperty(this.name, v);
    }
  }
  
  def hidrate(session:Session, node: Node, target: Object) = {
    var v:Object = node.getProperty(this.name,null)
    
    if(wrappedType.isEnum){
      v = wrappedType.enumValue(v)
    }

    if(wrappedType.isDate){
      v = new Date(v.asInstanceOf[java.lang.Long])
    }
    
    ReflectionUtils.invokeSetter(target, this.name, v)
  }
}
