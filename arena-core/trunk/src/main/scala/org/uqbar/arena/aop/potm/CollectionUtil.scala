package org.uqbar.arena.aop.potm
import scala.collection.mutable.Buffer
import scala.collection.JavaConverters.asScalaSetConverter

trait CollectionUtil {
  def convertSetToList[T](set: java.util.Set[T]): List[T] = {
    var b = List[T]()
    set.asScala.foreach(p => b.::(p))
    return b;
  }

  def convertSetToListOfEntry[T](set: java.util.Set[T], source: Any): Buffer[Entry] = {
    var buffer = Buffer[Entry]()
    set.asScala.foreach(p => p match {
      case entry: { def getKey(): T; def getValue(): T } => buffer.append(new Entry(entry.getKey(), entry.getValue(), source))
    })
    return buffer
  }
}