package org.uqbar.arena.widgets.tree;

import org.uqbar.arena.widgets.Container;
import org.uqbar.commons.model.ObservableObject;
import org.uqbar.lacar.ui.impl.jface.tree.JFaceTreeContentsBindingBuilder;
import org.uqbar.lacar.ui.model.BindingBuilder;
import org.uqbar.lacar.ui.model.bindings.Observable;

public class ObservableTwoProperty implements Observable{
	
	
		private final String propertyName1;
		private final String propertyName2;
		private ObservableObject model;

		public ObservableTwoProperty(String propertyName1, String propertyName2) {
			this.propertyName1 = propertyName1;
			this.propertyName2 = propertyName2;
		}

		public ObservableTwoProperty(ObservableObject model, String propertyName1, String propertyName2) {
			this(propertyName1, propertyName2);
			this.model = model;
		}

		// ********************************************************
		// ** Configuration
		// ********************************************************

		@Override
		public void setContainer(Container container) {
			if (this.model == null) {
				this.setModel(container.getModel());
			}
		}

		public ObservableTwoProperty setModel(ObservableObject model) {
			model.getGetter(this.propertyName1);
			model.getGetter(this.propertyName2);

			this.model = model;
			return this;
		}

		// ********************************************************
		// ** Building
		// ********************************************************

		@Override
		public void configure(BindingBuilder binder) {
			((JFaceTreeContentsBindingBuilder)binder).observeProperty(this.model, this.propertyName1, this.propertyName2);
		}
		
	}
