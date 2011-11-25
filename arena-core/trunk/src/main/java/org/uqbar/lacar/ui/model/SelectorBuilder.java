package org.uqbar.lacar.ui.model;

import org.uqbar.lacar.ui.impl.jface.JFaceSelectorBuilder;

public interface SelectorBuilder extends ControlBuilder {

	JFaceSelectorBuilder onSelection(Action action);

}
