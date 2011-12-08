package org.uqbar.lacar.ui.impl.jface.tree;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

abstract class TreeNode implements ITreeNode {
	protected ITreeNode fParent;
	protected List fChildren;

	public TreeNode(ITreeNode parent) {
		fParent = parent;
	}

	public Image getImage() {
		return null; /* TODO */
	}

	public boolean hasChildren() {
		return true;
	}

	public ITreeNode getParent() {
		return fParent;
	}

	public List getChildren() {
		if (fChildren != null)
			return fChildren;

		fChildren = new ArrayList();
		createChildren(fChildren);

		return fChildren;
	}

	/* subclasses should override this method and add the child nodes */
	protected abstract void createChildren(List children);
}
