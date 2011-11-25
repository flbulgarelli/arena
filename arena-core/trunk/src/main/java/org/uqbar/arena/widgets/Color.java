package org.uqbar.arena.widgets;


public class Color {
	
	public static final Color RED = new Color(255, 0, 0);
	public static final Color BLUE = new Color(0, 0, 255);
	public static final Color GREEN = new Color(0, 255, 0);
	
	private int red;
	private int green;
	private int blue;
	
	private Color(int red, int green, int blue) {
		this.setRed(red);
		this.setGreen(green);
		this.setBlue(blue);
	}
	
	public void setRed(int red) {
		this.red = red;
	}

	public int getRed() {
		return red;
	}

	public void setGreen(int green) {
		this.green = green;
	}

	public int getGreen() {
		return green;
	}

	public void setBlue(int blue) {
		this.blue = blue;
	}

	public int getBlue() {
		return blue;
	}


}
