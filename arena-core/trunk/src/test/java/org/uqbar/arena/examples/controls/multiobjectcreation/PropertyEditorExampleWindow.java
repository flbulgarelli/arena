package org.uqbar.arena.examples.controls.multiobjectcreation;

import java.util.Arrays;
import java.util.List;

import org.uqbar.arena.bindings.ObservableProperty;
import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.Selector;
import org.uqbar.arena.widgets.TextBox;
import org.uqbar.arena.windows.MainWindow;
import org.uqbar.lacar.ui.model.Adapter;

/**
 * 
 * @author jfernandes
 */
public class PropertyEditorExampleWindow extends MainWindow<UbicarCosa>{

	public PropertyEditorExampleWindow() {
		super(new UbicarCosa());
	}

	public void createContents(Panel mainPanel) {
		mainPanel.setLayout(new VerticalLayout());
		
		new Label(mainPanel).setText("Posicion:");
		new TextBox(mainPanel)
			.bindValueToProperty("posicion");
		
		new Selector(mainPanel)
			.setContents(this.getModelObject().getUbicables(), "class.simpleName");
		
		// 	esfera
		// 		estrellas
		Adapter<Ubicable, Boolean> esEsferaAdapter = this.esEsferaAdapter();
		
			//new Label(mainPanel).setText("Estrellas:");
			TextBox estrellas = new TextBox(mainPanel);
			estrellas.bindValueToProperty("ubicable.estrellas");
			estrellas.bindVisible(new ObservableProperty("ubicable")).setAdapter(esEsferaAdapter);
/*			
	// personaje
		// 		distanciaMaxima
		new Label(mainPanel).setText("Distancia Maxima:");
		new TextBox(mainPanel)
			.bindValueToProperty("distanciaMaxima");
			
		*/
		
	}

	protected Adapter<Ubicable, Boolean> esEsferaAdapter() {
		return new Adapter<Ubicable, Boolean>() {
			@Override
			public Boolean modelToView(Ubicable ubicable) {
				return ubicable instanceof Esfera;
			}
			
			@Override
			public Ubicable viewToModel(Boolean valueFromView) {
				return null;
			}

			@Override
			public Class<Ubicable> getModelType() {
				return Ubicable.class;
			}

			@Override
			public Class<Boolean> getViewType() {
				return Boolean.class;
			}
		};
	}

	public static void main(String[] args) {
		new PropertyEditorExampleWindow().startApplication();
	}
	
}