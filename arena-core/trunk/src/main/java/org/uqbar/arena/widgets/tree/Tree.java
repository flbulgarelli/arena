package org.uqbar.arena.widgets.tree;

import java.util.ArrayList;
import java.util.List;

import org.uqbar.arena.bindings.ObservableProperty;
import org.uqbar.arena.widgets.Container;
import org.uqbar.arena.widgets.Control;
import org.uqbar.arena.widgets.Node;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.lists.ObservableListContents;
import org.uqbar.arena.widgets.tables.Table;
import org.uqbar.lacar.ui.model.Action;
import org.uqbar.lacar.ui.model.ControlBuilder;
import org.uqbar.lacar.ui.model.PanelBuilder;
import org.uqbar.lacar.ui.model.bindings.Observable;

public class Tree<T>  extends Control implements Node{
	private static final long serialVersionUID = 1L;
	private Action onClickItem;
	private Action onExpand;
	
	public Action getOnClickItem() {
		return onClickItem;
	}
	
	public Action getOnExpand() {
		return onExpand;
	}

	/**
	 * Los componentes contenidos en este {@link Panel}
	 */
	private List<TreeNode> children = new ArrayList<TreeNode>();
	private String propertyNode;
	
	
	public Tree(Container container) {
		super(container);
	}
	
	public void addTreeItem(TreeNode treeItem){
		this.children.add(treeItem);
	}
	
	
	/**
	 * Asigna el contenido de esta Arbol.
	 * 
	 * @param model Una característica observable del modelo.
	 * 
	 * @return Esta misma tabla, para enviar mensajes anidados
	 */
	public Tree<T> bindContents(Observable model) {
		this.addBinding(model, new ObservableTreeContents());
		return this;
	}
	
	public Tree<T> bindContentsToProperty(String parentPropertyName, String childrenPropertyName) {
		return this.bindContents(new ObservableTwoProperty(parentPropertyName, childrenPropertyName));
	}
	
	public Tree<T> bindNodeToProperty(String propertyName){
		this.propertyNode = propertyName;
		return this;
	}

	@Override
	protected ControlBuilder createBuilder(PanelBuilder container) {
		return container.addTree(this, propertyNode, getWidth(), getHeigth());
	}
	
	
	public Tree onClickItem(Action onClickItem) {
		this.onClickItem = onClickItem;
		return this;
	}
	
	public Tree onExpand(Action onExpand) {
		this.onExpand = onExpand;
		return this;
	}


	public List<TreeNode> getChildren() {
		return children;
	}

	@Override
	public Node getParent() {
		return null;
	}

}
