package org.uqbar.arena.tests.nestedCombos;

import org.uqbar.commons.utils.Observable;

@Observable 
public class Province {
	private String name;

	public Province(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
