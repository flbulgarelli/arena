package uqbar.arena.persistence.mapping

import java.lang.reflect.Type
import org.neo4j.graphdb.Node
import uqbar.arena.persistence.ConfigurationException
import uqbar.arena.persistence.reflection.TypeWrapper
import org.uqbar.commons.utils.ReflectionUtils
import java.util.Date
import java.lang.Long
import uqbar.arena.persistence.Session
import java.math.BigDecimal

object FieldMapping {
  def create(name:String, fieldType:Type):FieldMapping = {
    return new FieldMapping(name,fieldType)
  }
}

class FieldMapping(name: String, fieldType: Type) extends Mapping {
  val wrappedType = new TypeWrapper(fieldType)
  checkNativeOrEnum();

  def checkNativeOrEnum() {
    if (!wrappedType.isNative && !wrappedType.isEnum && !wrappedType.isBuiltinType) {
      throw new ConfigurationException("La annotation PersistentField es solo aplicable a tipos nativos, Enum, String, java.util.Date o java.math.BigDecimal:" + wrappedType.name);
    }
  }

  def persist(session: Session, node: Node, target: Object) = {
    val v = getValue(target)

    if (v == null) {
      node.removeProperty(this.name);
      session.graphDB.index().forNodes("Attr").remove(node, this.name)
    } else {
      session.graphDB.index().forNodes("Attr-"+ target.getClass().toString()).add(node, this.name, v)
      node.setProperty(this.name, v);
    }
  }

  protected def getValue(target: Object): Object = {
    var v = ReflectionUtils.invokeGetter(target, this.name);

    if (wrappedType.isEnum) {
      v = if (v == null) null else v.toString()
    }

    if (wrappedType.isDate) {
      v = if (v == null) null else v.asInstanceOf[Date].getTime().asInstanceOf[java.lang.Long];
    }
    
    if(wrappedType.isBigDecimal){
      v = if (v == null) null else v.toString();
    }
    
    return v
  }

  def hidrate(session: Session, node: Node, target: Object) = {
    var v: Object = node.getProperty(this.name, null)

    if (wrappedType.isEnum) {
      v = wrappedType.enumValue(v)
    }

    if (wrappedType.isDate) {
      v = new Date(v.asInstanceOf[java.lang.Long])
    }

    if(wrappedType.isBigDecimal){
    	v = new BigDecimal(v.asInstanceOf[String])
    }
    
    ReflectionUtils.invokeSetter(target, this.name, v)
  }

  def query(queryBuilder: QueryBuilder, target: Object) {
    val v = this.getValue(target);
    if (v != null) {
      val stringValue = v.toString()
      if(!stringValue.isEmpty())
    	  queryBuilder.add(this.name, stringValue)
    }
  }
}
