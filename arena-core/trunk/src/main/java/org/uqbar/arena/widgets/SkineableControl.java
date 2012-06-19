package org.uqbar.arena.widgets;

import java.awt.Color;

import org.uqbar.lacar.ui.model.SkineableBuilder;

public abstract class SkineableControl extends Control {
	private static final long serialVersionUID = 1L;
	
	private Color foreground;
	private Color background;
	private int fontSize;
	
	public SkineableControl(Container container) {
		super(container);
	}
	
	public SkineableControl setForeground(Color foreground) {
		this.foreground = foreground;
		return this;
	}

	public SkineableControl setBackground(Color background) {
		this.background = background;
		return this;
	}

	public int getFontSize() {
		return fontSize;
	}

	public SkineableControl setFontSize(int fontSize) {
		this.fontSize = fontSize;
		return this;
	}

	protected void configureSkineableBuilder(SkineableBuilder builder){
		if(foreground != null){
			builder.setForeground(foreground);			
		}
		if(background != null){
			builder.setBackground(background);			
		}
		
		if(fontSize != 0){
			builder.setFontSize(fontSize);			
		}
	}

}
