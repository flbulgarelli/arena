package org.uqbar.lacar.ui.impl.jface;

import org.eclipse.swt.widgets.Control;
import org.uqbar.arena.widgets.Color;
import org.uqbar.lacar.ui.model.SkineableBuilder;


public abstract class JFaceSkineableControlBuilder<T extends Control> extends JFaceControlBuilder<T>  implements SkineableBuilder{
	

	public JFaceSkineableControlBuilder(JFaceContainer container) {
		super(container);
	}
	
	public JFaceSkineableControlBuilder(JFaceContainer container, T jfaceWidget) {
		super(container, jfaceWidget);
	}
	
	
	@Override
	public void setForeground(Color color) {
		org.eclipse.swt.graphics.Color swtColor = getSWTColor(color);
		this.getWidget().setForeground(swtColor);
	}

	
	@Override
	public void setBackground(Color color) {
		org.eclipse.swt.graphics.Color swtColor = getSWTColor(color);
		this.getWidget().setBackground(swtColor);
	}
	
	
	protected org.eclipse.swt.graphics.Color getSWTColor(Color color) {
		int blue = color.getBlue();
		int green = color.getGreen();
		int red = color.getRed();
		org.eclipse.swt.graphics.Color swtColor = new org.eclipse.swt.graphics.Color(getWidget().getDisplay(), red, green, blue);
		return swtColor;
	}

}
