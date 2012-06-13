package org.uqbar.arena.aop.potm
import org.uqbar.commons.utils.ReflectionUtils
import org.uqbar.commons.utils.TransactionalAndObservable

class Entry(var key:Any, var value:Any, var source:Any) {
  
  def getKey() = key
  
  def getValue() = value
  
  def getFieldValue() = ReflectionUtils.readField(source, this.key+"")
}
  
