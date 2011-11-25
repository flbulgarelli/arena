package org.uqbar.arena.widgets;

import org.eclipse.swt.widgets.Composite;
import org.uqbar.lacar.ui.impl.jface.JFacePanelBuilder;
import org.uqbar.lacar.ui.model.PanelBuilder;

/**
 * Arena {@link Widget} que permite crear directamente un control swt.
 * 
 * Sirve para poder embeber controles swt mas complejos, aún no implementados
 * en arena.
 * 
 * @author jfernandes
 */
public abstract class SWTControlWidget extends Widget {

	public SWTControlWidget(Container container) {
		super(container);
	}

	@Override
	public void showOn(PanelBuilder container) {
		//hack: si estas usando esta clase es porque estas usando SWT!
		this.createSWTControl(((JFacePanelBuilder) container).getWidget());
	}
	
	/**
	 * La subclase debe implementar este método y crear el control swt. 
	 */
	protected abstract void createSWTControl(Composite container);

}
