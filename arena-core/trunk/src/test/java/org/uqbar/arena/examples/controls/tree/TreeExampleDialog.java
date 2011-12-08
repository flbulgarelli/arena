package org.uqbar.arena.examples.controls.tree;

import org.uqbar.arena.Application;
import org.uqbar.arena.actions.MessageSend;
import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.tree.Tree;
import org.uqbar.arena.widgets.tree.TreeNode;
import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.Window;
import org.uqbar.arena.windows.WindowOwner;
import org.uqbar.commons.model.ObservableObject;

public class TreeExampleDialog extends Dialog<ObservableObject> {
	

	private static final long serialVersionUID = 1L;


	public TreeExampleDialog(WindowOwner owner) {
		super(owner, new ObservableObject());
	}

	@Override
	protected void executeTask() {

	}

	@Override
	protected void createFormPanel(Panel mainPanel) {
//		mainPanel.setLayout(new VerticalLayout());
//		Tree tree = new Tree(mainPanel).onClickItem(new MessageSend(this, "click"));
//		for (int i=0; i<4; i++) {
//			TreeNode iItem = new TreeNode (tree);
//			iItem.setText ("TreeItem (0) -" + i);
//			for (int j=0; j<4; j++) {
//				TreeNode jItem = new TreeNode (iItem);
//				jItem.setText ("TreeItem (1) -" + j);
//			}
//		}
	}
	
	public void click(TreeNode treeItem){
//		System.out.println(treeItem.getText());
	}
	
	static class App extends Application  {
		
		@Override
		protected Window<?> createMainWindow() {
			return new TreeExampleDialog(this);
		}

	}
	
	public static void main(String[] args) {
		new App().start();
	}
}
