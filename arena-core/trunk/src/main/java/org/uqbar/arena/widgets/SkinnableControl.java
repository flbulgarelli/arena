package org.uqbar.arena.widgets;

import java.awt.Color;

import org.uqbar.lacar.ui.model.SkinnableBuilder;

public abstract class SkinnableControl extends Control {
	private static final long serialVersionUID = 1L;
	
	private Color foreground;
	private Color background;
	private int fontSize;
	
	public SkinnableControl(Container container) {
		super(container);
	}
	
	public SkinnableControl setForeground(Color foreground) {
		this.foreground = foreground;
		return this;
	}

	public SkinnableControl setBackground(Color background) {
		this.background = background;
		return this;
	}

	public int getFontSize() {
		return fontSize;
	}

	public SkinnableControl setFontSize(int fontSize) {
		this.fontSize = fontSize;
		return this;
	}

	protected void configureSkineableBuilder(SkinnableBuilder builder){
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
