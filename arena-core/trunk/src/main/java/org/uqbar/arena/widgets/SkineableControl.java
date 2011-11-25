package org.uqbar.arena.widgets;

import org.uqbar.lacar.ui.model.SkineableBuilder;

public abstract class SkineableControl extends Control {
	private static final long serialVersionUID = 1L;
	
	private Color foreground;
	private Color background;
	
	public SkineableControl(Panel container) {
		super(container);
	}
	
	public void setForeground(Color foreground) {
		this.foreground = foreground;
	}

	public void setBackground(Color background) {
		this.background = background;
	}

	protected void configureSkineableBuilder(SkineableBuilder builder){
		if(foreground != null){
			builder.setForeground(foreground);			
		}
		if(background != null){
			builder.setBackground(background);			
		}
	}

}
