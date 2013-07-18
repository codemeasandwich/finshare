package finController;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

import finTools.finSearch;
import finTools.finSetup;
import finTools.finSound;

public class Tab_finSearch extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private static JButton SearchCancel_jButton = null;
	private static JButton Search_jButton = null;
	private JTextField Search_TextField = null;
	private static JComboBox filter_ComboBox = null;
	
	private static JProgressBar uniteSearch_ProgressBar = new JProgressBar();
	private static JProgressBar totalSearch_ProgressBar = new JProgressBar();
	private static int uniteSearch_ProgressNumber = 0;
	private static int TotalSearch_ProgressNumber = 0;
	
	public Tab_finSearch()
	{
		this.setLayout(new GridBagLayout());
		this.setBorder(javax.swing.BorderFactory.createEmptyBorder(5,5,5,5));
		
		GridBagConstraints gridBagPanelSearch = new GridBagConstraints();

		gridBagPanelSearch.gridx = 0;
		gridBagPanelSearch.gridy = 5;
		this.add(new JLabel(" "), gridBagPanelSearch);

		gridBagPanelSearch.gridx = 1;
		gridBagPanelSearch.gridwidth = 1;
		gridBagPanelSearch.gridy = 8;
		this.add(getSearchCancel_jButton(), gridBagPanelSearch);

		gridBagPanelSearch.gridx = 0;
		gridBagPanelSearch.gridwidth = 3;
		gridBagPanelSearch.gridy = 3;
		this.add(new JLabel("Total Network Scan"), gridBagPanelSearch);

		gridBagPanelSearch.gridx = 0;
		gridBagPanelSearch.gridwidth = 3;
		gridBagPanelSearch.gridy = 0;
		this.add(new JLabel("Unite Scan"), gridBagPanelSearch);

		gridBagPanelSearch.gridx = 0;
		gridBagPanelSearch.gridwidth = 4;
		gridBagPanelSearch.gridy = 4;
		this.add(totalSearch_ProgressBar, gridBagPanelSearch);

		gridBagPanelSearch.gridx = 0;
		gridBagPanelSearch.gridwidth = 4;
		gridBagPanelSearch.gridy = 1;
		this.add(getUniteSearch_ProgressBar(), gridBagPanelSearch);

		gridBagPanelSearch.gridx = 0;
		gridBagPanelSearch.gridwidth = 1;
		gridBagPanelSearch.gridy = 8;
		this.add(getSearch_jButton(), gridBagPanelSearch);

		gridBagPanelSearch.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagPanelSearch.gridy = 7;
		gridBagPanelSearch.weightx = 1.0;
		gridBagPanelSearch.gridwidth = 3;
		gridBagPanelSearch.gridx = 0;
		this.add(getSearch_TextField(), gridBagPanelSearch);

		gridBagPanelSearch.fill = java.awt.GridBagConstraints.NONE;
		gridBagPanelSearch.anchor = java.awt.GridBagConstraints.EAST;
		gridBagPanelSearch.gridy = 6;
		gridBagPanelSearch.weightx = 0;
		gridBagPanelSearch.gridx = 2;
		this.add(getFilter_ComboBox(), gridBagPanelSearch);
		
		gridBagPanelSearch.gridx = 2;
		gridBagPanelSearch.gridy = 5;
		this.add(new JLabel("File extension"), gridBagPanelSearch);
	}
	private JButton getSearch_jButton() 
	{
		if (null == Search_jButton) 
		{
			Search_jButton = new JButton("Search");
			Search_jButton.setToolTipText("Search for a file!");
			Search_jButton.addActionListener(this);
		}
		return Search_jButton;
	}

	private JTextField getSearch_TextField() 
	{
		if (null == Search_TextField) 
		{
			Search_TextField = new JTextField();
			Search_TextField.addActionListener(this);
		}
		return Search_TextField;
	}

	private JComboBox getFilter_ComboBox() 
	{
		if (null == filter_ComboBox) 
		{
			filter_ComboBox = new JComboBox();
			filter_ComboBox.setToolTipText("Select the type of file to search for.");
			fillFilter_ComboBox();
		}
		return filter_ComboBox;
	}
	private JButton getSearchCancel_jButton() 
	{
		if (null == SearchCancel_jButton) 
		{
			SearchCancel_jButton = new JButton("Cancel");
			SearchCancel_jButton.setEnabled(false);
			SearchCancel_jButton.addActionListener(this);
			SearchCancel_jButton.setVisible(false);
		}
		return SearchCancel_jButton;
	}
	public static void updateFilter_ComboBox()
	{
		filter_ComboBox.removeAllItems();
		fillFilter_ComboBox();
	}
	public static void fillFilter_ComboBox()
	{
		filter_ComboBox.addItem("All files");
		ArrayList templist = finSetup.getSearchFilter();
		
		for(int i =0;i<templist.size();i++)
		{
			filter_ComboBox.addItem(templist.get(i).toString());
		}
	}
	private JProgressBar getUniteSearch_ProgressBar() 
	{
		return uniteSearch_ProgressBar;
	}
	
	public static void addUniteSearch_ProgressBar(int valu)
	{
		uniteSearch_ProgressNumber += valu;
		uniteSearch_ProgressBar.setValue(uniteSearch_ProgressNumber);
		uniteSearch_ProgressBar.paintImmediately(new Rectangle(uniteSearch_ProgressBar.getBounds())); 
	}
	
	public static void addTotalSearch_ProgressBar()
	{
		TotalSearch_ProgressNumber++;
	    totalSearch_ProgressBar.setValue(TotalSearch_ProgressNumber);
		totalSearch_ProgressBar.paintImmediately(new Rectangle(totalSearch_ProgressBar.getBounds())); 
	}
	
	public static void setUniteSearch_ProgressBar(int max)
	{
		uniteSearch_ProgressNumber = 0;
		uniteSearch_ProgressBar.setMaximum(max);
		uniteSearch_ProgressBar.setValue(0);
	}
	
	public static void setTotalSearch_ProgressBar(int max)
	{
		TotalSearch_ProgressNumber = 0;
		totalSearch_ProgressBar.setMaximum(max);
		totalSearch_ProgressBar.setValue(0);
	}
	public static void addFilter2ComboBox(String val)
	{
		filter_ComboBox.addItem(val);
	}
//	-----------------------------------------------------------------------------------

	public void actionPerformed(ActionEvent e) 
	{
		finSound.playSound("Click");
		
		if (e.getSource() == SearchCancel_jButton)
		{
			setUniteSearch_ProgressBar(100);
			setTotalSearch_ProgressBar(100);
			finInterface.setStatus("Search Canceled by user!");
		} 
		else if (e.getSource() == Search_jButton || e.getSource() == Search_TextField)
		{
			if(Search_TextField.getText() != null && Search_TextField.getText().equals("") == false)
			{
					finInterface.setStatus("Search Started ");
					Search_TextField.selectAll(); 
					finSearch search = new finSearch();
					search.search_Thread(Search_TextField.getText(),filter_ComboBox.getSelectedItem().toString());
			}
			else 
			{
				finSound.playSound("Error_Small");
				finInterface.setStatus("you must enter the name of the file you are looking for.");
			}
		}
	}
}
