package org.uqbar.arena.tests.nestedPropertyAccess;

import org.uqbar.commons.utils.TransactionalAndObservable;

@TransactionalAndObservable
public class NestedPropertyAccessDomain {

	private Country country;

	// ****************************************************************
	// ** CONSTRUCTORS
	// ****************************************************************

	public NestedPropertyAccessDomain(Country country) {
		this.setCountry(country);
	}

	// ****************************************************************
	// ** ACCESSORS
	// ****************************************************************

	public Country getCountry() {
		return this.country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}
}