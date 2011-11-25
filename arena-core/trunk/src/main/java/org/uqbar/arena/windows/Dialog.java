package org.uqbar.arena.windows;

import java.util.List;

import com.uqbar.commons.collections.CollectionFactory;

import org.uqbar.arena.widgets.Panel;
import org.uqbar.commons.model.ObservableObject;
import org.uqbar.commons.model.UserException;
import org.uqbar.lacar.ui.model.Action;

/**
 * Ventana (SimpleWindow) especial que implementa la interface {@link TaskWindow}.
 * 
 * Se utiliza para modelar una interaccion particular donde la ventana sirve
 * para realizar una "accion", y dicha accion se puede "aceptar" o "cancelar".
 * 
 * Esta ventana entonces ya implementa logica para el accept() y el cancel()
 * por ejemplo para cerrar el dialogo. 
 * 
 * Sin embargo no se encarga de construir nada. Es decir que no construye ningun
 * elemento.
 * Las subclases se deben encargar de eso.
 * 
 * @see TaskWindow
 * 
 * @author npasserini
 */
public abstract class Dialog<T extends ObservableObject> extends SimpleWindow<T> implements TaskWindow {
	public static final String ACCEPT = "accept";
	public static final String CANCEL = "cancel";
	private List<Action> acceptActions = CollectionFactory.createList();
	private List<Action> cancelActions = CollectionFactory.createList();

	public Dialog(WindowOwner owner, T model) {
		super(owner, model);
	}

	// ********************************************************
	// ** Eventos
	// ********************************************************

	@Override
	public void onAccept(Action action) {
		this.acceptActions.add(action);
	}

	@Override
	public void onCancel(Action action) {
		this.cancelActions.add(action);
	}

	// ********************************************************
	// ** Acciones de la tarea
	// ********************************************************

	/**
	 * La implementación por defecto invoca primero a {@link #executeTask()}, notifica los listeners del
	 * evento {@link #onAccept(Action)} y luego cierra la ventana.
	 */
	@Override
	public void accept() {
		this.executeTask();

		for (Action action : this.acceptActions) {
			action.execute();
		}

		this.close();
	}

	/**
	 * La implementación por defecto invoca primero a {@link #cancelTask()} y luego cierra la ventana.
	 */
	@Override
	public void cancel() {
		this.cancelTask();

		for (Action action : this.cancelActions) {
			action.execute();
		}

		this.close();
	}

	/**
	 * Hook para ejecutar la tarea asociada a la ventana. Este método no debería tener lógica de negocio, sino
	 * delegar en el modelo.
	 * 
	 * @throws UserException En caso que la ejecución no sea exitosa. Eso evitará que se cierre la ventana.
	 */
	protected abstract void executeTask();

	/**
	 * Hook para ejecutar la cancelación de la tarea asociada a la ventana. Por defecto no hace nada.
	 * 
	 * @throws UserException En caso que no se pueda o no se desee cancelar la tarea. Eso evitará que se
	 *             cierre la ventana.
	 */
	protected void cancelTask() {
	}
	
	@Override
	protected void addActions(Panel actionsPanel) {
	}

}