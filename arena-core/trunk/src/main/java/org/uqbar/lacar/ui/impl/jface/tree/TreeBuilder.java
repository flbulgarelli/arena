package org.uqbar.lacar.ui.impl.jface.tree;

import org.uqbar.arena.widgets.tree.TreeNodeBuilder;
import org.uqbar.lacar.ui.model.BindingBuilder;
import org.uqbar.lacar.ui.model.ControlBuilder;
import org.uqbar.lacar.ui.model.LabelProvider;

public interface TreeBuilder<R> extends ControlBuilder {
	
	public TreeNodeBuilder<R> addNode(LabelProvider<R> labelProvider);

	public abstract BindingBuilder observeContents();

}
