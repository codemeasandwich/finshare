package finController;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class finDownload_Panel extends JPanel
{

	private static final long serialVersionUID = 1L;
	private JTable TransferDown_Table = null;
	private static final String[] headerStr = {"File Name","User","Status","Progress","Down/Total","Time Remaining"};//item on the table
	//private DefaultTableModel Download_TableModel = new DefaultTableModel(headerStr,1);		//add the names of the items and set the number of rows to zero 
	public static DefaultTableModel Download_TableModel = new DefaultTableModel(headerStr,0);
	
	public finDownload_Panel()
	{
		JPanel titel1 = new JPanel();
		titel1.setLayout(new BorderLayout());
		titel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		titel1.add(new JLabel("Downloads"), java.awt.BorderLayout.WEST);
		
		//JPanel pan1 = new JPanel();
		this.setLayout(new BorderLayout());
		this.add(titel1, java.awt.BorderLayout.NORTH);
		this.add(new JScrollPane(getTransferDown_Table()), java.awt.BorderLayout.CENTER);
		//addTransfer();
	}
	
	private JTable getTransferDown_Table() //Transfer Table
	{
		if (null == TransferDown_Table) 
		{
			//String[] headerStr = {"IP","File Name", "Type", "Total Size","Total sent","Progress bar"};//item on the table
			
			//DefaultTableModel Download_TableModel = new DefaultTableModel(headerStr,1);		//add the names of the items and set the number of rows to zero 
			
			TransferDown_Table = new JTable(Download_TableModel)
			{
				private static final long serialVersionUID = 1L;
				public boolean isCellEditable(int rowNumber, int colNumber)
			{
				return false;
			}};//desabel editing
			
			TransferDown_Table.setSelectionBackground(new java.awt.Color(220,220,0));
		}
		return TransferDown_Table;
	}
	//public
	//public void addTransfer()
	//{
		//Download_TableModel.addRow(new Object[]{"","","","","",""});
	//}
}
