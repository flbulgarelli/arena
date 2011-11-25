package org.unqbar.arena.aop;

import javassist.ClassPool;

import com.uqbar.renascent.framework.aop.AdviceWeaver;
import com.uqbar.renascent.framework.aop.FrameworkClassLoader;


/**
 * Nuestro classloader, que al cargar una clase, le hace weaving para meterle la magia de aspectos.
 * 
 * Parametro para correr tests con este ClassLoader.
 * 
 * -Djava.system.class.loader=org.unqbar.arena.aop.VideoclubClassLoader
 */
public class VideoclubClassLoader extends FrameworkClassLoader {

	public VideoclubClassLoader(ClassLoader parent) {
		super(parent);
	}
	

	protected AdviceWeaver createAdviceWeaver(ClassPool cp) {
		return new AdviceWeaver(cp, new TransactionalAndObservableAdviceWeaverStrategy());
	}

}
