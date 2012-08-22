package org.uqbar.arena.tests.nestedCombos;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.uqbar.commons.utils.Observable;

@Observable
public class NestedCombosDomain {
	private Country country;
	private Province province;
	private List<Country> possibleCountries = new ArrayList<Country>();
	private List<Province> possibleProvinces = new ArrayList<Province>();

	public NestedCombosDomain() {
		this.addCountry("Argentina", "Buenos Aires", "Córdoba", "Santa Fé");
		this.addCountry("Bolivia", "Cochabamba", "Potosí", "La Paz");
	}

	public void addCountry(String name, String... provinces) {
		Country country = new Country(name);
		for (String provinceName : provinces) {
			country.addProvince(provinceName);
		}
		this.possibleCountries.add(country);
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
		this.possibleProvinces = country.getProvinces();
	}

	public Province getProvince() {
		return province;
	}

	public void setProvince(Province province) {
		this.province = province;
	}

	public List<Country> getPossibleCountries() {
		return possibleCountries;
	}

	public void setPossibleCountries(List<Country> possibleCountries) {
		this.possibleCountries = possibleCountries;
	}

	public List<Province> getPossibleProvinces() {
		return possibleProvinces;
	}

	public void setPossibleProvinces(List<Province> possibleProvinces) {
		this.possibleProvinces = possibleProvinces;
	}
}
