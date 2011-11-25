package org.uqbar.arena.examples.controls.spinner;

import net.sf.oval.constraint.Max;
import net.sf.oval.constraint.Min;

import org.uqbar.commons.model.ObservableObject;

/**
 * 
 * @author jfernandes
 */
public class Alarm extends ObservableObject {
	private int defCon;
	private int annotatedDefCon; 

	public int getDefCon() {
		return this.defCon;
	}

	public void setDefCon(int defCon) {
		this.setFieldValue("defCon", defCon);
	}

	@Min(value = 1)
	@Max(value = 5)
	public int getAnnotatedDefCon() {
		return this.annotatedDefCon;
	}
	
	public void setAnnotatedDefCon(int annotatedDefCon) {
		this.setFieldValue("annotatedDefCon", annotatedDefCon);
	}

}
