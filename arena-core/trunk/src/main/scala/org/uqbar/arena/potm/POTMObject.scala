package org.uqbar.arena.potm
import com.uqbar.renascent.framework.aop.transaction.ObjectTransactionManager
import com.uqbar.renascent.framework.aop.transaction.utils.BasicTaskOwner

/**
 * @author nny
 *-Djava.system.class.loader=org.unqbar.arena.aop.ArenaClassLoader
 */
object POTMObject {
  
  def main(args: Array[String]) {
    var tsko = new BasicTaskOwner("potm");
    var examples = List(new ExampleObservableObject("e1", 1), new ExampleObservableObject("e2", 2),new ExampleObservableObject("e3", 3))
    
    ObjectTransactionManager.begin(tsko)
    
    examples.foreach( e => {
      e.setName(e.getName()+ e.getId())
      e.setId(e.getId() * 10)
    })
    
    ObjectTransactionManager.begin(tsko)
       
    ObjectTransactionManager.begin(tsko)
    examples.foreach( e => {
      e.setName(e.getName() +e.getId())
      e.setId(e.getId() * 10)
    })  
    new PureObjectTransactionMonitorApplication(new ObjectTransactionImplObservable()).start();
    
    
    ObjectTransactionManager.commit(tsko)
    new PureObjectTransactionMonitorApplication(new ObjectTransactionImplObservable()).start();
    ObjectTransactionManager.commit(tsko)
    new PureObjectTransactionMonitorApplication(new ObjectTransactionImplObservable()).start();
    ObjectTransactionManager.commit(tsko)
    
    examples.foreach( e => {
      println(e.getName())
      println(e.getId())
    })
  }

}