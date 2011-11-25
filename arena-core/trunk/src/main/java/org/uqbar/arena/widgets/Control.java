package org.uqbar.arena.widgets;

import java.util.Collection;
import java.util.List;

import org.uqbar.arena.bindings.ObservableProperty;
import org.uqbar.arena.bindings.ObservableValue;
import org.uqbar.lacar.ui.model.BindingBuilder;
import org.uqbar.lacar.ui.model.ControlBuilder;
import org.uqbar.lacar.ui.model.PanelBuilder;
import org.uqbar.lacar.ui.model.bindings.Binding;
import org.uqbar.lacar.ui.model.bindings.Observable;
import org.uqbar.lacar.ui.model.bindings.ViewObservable;

import com.uqbar.commons.collections.Closure;
import com.uqbar.commons.collections.CollectionFactory;

/**
 * Un {@link Widget} que permite la edición de un único valor específico. Superclase para todos los controles
 * más comúnes: cajas de texto, selectores, etc.
 * 
 * @author npasserini
 */
public abstract class Control extends Widget {
	private Collection<Binding<ControlBuilder>> bindings = CollectionFactory.createCollection();
	protected List<Closure<ControlBuilder>> configurations = CollectionFactory.createList();

	public Control() {
		this(null);
		
	}
	public Control(Container container) {
		super(container);
	}

	// ********************************************************
	// ** Configurations
	// ********************************************************

	// Para evitar este SupressWarnings habría que poner un self-bound generic type parameter a todos los
	// widgets y prefiero no hacerlo.
	@SuppressWarnings("unchecked")
	public Control addConfiguration(Closure<? extends ControlBuilder> configuration) {
		this.configurations.add((Closure<ControlBuilder>) configuration);
		return this;
	}

	// ********************************************************
	// ** Binding
	// ********************************************************

	/**
	 * Vincula el valor de este control con una propiedad del modelo del contenedor.
	 * 
	 * @param modelProperty Una propiedad del modelo del contenedor.
	 * @return this
	 */
	public <C extends ControlBuilder> Binding<C> bindValueToProperty(String modelProperty) {
		return this.bindValue(new ObservableProperty(modelProperty));
	}

	/**
	 * Vincula el valor de este control con una característica del modelo del contenedor.
	 * 
	 * @param modelObservable Una característica observable relativa al modelo del contenedor.
	 * @return this
	 */
	public <C extends ControlBuilder> Binding<C> bindValue(ObservableProperty modelObservable) {
		return this.addBinding(modelObservable, new ObservableValue<C>());
	}

	public <C extends ControlBuilder> Binding<C> bindEnabled(Observable modelObservable) {
		return this.addBinding(modelObservable, new ViewObservable<C>() {
			@Override
			public BindingBuilder createBinding(C builder) {
				return builder.observeEnabled();
			}
		});
	}
	
	/**
	 * Shortcut method for bindEnabled(new ObservableProperty(propertyName))
	 * It binds the enabled feature of this control to the value of the model's property given by parameter.
	 */
	public <C extends ControlBuilder> Binding<C> bindEnabledToProperty(String propertyName) {
		return this.bindEnabled(new ObservableProperty(propertyName));
	}
	
	public <C extends ControlBuilder> Binding<C> bindVisible(Observable modelObservable) {
		return this.addBinding(modelObservable, new ViewObservable<C>() {
			@Override
			public BindingBuilder createBinding(C builder) {
				return builder.observeVisible();
			}
		});
	}

	/**
	 * Agrega un binding entre dos observables, validándolo en este contexto.
	 * 
	 * @param model Una característica observable del modelo asociado a este control.
	 * @param view Una característica observable de este control.
	 * @return Devuelve this como una comodidad para enviar mensajes anidados.
	 */
	@SuppressWarnings("unchecked")
	protected <C extends ControlBuilder> Binding<C> addBinding(Observable model, ViewObservable<C> view) {
		model.setContainer(this.getContainer());

		Binding<C> binding = new Binding<C>(model, view);
		this.bindings.add((Binding<ControlBuilder>) binding);
		return binding;
	}

	// ********************************************************
	// ** Rendering
	// ********************************************************

	@Override
	public void showOn(PanelBuilder container) {
		ControlBuilder builder = this.createBuilder(container);

		for (Closure<ControlBuilder> configuration : this.configurations) {
			configuration.execute(builder);
		}
		
		configure(builder);

		// ATENCION: Las tablas necesitan que el pack se asigne antes que los bindings, esto es porque el
		// binding necesita tener asignado un LabelProvider, que en la implementación actual se está asignando
		// en el pack.
		builder.pack();

		for (Binding<ControlBuilder> binding : this.bindings) {
			binding.execute(builder);
		}
	}

	public void configure(ControlBuilder builder){
		
	}
	
	protected abstract ControlBuilder createBuilder(PanelBuilder container);
}