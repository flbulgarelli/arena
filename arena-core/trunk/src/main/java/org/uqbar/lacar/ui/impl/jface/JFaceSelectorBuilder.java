package org.uqbar.lacar.ui.impl.jface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.widgets.Combo;
import org.uqbar.commons.model.ObservableObject;
import org.uqbar.lacar.ui.impl.jface.actions.JFaceActionAdapter;
import org.uqbar.lacar.ui.impl.jface.bindings.JFaceBindingBuilder;
import org.uqbar.lacar.ui.model.Action;
import org.uqbar.lacar.ui.model.Adapter;
import org.uqbar.lacar.ui.model.BindingBuilder;
import org.uqbar.lacar.ui.model.ButtonBuilder;
import org.uqbar.lacar.ui.model.SelectorBuilder;

import com.uqbar.commons.exceptions.ProgramException;

/**
 * @param <T> El tipo de objetos que contendrá el combo.
 */
public class JFaceSelectorBuilder extends JFaceControlBuilder<Combo> implements SelectorBuilder, Adapter<Object, String> {
	private Map<String, Object> map;
	private final String descriptionProperty;

	public JFaceSelectorBuilder(Combo jFaceWidget, JFaceContainer context, List options, String descriptionProperty, boolean nullValue) {
		super(context, jFaceWidget);
		this.descriptionProperty = descriptionProperty;

		this.map = new HashMap<String, Object>();
		String[] itemValues = new String[options.size() + (nullValue ? 1 : 0)];
		int i = 0;

		if (nullValue) {
			this.map.put("", null);
			itemValues[i++] = "";
		}

		for (Object option : options) {
			String optionValueString = this.asString(option);
			this.map.put(optionValueString, option);
			itemValues[i++] = optionValueString;
		}

		this.getWidget().setItems(itemValues);
	}

	@Override
	public BindingBuilder observeValue() {
		return new JFaceBindingBuilder(this, SWTObservables.observeSelection(this.getWidget())).setAdapter(this);
	}
	
	@Override
	public JFaceSelectorBuilder onSelection(Action action) {
		this.getWidget().addSelectionListener(new JFaceActionAdapter(this.getContainer(), action));
		return this;
	}
	
	// ********************************************************
	// ** Adapter Interface
	// ********************************************************

	@Override
	public Object viewToModel(String valueFromView) {
		return this.map.get(valueFromView);
	}

	@Override
	public String modelToView(Object valueFromModel) {
		return this.asString(valueFromModel);
	}

	private String asString(Object valueFromModel) {
		if (valueFromModel == null) {
			return "";
		}
		return this.descriptionProperty == null ? "" : this.getProperty(this.descriptionProperty, valueFromModel);
	}
	
	protected String getProperty(String propertyName, Object object) {
		Object value;
		if (object instanceof ObservableObject) {
			value = ((ObservableObject) object).getProperty(propertyName);
		}
		else {
			value = ObservableObject.getPropertyValue(object, propertyName);
		}
		return value == null ? "" : value.toString();
	}

	@Override
	public Class<Object> getModelType() {
		return Object.class;
	}

	@Override
	public Class<String> getViewType() {
		return String.class;
	}
}
