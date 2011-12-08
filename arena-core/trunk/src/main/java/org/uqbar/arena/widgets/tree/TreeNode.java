package org.uqbar.arena.widgets.tree;

import org.uqbar.arena.widgets.Node;
import org.uqbar.arena.widgets.tables.PropertyLabelProvider;
import org.uqbar.arena.widgets.tables.TransformerLabelProvider;
import org.uqbar.lacar.ui.model.ColumnBuilder;
import org.uqbar.lacar.ui.model.LabelProvider;
import org.uqbar.lacar.ui.model.TableBuilder;

import com.uqbar.commons.collections.Closure;
import com.uqbar.commons.collections.Transformer;

public class TreeNode<R> implements Node {

	private Node parent;
	private LabelProvider<R> labelProvider;
	private Object data;

	public TreeNode(Tree parent) {
		this.parent = parent;
		parent.addTreeItem(this);
	}

	public TreeNode(TreeNode parent) {
		this.parent = parent;
		parent.getParent().addTreeItem(this);
	}

	public void addTreeItem(TreeNode treeItem) {
		this.getParent().addTreeItem(treeItem);
	}

	@Override
	public Node getParent() {
		return parent;
	}

	// ********************************************************
	// ** Binding
	// ********************************************************

	public TreeNode<R> bindContentsToProperty(String propertyName) {
		this.labelProvider = new PropertyLabelProvider<R>(propertyName);
		return this;
	}

	public <U> TreeNode<R> bindContentsToTransformer(Transformer<R, U> transformer) {
		this.labelProvider = new TransformerLabelProvider<R, U>(transformer);
		return this;
	}
	
	// ********************************************************
	// ** Rendering
	// ********************************************************

	public void showOn(TableBuilder<R> tree) {
		tree.addColumn(this.labelProvider);
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
