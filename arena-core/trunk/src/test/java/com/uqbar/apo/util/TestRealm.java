package com.uqbar.apo.util;

import org.eclipse.core.databinding.observable.Realm;

public class TestRealm extends Realm {
	public static void install() {
		Realm.setDefault(new TestRealm());
	}

	@Override
	public boolean isCurrent() {
		return true;
	}

}
