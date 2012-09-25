package org.uqbar.arena.aop;

import javassist.ClassPool;

import com.uqbar.aop.APOClassLoader;
import com.uqbar.aop.AdviceWeaver;


/**
 * Nuestro classloader, que al cargar una clase, le hace weaving para meterle la magia de aspectos.
 * 
 * Parametro para correr tests con este ClassLoader.
 * 
 * -Djava.system.class.loader=org.uqbar.arena.aop.ArenaClassLoader
 */
public class ArenaClassLoader extends APOClassLoader {

	public ArenaClassLoader(ClassLoader parent) {
		super(parent);
	}
	

	protected AdviceWeaver createAdviceWeaver(ClassPool cp) {
		return new TransactionalAndObservableAdviceWeaver(cp);
	}

}
