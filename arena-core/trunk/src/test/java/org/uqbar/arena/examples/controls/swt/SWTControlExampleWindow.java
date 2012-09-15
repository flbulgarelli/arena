package org.uqbar.arena.examples.controls.swt;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.SWTControlWidget;
import org.uqbar.arena.windows.MainWindow;

/**
 * 
 * @author jfernandes
 */
public class SWTControlExampleWindow extends MainWindow<Object> {

	public SWTControlExampleWindow() {
		super(new Object()); // el modelo no es importante acá.
	}

	/**
	 * SWTControlWidget es un control de arena que nos permite agregar controles de swt puro.
	 * Es una clase abstract, y tenemos que implementar el método abstracto "createSWTControl".
	 * Entonces estamos usando una clase anónima.
	 * En nuestro caso llamamos a otro método para que el código quede más claro
	 * y no meter tanto código en la clase anónima.
	 */
	@Override
	public void createContents(Panel mainPanel) {
		mainPanel.setLayout(new VerticalLayout());
		new SWTControlWidget(mainPanel) {
			// 
			@Override
			protected void createSWTControl(Composite container) {
				createSWTContent(container);
			}
		};
	}
	
	protected void createSWTContent(Composite container) {
		// creamos un textbox
		new Text(container, SWT.NONE);
		// creamos una imagen
		this.createImage(container);
	}

	protected void createImage(Composite container) {
		// en swt se crea un label y se le setea como background un objeto image.
		org.eclipse.swt.widgets.Label label = new org.eclipse.swt.widgets.Label(container, SWT.NONE);
		Image image = ImageDescriptor.createFromFile(SWTControlWidget.class, "/hermes-32x32.gif").createImage();
		label.setBackgroundImage(image);
	}

	public static void main(String[] args) {
		new SWTControlExampleWindow().startApplication();
	}
	
}
