package org.toolup.archi.io.mxgraph;

import java.io.IOException;
import java.io.InputStream;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import com.mxgraph.io.mxCodec;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxUtils;
import com.mxgraph.util.mxXmlUtils;
import com.mxgraph.view.mxGraph;

public class GUIMxGraph  {

	private JTree treeSelection;


	public JTree getTreeSelection() {
		if(treeSelection == null) {
			treeSelection = new JTree(new DefaultTreeModel(new DefaultMutableTreeNode("galerie")));

			treeSelection.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 0));
			ToolTipManager.sharedInstance().registerComponent(treeSelection);
			treeSelection.addTreeSelectionListener(getTreeSelectionListener());
		}
		return treeSelection;
	}

	private TreeSelectionListener getTreeSelectionListener() {
		return (TreeSelectionEvent e) -> {
			Object selectedTreeElem = e.getNewLeadSelectionPath();
			if (selectedTreeElem instanceof TreePath) {
				selectedTreeElem = ((TreePath)selectedTreeElem).getLastPathComponent();
			}else if(selectedTreeElem instanceof DefaultMutableTreeNode){
				selectedTreeElem = ((DefaultMutableTreeNode)selectedTreeElem).getUserObject();
			}

		};
	}

	public static void showWithJGraphX(InputStream is, String name) {
		org.w3c.dom.Document document = null;
		try {
			document = mxXmlUtils.parseXml(mxUtils.readInputStream(is));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		mxCodec codec = new mxCodec(document);
		mxGraph graph = new mxGraph();
		codec.decode(document.getDocumentElement(), graph.getModel());
		showWithJGraphX(graph, name);
	}



	public static void showWithJGraphX(mxGraph graph, String name) {

		SwingUtilities.invokeLater(() -> {
			JFrame debugFrame = new JFrame();
			debugFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			debugFrame.setSize(1200, 800);

			JTabbedPane viewsPane = new JTabbedPane();
			viewsPane.setOpaque(false);
			debugFrame.getContentPane().add(viewsPane);

			((JTabbedPane)debugFrame.getContentPane().getComponent(0)).add(name,new mxGraphComponent(graph));
			((JTabbedPane)debugFrame.getContentPane().getComponent(0)).setSelectedIndex(((JTabbedPane)debugFrame.getContentPane().getComponent(0)).getTabCount() - 1);
			if(! debugFrame.isVisible()) {
				debugFrame.setVisible(true);
			}

		});

	}
}
