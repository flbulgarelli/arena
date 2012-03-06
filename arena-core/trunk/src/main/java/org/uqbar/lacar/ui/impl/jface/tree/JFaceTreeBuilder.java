package org.uqbar.lacar.ui.impl.jface.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections15.map.HashedMap;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Tree;
import org.uqbar.arena.widgets.tree.TreeNodeBuilder;
import org.uqbar.arena.widgets.tree.TreeNode;
import org.uqbar.lacar.ui.impl.jface.JFaceContainer;
import org.uqbar.lacar.ui.impl.jface.JFaceControlBuilder;
import org.uqbar.lacar.ui.impl.jface.lists.SelectionChangeListener;
import org.uqbar.lacar.ui.model.BindingBuilder;
import org.uqbar.lacar.ui.model.LabelProvider;

public class JFaceTreeBuilder<R> extends JFaceControlBuilder<Tree> implements TreeBuilder<R>{

	private Map<org.eclipse.swt.widgets.TreeItem, TreeNode> mappingSWT_Arena;
	private Map<TreeNode, org.eclipse.swt.widgets.TreeItem> mappingArena_SWT;
	private TreeViewer viewer;
	private final org.uqbar.arena.widgets.tree.Tree arenaTree;
	private List<JFaceTreeNodeBuilder<R>> childs;
	private String propertyNode;


	public JFaceTreeBuilder(JFaceContainer container, org.uqbar.arena.widgets.tree.Tree arenaTree, String propertyNode) {
		super(container);
		this.arenaTree = arenaTree;
		this.mappingSWT_Arena = new HashedMap<org.eclipse.swt.widgets.TreeItem, TreeNode>();
		this.mappingArena_SWT = new HashedMap<TreeNode, org.eclipse.swt.widgets.TreeItem>();
		this.propertyNode= propertyNode;
		this.childs = new ArrayList<JFaceTreeNodeBuilder<R>>();
		this.viewer = this.createTree(this.getContainer().getJFaceComposite());
		this.initialize(this.viewer.getTree());	
	}


	private TreeViewer createTree(Composite jFaceComposite) {
		final TreeViewer treeViewer = new TreeViewer(jFaceComposite, SWT.MULTI | SWT.H_SCROLL	| SWT.V_SCROLL);
		treeViewer.setLabelProvider(new ReflectionLabelProvider(propertyNode));
		treeViewer.addSelectionChangedListener(new SelectionChangeListener(arenaTree.getOnClickItem()));
		
		return treeViewer;
	}
	
	// ********************************************************
	// ** Description 
	// ********************************************************

	@Override
	public TreeNodeBuilder<R> addNode(LabelProvider<R> labelProvider) {
		JFaceTreeNodeBuilder<R> child = new JFaceTreeNodeBuilder<R>(this, labelProvider);
		childs.add(child);
		return child;
	};

	@Override
	public BindingBuilder observeContents() {
		return new JFaceTreeContentsBindingBuilder(this);
	}
	

	@Override
	protected Control getControlLayout() {
		return this.viewer.getControl();
	}

	@Override
	public BindingBuilder observeValue() {
		return null;
	}
	
	public TreeViewer getJFaceTreeViewer() {
		return viewer;
	}


	public List<JFaceTreeNodeBuilder<R>> getChilds() {
		return childs;
	}



}
