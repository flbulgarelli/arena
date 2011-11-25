package org.uqbar.lacar.ui.model.adapter;

import org.apache.commons.lang.StringUtils;
import org.uqbar.lacar.ui.model.Adapter;

/**
 * Adapts a String object into a boolean checking if it's not null or empty.
 * 
 * @author jfernandes
 */
public class NotEmptyAdapter implements Adapter<String, Boolean> {
	
	@Override
	public String viewToModel(Boolean valueFromView) {
		// doesn't make sense
		return null;
	}

	@Override
	public Boolean modelToView(String valueFromModel) {
		return StringUtils.isNotBlank(valueFromModel);
	}

	@Override
	public Class<String> getModelType() {
		return String.class;
	}

	@Override
	public Class<Boolean> getViewType() {
		return Boolean.class;
	}

}
