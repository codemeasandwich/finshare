package finTools;

import finController.*;
import finNetwork.*;
//import javax.swing.JComboBox;
import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
//import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class finSearch 
{
	public void search_Thread(final String inputSearchTextString,final String perfix)
	{
		try
		{
			Thread searchThread = new Thread(new Runnable()
	                {
	                    public void run()
	                    {
	                    	search(inputSearchTextString,perfix);
	                    }});
	        	searchThread.start();
		}
		catch(Exception ex)
		{
			finError.setError("Search Thread");
			finError.add2ErrorLog(ex.toString());
		}
	}
	
	 /*******************************************************************
	 * finShare - Smare Search
	 * 
	 * Steps
	 * 1st. split the inputSearchText into individual words
	 * 2nd. remove any generic words
	 * 3rd. if the search contains alot of words, allow some give in the result
	 * 4th. split the words into char arrays
	 * 5th. loop true all the users
	 * 6th. loop true user's files
	 * 
	 \*******************************************************************/
	
	public void search(String inputSearchTextString,String perfix)
	{
		
		final String[] headerStr = {"File Name", "Type", "Size","IP","ID"};//item on the table
		char[] inputSearchText = inputSearchTextString.toCharArray();
		if('*' == inputSearchText[0] || '*' == inputSearchText[inputSearchText.length -1])//&& ('.' == SearchTextArray[1] || '.' == SearchTextArray[SearchTextArray.length -2]))
		{
			finSound.playSound("Error_Small");
			finInterface.setStatus("Please use the file extension drop down list instead.");
		}
		else
		{
		DefaultTableModel Search_TableModel = new DefaultTableModel(headerStr,0);		//add the names of the items and set the number of rows to zero 
		
		final JTable Search_Table = new JTable(Search_TableModel)//add it to are table
		{
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int rowNumber, int colNumber){return false;}
			/*
			public Class getColumnClass2(int a,int b) 
		    {
		        return getValueAt(a, b).getClass();
		    }
			
			public Class getColumnClass(int a,int b) 
		    {
		        return getValueAt(a, b).getClass();
		    }
	        public Object getValueAt(int row, int col) 
	        {
	            return data[row][col];
	        }*/
	        
		};//desabel editing
		
		//searchTable Search_Table = new Search_Table();
		Search_Table.addMouseListener(//(this);//detech if the user clicks on a row
		new MouseListener()
		{
			public void mouseClicked(MouseEvent cat)
			{
				//
			}
			public void mousePressed(MouseEvent cat)
			{
				//
			}
			public void mouseExited(MouseEvent cat)
			{
				//
			}
			public void mouseReleased(MouseEvent cat)//this ;)
			{
				finSound.playSound("Click");

					finInterface.setDownload_windowjLabel("Search set");
					
					if(Search_Table.getValueAt(Search_Table.getSelectedRow(), 0).equals("No files were found") == false)
					{	//	0		,	1	,	2	,	3 , 4
						//"File Name", "Type", "Size","IP","ID"
						finDownloadSet.setDownload((fileDescript)Search_Table.getValueAt(Search_Table.getSelectedRow(), 1),(String)Search_Table.getValueAt(Search_Table.getSelectedRow(), 3));
						/*((String)Search_Table.getValueAt(Search_Table.getSelectedRow(), 0)+"."+
						(String)Search_Table.getValueAt(Search_Table.getSelectedRow(), 1),
						(String)Search_Table.getValueAt(Search_Table.getSelectedRow(), 4),
						(String)Search_Table.getValueAt(Search_Table.getSelectedRow(), 3));*/
						
						finInterface.selectIPs_ComboBox((String)Search_Table.getValueAt(Search_Table.getSelectedRow(), 3));
					}
					else
					{
						finDownloadSet.nullDownload();
					}

			}
			public void mouseEntered(MouseEvent cat)//on enter the table fired ones
			{
				//
			}
		});
		Search_Table.getTableHeader().setReorderingAllowed(false);//lock the col order
		Search_Table.setSelectionBackground(new java.awt.Color(227,227,0));
		Search_Table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);//selection only one file are a time
		
		TableColumn column = null;
		for (int i = 5; i < 5; i++) //size the cols
		{
		    column = Search_Table.getColumnModel().getColumn(i);
		    if (0 == i)
		        column.setPreferredWidth(500); //make the filename column bigger
		    else if  (i<3)
		    	column.setPreferredWidth(10);
		    else//hide the other cols
		    {
		    	Search_Table.getTableHeader().getColumnModel().getColumn(i).setMaxWidth(0);
		    	Search_Table.getTableHeader().getColumnModel().getColumn(i).setMinWidth(0);
		    	column.setMaxWidth(0);
		    }
		}
		
		//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
		
		JLabel Search_numberofFound = new JLabel("");
		JLabel Search_TimeElapsed =  new JLabel("");

		JPanel txtPane = new JPanel();
		txtPane.setLayout(new BorderLayout());
		txtPane.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		txtPane.add(Search_numberofFound, java.awt.BorderLayout.WEST);
		txtPane.add(Search_TimeElapsed, java.awt.BorderLayout.EAST);
		
		JPanel Pane = new JPanel();
		Pane.setLayout(new BorderLayout());
		
		Pane.add(txtPane, java.awt.BorderLayout.SOUTH);
		Pane.add(new JScrollPane(Search_Table), java.awt.BorderLayout.CENTER);

		finInterface.finSearchTabs.addTab(inputSearchTextString, null,Pane,null);
		finInterface.finSearchTabs.setSelectedIndex(finInterface.finSearchTabs.getComponentCount()-1);
		
		//finInterface.setSearch_TimeElapsed("");//reset timer
		finBenchmark searchBenchmark = new finBenchmark();
		
		boolean allfiles = perfix.equals("All files");
		boolean foundAtAll = false;
		boolean found = false;
		int foundCount = 0;//found all the keywords
		int numberFound = 0;
		//finInterface.setSearch_numberofFound(numberFound);//reset counter
		String word = new String();
		String remove[] = {"the","and","for","to","if","is","in","&","a","i"};
		ArrayList SearchComponents = new ArrayList();
		ArrayList SearchComponentsArray = new ArrayList();
		
		for(int x = 0; x<inputSearchText.length; x++)
		{
			if(inputSearchText[x] != ' '&& inputSearchText[x] != '.')
			{
				word += inputSearchText[x];
			} 
			else
			{
				SearchComponents.add(word);
				word = "";
			}
		}
		SearchComponents.add(word);//catch the last word
		
		for(int x = 0; x<remove.length; x++)//cleanup seach words i.e. keep only the uniqe\Key words
		{
			for(int x2 = 0; x2<SearchComponents.size(); x2++)
			{
				if(SearchComponents.get(x2).toString().equals(remove[x]))
				{
					SearchComponents.remove(x2);
				}
			}
		}
		if(SearchComponents.size()>=6)// alittle bit of give
		{
			foundCount = SearchComponents.size() - 2;
		}
		else
		{
			foundCount = SearchComponents.size();
		}
		
		for(int x = 0; x<SearchComponents.size(); x++)//components to an array
		{
			SearchComponentsArray.add(SearchComponents.get(x).toString().toCharArray());
		}

		//set the total progress bar to the number of users connected
		Tab_finSearch.setTotalSearch_ProgressBar(finNetList.netList.size());
		
		ArrayList aList = null;
		fileDescript tempFileDes = null;
		for(int i = 0; i<finNetList.netList.size(); i++)
		{
			aList = (ArrayList)finNetList.netList.get(i);						//get user on the network
			Tab_finSearch.setUniteSearch_ProgressBar(aList.size());	//set Unit 
			
			tempFileDes = (fileDescript)aList.get(0);
			
			String targetIP = tempFileDes.getID();

			int sizeof1present = 0;
			if(aList.size()>100)
			{
				sizeof1present = (int)aList.size()/100;
			}
		
			int sizeof1present_counter = 0;
			
			for(int ii = 1; ii<aList.size(); ii++)// start at 1 because 0 is the root for that user
			{
				if(sizeof1present_counter >= sizeof1present)
				{
					Tab_finSearch.addUniteSearch_ProgressBar(sizeof1present);
					sizeof1present_counter = 0;
				}
				sizeof1present_counter++;
				
				tempFileDes = (fileDescript)aList.get(ii);//get the file from the list
				if((true == allfiles || tempFileDes.getType().equalsIgnoreCase(perfix))&&(tempFileDes.getType().equals("#") == false && tempFileDes.getType().equals("##") == false ))//do we want to scan this file??
				{
					char fileStringArray[] = tempFileDes.getFilename().toLowerCase().toCharArray();//file name to char array

					for(int SearchComponentsIndex = 0;SearchComponentsIndex<SearchComponents.size();SearchComponentsIndex++)
					{
						char SearchComponent1[] = (char[])SearchComponentsArray.get(SearchComponentsIndex);
						int count = 0;
						while(count<fileStringArray.length && false == found)
						{
							if(SearchComponent1[0] == fileStringArray[count])//does the first lettle of the searchComponent mech a lettlein the file name
							{
								int tempCounter = count;
								int SearchComponent1counter = 0;
									
								while(false == found && count<fileStringArray.length && SearchComponent1counter<SearchComponent1.length && SearchComponent1[SearchComponent1counter] == fileStringArray[count])
								{
									if(SearchComponent1.length -1 == SearchComponent1counter)
									{
										foundCount--;
										found = true;
									}
									else
									{
										SearchComponent1counter++;
										count++;
									}
								}
								found = false;
								count = tempCounter;//reset counter
							}
							count++;
						}
					} 
					if(foundCount <= 0)
					{
						foundAtAll = true;
						Search_TableModel.addRow(new Object[]{tempFileDes.getFilename(),tempFileDes.getType(),tempFileDes.getSize(),targetIP,/*new JComboBox()});*/tempFileDes});//tempFileDes.getID()});
						numberFound++;
						Search_numberofFound.setText("File found: "+numberFound);
					}
					foundCount = SearchComponents.size();
					
				}//end of if file type
				
			}//end of Inner loop - aList
			Tab_finSearch.addTotalSearch_ProgressBar();//update after the search is don, not before!!
		}//end of outter loop - netList
		
		Tab_finSearch.setTotalSearch_ProgressBar(100);//reset the bars
		Tab_finSearch.setUniteSearch_ProgressBar(100);
		
		if(false == foundAtAll)
		{
			Search_TableModel.addRow(new Object[]{"No files were found","","","",""});
		}
		Search_TimeElapsed.setText("("+searchBenchmark.elapsed()+" seconds)");

		//finInterface.SearchButtons(false);
		finInterface.setStatus("Search compleate");
	}
	}
}/*
class searchTable extends AbstractTableModel
{
	private static final long serialVersionUID = 1L;

	//private String[] columnNames = ...//same as before...
    private Object[][] data;// = ...//same as before...

	final String[] columnNames = {"File Name", "Type", "Size","IP","ID"};//item on the table
	
	
    public int getColumnCount() 
    {
        return columnNames.length;
    }

    public int getRowCount() 
    {
        return data.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    public Class getColumnClass(int c) 
    {
        return getValueAt(0, c).getClass();
    }


    public boolean isCellEditable(int row, int col)
    { return false; }


    public void setValueAt(Object value, int row, int col)
    {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }
}*/
