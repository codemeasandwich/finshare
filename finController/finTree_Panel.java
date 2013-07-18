package finController;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.JTree;

import finGUI.finTree;
import finTools.finFileIndex;

public class finTree_Panel extends JPanel
{
	private static final long serialVersionUID = 1L;
	private JScrollPane ScrollTree;
	private JPanel fileTree_status_panel;
	private static JLabel fileTree_type;
	private static JLabel fileTree_count;
	private static JTree Tree_jTree = null;
	//private static JLabel fileTree_type = null;
	
	public finTree_Panel()
	{
		fileTree_count = new JLabel();			//desplay the number of files and dirs in the file tree
		fileTree_type = new JLabel();			//desplay info on the selecet file on the tree
		finTree oneTree = new finTree();
		Tree_jTree = oneTree.getJTree_Array(finFileIndex.getArrayList());
		ScrollTree = new JScrollPane();
		//ScrollTree.setOpaque(false);
		this.setLayout(new BorderLayout());
		this.add(new JLabel("File Tree"), java.awt.BorderLayout.NORTH);
		this.add(getFileTree_status(), java.awt.BorderLayout.SOUTH);//get status for the file tree
		ScrollTree.setViewportView(Tree_jTree);
		this.add(ScrollTree, java.awt.BorderLayout.CENTER);//get the scrollpane wich cantanes the file tree
	}
	
	//public void removeTree()
	//{
		//ScrollTree.remove(Tree_jTree);
		//ScrollTree.removeAll();
		//ScrollTree = null;
	//}
	
	public void updateTree(JTree newTree)
	{
		//System.out.println("updateTree");
		Tree_jTree = newTree;
		ScrollTree.setViewportView(Tree_jTree);
		//ScrollTree.repaint();
	}
	
	private JPanel getFileTree_status()
	{
		if(null == fileTree_status_panel)
		{
			fileTree_status_panel = new JPanel();
			fileTree_status_panel.setLayout(new BorderLayout());
			fileTree_status_panel.setBorder(new BevelBorder(BevelBorder.LOWERED));
			fileTree_status_panel.add(fileTree_count, java.awt.BorderLayout.WEST);
			fileTree_status_panel.add(fileTree_type, java.awt.BorderLayout.EAST);
		}
		return fileTree_status_panel;
	}
	public static void setFileTree_count(String massage)//despaly the number of file and dirs in the file tree
	{
			fileTree_count.setText(" "+massage);
	}
	public static void setFileTree_type(String type)//set the file type decripshion
	{
			fileTree_type.setText(type + " ");
	}
	public void updateNodeRenderer()
	{
		Tree_jTree.setCellRenderer(finTree.getJTree_CellRenderer());
	}
	/*
	public static void setCellRenderer(finTree_Pane inputRenderer)
	{
		Tree_jTree.setCellRenderer(inputRenderer);//refresh the file tree
	}
	*/
}
