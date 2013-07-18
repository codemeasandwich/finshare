package finController;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

public class finUpload_Panel extends JPanel 
{
	private static final long serialVersionUID = 1L;
	private JTable TransferUp_Table = null;	

	public finUpload_Panel()
	{

		JPanel titel2 = new JPanel();
		titel2.setLayout(new BorderLayout());
		titel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		titel2.add(new JLabel("Uploads"), java.awt.BorderLayout.WEST);
		
		//JPanel pan2 = new JPanel();//new JLabel("Uploads")
		this.setLayout(new BorderLayout());
		this.add(titel2, java.awt.BorderLayout.NORTH);
		this.add(getTransferUp_Table(), java.awt.BorderLayout.CENTER);
	}
	private JTable getTransferUp_Table() //Transfer Table
	{
		if (null == TransferUp_Table) 
		{
			TransferUp_Table = new JTable();
		//	String[] headerStr = {"File Name", "Type", "Size","IP","ID"};//item on the table
			
		//	DefaultTableModel Search_TableModel = new DefaultTableModel(headerStr,0);
		}
		return TransferUp_Table;
	}
}
