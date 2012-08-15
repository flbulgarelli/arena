package org.uqbar.arena;

import org.uqbar.arena.aop.ArenaClassLoader;
import org.uqbar.arena.windows.Window;
import org.uqbar.arena.windows.WindowOwner;
import org.uqbar.lacar.ui.impl.jface.JFaceApplicationBuilder;
import org.uqbar.lacar.ui.model.ApplicationRunner;
import org.uqbar.lacar.ui.model.WindowFactory;

/**
 * Punto de entrada a una aplicación Arena.
 * 
 * @author npasserini
 */
public abstract class Application implements WindowOwner, Runnable {
	private ApplicationRunner delegate;

	public Application() {
		this.delegate = new JFaceApplicationBuilder();
		this.validateClassLoader();
	}

	/**
	 * Se fija que se esté usando el class loader necesario
	 */
	protected void validateClassLoader() {
		if (!this.getClass().getClassLoader().getClass().getName().equals(this.getNecesaryClassLoaderName())) {
			throw new RuntimeException(
					"Esta aplicación no está corriendo con el ClassLoader necesario. Corra la aplicación con el siguiente parámetro para la VM: -Djava.system.class.loader="
							+ this.getNecesaryClassLoaderName()
							+ ". El ClassLoader actual es: "
							+ this.getClass().getClassLoader());
		}
	}

	/**
	 * Devuelve el nombre del classLoader que es capaz de cargar la aplicación
	 * correctamente
	 * 
	 * @return
	 */
	protected String getNecesaryClassLoaderName() {
		return ArenaClassLoader.class.getName();
	}

	/**
	 * Arranca la aplicación. Este es el único mensaje que debería mandarse a la
	 * aplicación, el resto es manejado por el framework.
	 */
	public void start() {
		this.delegate.run(this);
	}

	/**
	 * Este método debe ser sobreescrito por las aplicaciones concretas, para
	 * crear la ventana principal de la aplicación.
	 */
	protected abstract Window<?> createMainWindow();

	// ********************************************************
	// ** Internal
	// ********************************************************

	/**
	 * ATENCIÓN: Este método es para uso interno del framework y no debe ser
	 * invocado directamente ni redefinido.
	 */
	@Override
	public void run() {
		this.createMainWindow().open();
	}

	/**
	 * ATENCIÓN: Este método es para uso interno del framework y no debe ser
	 * invocado directamente ni redefinido.
	 */
	@Override
	public WindowFactory getDelegate() {
		return this.delegate;
	}

}