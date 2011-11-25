package org.uqbar.lacar.ui.impl.jface;

import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.uqbar.lacar.ui.impl.jface.bindings.JFaceBindingBuilder;
import org.uqbar.lacar.ui.model.BindingBuilder;

/**
 * Construye un campo de texto simple.
 * 
 * @author npasserini
 */
public class JFaceTextBuilder extends JFaceSkineableControlBuilder<Text> {

	public JFaceTextBuilder(JFaceContainer container) {
		super(container, new Text(container.getJFaceComposite(), SWT.SINGLE | SWT.BORDER));
	}

	@Override
	public BindingBuilder observeValue() {
		return new JFaceBindingBuilder(this, SWTObservables.observeText(this.getWidget(), SWT.Modify));
	}
}
