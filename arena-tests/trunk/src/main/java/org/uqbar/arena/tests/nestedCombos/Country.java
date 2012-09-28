package org.uqbar.arena.tests.nestedCombos;

import java.util.ArrayList;
import java.util.List;

import org.uqbar.commons.utils.Observable;


@Observable
public class Country {
	private String name;
	private List<Province> provinces = new ArrayList<Province>();

	public List<Province> getProvinces() {
		return provinces;
	}

	public void setProvinces(List<Province> provinces) {
		this.provinces = provinces;
	}

	public Country(String name) {
		this.name = name;
	}

	public void addProvince(String provinceName) {
		this.provinces.add(new Province(provinceName));
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
