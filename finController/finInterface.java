package finController;

import finGUI.*;
//import finGUI.finTree;
//import finGUI.fin_Menubar;
import finMain.*;
import finTools.*;
import finNetwork.*;

import java.awt.Frame;
import java.awt.Insets;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;

public class finInterface implements ActionListener
{
	private static boolean SingleInstance = false;
	private static boolean IPs_ComboBoxFirst = false;
	
	private static JFrame main_Frame = null;
	
	private static JPanel main_ContentPane = null;
	private JPanel Tab_Panel = null;
	private JPanel Panel_Network = null;
	private JPanel Panel_Download = null;
	
	private JSplitPane main_SplitPane = null;
	private JSplitPane SplitPane_TreeTabs = null;
	private JSplitPane jSplitPane_SearchTransfer = null;
	
	private JTabbedPane mainTab_jTabbedPane = null;
	public static JTabbedPane finSearchTabs = null;
	//private JButton update_jButton = null;
	private JButton Download_jButton = null;
	private JButton updateTree_jButton = null;
	private JButton tabRemove_jButton = null;
	
	private static JComboBox IPs_ComboBox = null;
	private static JLabel Download_ThisFilejLabel = null;
	private static JLabel Download_windowjLabel = null;
	private JPanel Search_mainPanel2 = null;
	private JPanel Download_ThisFilejPanel = null;
	private JSplitPane jSplitPane_DownLoadUpLoad = null;

	private finLoading loading;
	private static finStatus_ContentPane status;
	private static finTree_Panel finTreePanel;
	
	public finInterface()
	{
		if(true == SingleInstance)//still needs work
		{
			JOptionPane.showMessageDialog(new JFrame(),"finShare is already open!","Single Instance warning",JOptionPane.WARNING_MESSAGE);
		}
		else
		{
			SingleInstance = true;

			loading = new finLoading();
			loading.loadingMassage("splach screen");
		}
	}
	
	public JFrame getMain_Frame()
	{
			loading.loadingMassage("Reading setup file");
		
		finError.testErrorLog();									//run test to see if the log file is there
		finSetup.setup();											//reading settings from the setup file (setup.ini)
		changeSkin(finSetup.getSkin());								//set the look from the setup file
			loading.loadingMassage("Getting sheard list...");
		 
		finFileIndex.setRoot();										//add host info to the arraylist
		finFileIndex.filetable_create(finSetup.getSharepath());			//add all the file that are to be sheard to the array list
		finFileIndex.upDateArrayList();
		finDownloadSet.upPathList();

			loading.loadingMassage("Getting Host info...");
		main_Frame = new JFrame("finShare.v2 - Host:"+ finToolBox.loach_ip()+" - "+ finToolBox.loach_name());//create the windows and add the IP and computer name to the titel
			loading.loadingMassage("Building Main window");
		main_Frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);//set exit to close the progream
		main_Frame.setIconImage(new ImageIcon("finTools/images/finShare_iconSmall.gif").getImage());//add an icon to the window
		main_Frame.setBounds(new java.awt.Rectangle(0,0,550,400));	//add the window size
			loading.loadingMassage("adding Content Pane");
		main_Frame.setContentPane(getMain_ContentPane());			//add the main Content of the window
			loading.loadingMassage("adding meau bar");
			main_Frame.setJMenuBar(new fin_Menubar());
		main_Frame.setLocationRelativeTo(null);						//center the window on the screen
			loading.loadingMassage("all done");
			finSound.playSound("Start");							//play the startup sound
		
		if(false == finError.getError())
		{
			finError.add2ErrorLog("finShare has started successfully.","");
			setStatus("finShare has started successfully.");
		}
		else
		{
			finError.add2ErrorLog("finShare has started with errors.","");
			setStatus("finShare has started with errors");
		}
		main_Frame.pack();//The pack method sizes the frame so that all its contents are at or above their preferred sizes
		main_Frame.setVisible(true);							//show the window
		main_Frame.setExtendedState(Frame.MAXIMIZED_BOTH); //show the frame grawing to fill the screen ^_^
		System.out.println("Frame.MAXIMIZED_BOTH = "+Frame.MAXIMIZED_BOTH);
		loading.dispose();
		//Time 2 connect!
		
		if(finToolBox.loach_ip().equals("127.0.0.1"))
		{
			fin_Menubar.setConnect(false);
			setStatus("System can't find any network to send the multicast on");
		}
		else
		{
			fin_Menubar.setConnect(true);
			crossOverLayer2.connect();//call the connect
		}
		System.out.println(finSetup.getDownloadpath());
		return main_Frame;
	}

	public static void setStatus(String text)//desplay a message to the user
	{
		if(finError.getError())					//test a booline if an error acceured
		{ 
			if (finError.getError_type() != null && text != "" && text != null)
			status.setStatus("(Error "+finError.getError_type()+") Last massage: " + text);
			else if (finError.getError_type() != null)
			status.setStatus("(Error "+finError.getError_type()+")");
			else //if (other_tool.getError() == true)
			status.setStatus(" Error!! "+ text);
		}
		else
		status.setStatus(" " + text);					//desplay a message
	}
	
	public static void changeSkin(int val)			//change the skin
	{
		UIManager.LookAndFeelInfo info[] = UIManager.getInstalledLookAndFeels();//get an array of all the skins installed on the computer
		
		String skin = new String("");				// the name of the skin in the array at int val

		if(val<info.length)
		{
			skin = info[val].getClassName();		//get the skin at val in the array
			finSetup.updateSetup("[Skin]",Integer.toString(val),true);//save the skin val
		}
		else
		{
			skin = UIManager.getSystemLookAndFeelClassName();//if val is not in the array then get the system skin
          
			for(int i =0;i<info.length;i++)			//find the index of the system skin and add it to the setup file
           {
        	   if(info[i].getClassName() == skin)
        		   finSetup.updateSetup("[Skin]",Integer.toString(i),true);//save the skin index
           }
		}
	      try 
	      {
		            UIManager.setLookAndFeel(skin);	//apply the skin
		            if(main_Frame != null)			//if window load then
		            {
			            SwingUtilities.updateComponentTreeUI(main_Frame);
		            }
	      } 
	      catch (Exception e1) 
          {
	    	  finError.setError("UIManager set Look And Feel");
	    	  finError.add2ErrorLog(e1.toString());
          }	     
	} 
	public static void end()						//exit the programe
	{
		main_Frame.dispose();
		//System.exit(1);
	}
	
	public static void Refresh_screen()
	{		
		main_ContentPane.revalidate();
		main_Frame.validate();
		finTreePanel.updateNodeRenderer();
		//Tree_jTree.setCellRenderer(finTree.getJTree_CellRenderer());//refresh the file tree
	}

	private JPanel getMain_ContentPane() 			// window Content
	{
		if (null == main_ContentPane) 				// if 1st time
		{
			main_ContentPane = new JPanel();
			status = new finStatus_ContentPane();
			main_ContentPane.setLayout(new BorderLayout());//layout style
			main_ContentPane.add(getMain_SplitPane(), java.awt.BorderLayout.CENTER);// add content
			loading.loadingMassage("Status bar");
			main_ContentPane.add(status, java.awt.BorderLayout.SOUTH);//add a message bar to noteafi the user
		}
		return main_ContentPane;
	}

	private JSplitPane getMain_SplitPane() 
	{
		if (null == main_SplitPane) 
		{
			main_SplitPane = new JSplitPane();
			main_SplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);//split verical
			main_SplitPane.setOneTouchExpandable(true);//make resize easy
			main_SplitPane.setResizeWeight(0.0);
			main_SplitPane.setBottomComponent(getJSplitPane_SearchTransfer());//add the search and transfer panel to the bottom half
			main_SplitPane.setTopComponent(getSplitPane_TreeTabs());//add the file tree and tabs to the top half
		}
		return main_SplitPane;
	}

	private JSplitPane getSplitPane_TreeTabs() 
	{
		if (null == SplitPane_TreeTabs) 
		{
			SplitPane_TreeTabs = new JSplitPane();
			finTreePanel = new finTree_Panel();
			//finTreePanel.setOpaque(true);
			SplitPane_TreeTabs.setOrientation(javax.swing.JSplitPane.HORIZONTAL_SPLIT);//split horizontal
			SplitPane_TreeTabs.setOneTouchExpandable(true);
				loading.loadingMassage("Adding Tabs");
			SplitPane_TreeTabs.setRightComponent(getTab_Panel());//add the tabs
				loading.loadingMassage("Generating FileTree...");
			SplitPane_TreeTabs.setLeftComponent(finTreePanel);//get the file tree
			SplitPane_TreeTabs.setResizeWeight(1.0);
		}
		return SplitPane_TreeTabs;
	}

	private JSplitPane getJSplitPane_SearchTransfer() //return the pane with the search and transfer
	{
		if (null == jSplitPane_SearchTransfer) 
		{
			jSplitPane_SearchTransfer = new JSplitPane();
			jSplitPane_SearchTransfer.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
			jSplitPane_SearchTransfer.setOneTouchExpandable(true);
			jSplitPane_SearchTransfer.setResizeWeight(0.5);
			jSplitPane_SearchTransfer.setBottomComponent(getJSplitPane_DownLoadUpLoad());
			jSplitPane_SearchTransfer.setTopComponent(getSearch_mainPanel());//get the search table
		}
		return jSplitPane_SearchTransfer;
	}

	private JPanel getTab_Panel() 
	{
		if (null == Tab_Panel)
		{
			Tab_Panel = new JPanel();
			Tab_Panel.setLayout(new BorderLayout());
			Tab_Panel.add(getMainTab_jTabbedPane(), java.awt.BorderLayout.CENTER);
		}
		return Tab_Panel;
	}

	private JTabbedPane getMainTab_jTabbedPane() 
	{
		if (null == mainTab_jTabbedPane) 
		{
			mainTab_jTabbedPane = new JTabbedPane();
			mainTab_jTabbedPane.addTab("Control", null, getPanel_Download(), null);
			//mainTab_jTabbedPane.addTab("Chat", null, getPanel_Chat(), null);
			mainTab_jTabbedPane.addTab("Chat", null, new Tab_finChat(), null);
			mainTab_jTabbedPane.addTab("Search", null, new Tab_finSearch(), null);
		}
		return mainTab_jTabbedPane;
	}

	private JPanel getPanel_Network() 
	{
		if (null == Panel_Network)
		{
			Panel_Network = new JPanel();
			Panel_Network.setLayout(new GridBagLayout());
			Panel_Network.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Network", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
			
			GridBagConstraints gridBag = new GridBagConstraints();
			gridBag.gridx = 0;
			gridBag.gridy = 0;
			Panel_Network.add(new JLabel("Other users"), gridBag);
			
			gridBag.gridx = 1;
			gridBag.gridy = 0;
			Panel_Network.add(new JLabel("Update"), gridBag);
			
			gridBag.gridx = 1;
			gridBag.gridy = 1;
			Panel_Network.add(getUpdateTree_jButton(), gridBag);
			
			gridBag.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBag.gridy = 1;
			gridBag.weightx = 0.0;
			gridBag.gridwidth = 1;
			gridBag.anchor = java.awt.GridBagConstraints.EAST;
			gridBag.gridx = 0;
			Panel_Network.add(getIPs_ComboBox(), gridBag);
			
			gridBag.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBag.gridy = 3;
			gridBag.weightx = 0.0;
			gridBag.anchor = java.awt.GridBagConstraints.WEST;
			gridBag.gridx = 0;
		}
		return Panel_Network;
	}
	private JButton getUpdateTree_jButton() 
	{
		if (null == updateTree_jButton) 
		{
			updateTree_jButton = new JButton("File Tree");
			updateTree_jButton.setToolTipText("Update the file tree.");
			updateTree_jButton.addActionListener(this);
		}
		return updateTree_jButton;
	}

	private JComboBox getIPs_ComboBox() 
	{
		if (null == IPs_ComboBox) 
		{
			IPs_ComboBox = new JComboBox();
			IPs_ComboBox.addItem("No other users connected");
			IPs_ComboBoxFirst = true;
		}
		return IPs_ComboBox;
	}
	public static void selectIPs_ComboBox(String InIP)
	{
		if(IPs_ComboBox.getItemAt(IPs_ComboBox.getSelectedIndex()).toString().equals(InIP) == false)//if not select already
		{
			int i = 0;
			while(IPs_ComboBox.getItemAt(i).toString().equals(InIP) == false && i<IPs_ComboBox.getItemCount())
			{
				i++;
			}
			IPs_ComboBox.setSelectedIndex(i);
		}
	}
	public static void add2IPs_ComboBox(String InIP)
	{
		if(true == IPs_ComboBoxFirst)
		{
			IPs_ComboBoxFirst = false;
			IPs_ComboBox.removeAllItems();
			IPs_ComboBox.addItem("Select a user from the list");
		}
		
		IPs_ComboBox.addItem(InIP);
	}
	public static void setDownload_ThisFile(String fileName)
	{
		Download_ThisFilejLabel.setText(fileName);
	}
	
	private JPanel getPanel_Download() 
	{
		if (null == Panel_Download) 
		{
			Panel_Download = new JPanel();
			Panel_Download.setLayout(new BorderLayout());
			Panel_Download.add(getPanel_Network(), java.awt.BorderLayout.NORTH);
			Panel_Download.add(getDownload_ThisFilejPanel(), java.awt.BorderLayout.CENTER);
		}
		return Panel_Download;
	}

	private JButton getDownload_jButton() 
	{
		if (null == Download_jButton) 
		{
			Download_jButton = new JButton("Download");
			Download_jButton.setToolTipText("Download the file");
			Download_jButton.addActionListener(this);
		}
		return Download_jButton;
	}
	
	public static void setDownload_windowjLabel(String input)
	{
		Download_windowjLabel.setText(input);
	}

	private JPanel getSearch_mainPanel() 
	{
		if (null == Search_mainPanel2) 
		{
			Search_mainPanel2 = new JPanel();
			Search_mainPanel2.setLayout(new GridBagLayout());
			
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 1;
			gridBagConstraints2.anchor = java.awt.GridBagConstraints.NORTHEAST;
			gridBagConstraints2.gridy = 0;
			
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.weightx = 1.0;
			gridBagConstraints1.weighty = 1.0;
			gridBagConstraints1.gridwidth = 2;
			gridBagConstraints1.gridx = 0;

			Search_mainPanel2.add(getSearchTabsRemove(), gridBagConstraints2);
			Search_mainPanel2.add(getfinSearchTabs(), gridBagConstraints1);

		}
		return Search_mainPanel2;
	}
	
	private JButton getSearchTabsRemove()
	{
		if (null == tabRemove_jButton) 
		{
			tabRemove_jButton = new JButton("Remove");
			tabRemove_jButton.setMargin(new Insets(0,0,0,0));
			tabRemove_jButton.addActionListener(this);
    	}
		return tabRemove_jButton;
	}
	
	private JTabbedPane getfinSearchTabs()
	{
		if (null == finSearchTabs) 
		{
			finSearchTabs = new JTabbedPane();
		}
		return finSearchTabs;
	}
	
	private JPanel getDownload_ThisFilejPanel() 
	{
		if (null == Download_ThisFilejPanel) 
		{
			Download_ThisFilejPanel = new JPanel();
			Download_ThisFilejPanel.setLayout(new GridBagLayout());
			Download_ThisFilejPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Download", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
			
			Download_windowjLabel = new JLabel(" ");
			Download_ThisFilejLabel = new JLabel(" ");
			Download_ThisFilejLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			Download_ThisFilejLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
			
			GridBagConstraints gridBag = new GridBagConstraints();
			gridBag.gridx = 0;
			gridBag.anchor = java.awt.GridBagConstraints.SOUTH;
			gridBag.gridy = 0;
			Download_ThisFilejPanel.add(Download_windowjLabel, gridBag);

			gridBag.gridx = 0;
			gridBag.anchor = java.awt.GridBagConstraints.CENTER;
			gridBag.gridy = 1;
			Download_ThisFilejPanel.add(getDownload_jButton(), gridBag);
			
			gridBag.insets = new java.awt.Insets(0,0,0,0);
			gridBag.gridy = 2;
			gridBag.ipadx = 0;
			gridBag.anchor = java.awt.GridBagConstraints.NORTH;
			gridBag.gridx = 0;
			Download_ThisFilejPanel.add(Download_ThisFilejLabel, gridBag);

		}
		return Download_ThisFilejPanel;
	}	
	
	private JSplitPane getJSplitPane_DownLoadUpLoad()
	{
		if (null == jSplitPane_DownLoadUpLoad) 
		{
			JPanel pan1 = new finDownload_Panel();			
			JPanel pan2 = new finUpload_Panel();
			
			jSplitPane_DownLoadUpLoad = new JSplitPane();
			jSplitPane_DownLoadUpLoad.setOneTouchExpandable(true);
			
			jSplitPane_DownLoadUpLoad.setLeftComponent(pan1);
			jSplitPane_DownLoadUpLoad.setRightComponent(pan2);
		}
		return jSplitPane_DownLoadUpLoad;
	}

//	-----------------------------------------------------------------------------------

	public void actionPerformed(ActionEvent e) 
	{
		finSound.playSound("Click");
		
		if (e.getSource() == Download_jButton)
		{
			if(null != finDownloadSet.getIPofFiletoGet())
			{
				boolean lochIP = finDownloadSet.getIPofFiletoGet().equals(finToolBox.loach_ip());
				
				if(false == lochIP)
				{
					finSound.playSound("Download");
					setStatus("Downloading: "+Download_ThisFilejLabel.getText());
					crossOverLayer2.DownloadThisfile(finDownloadSet.getFileDescript(),finDownloadSet.getIPofFiletoGet());
				}
				else// if(lochIP)
				{
					finSound.playSound("Error_Small");
					setStatus("Can't Download from youre self!!");
				}
			}
			else
			{
				finSound.playSound("Error_Small");
				setStatus("You need to select a FILE first!");
			}
		}
		else if (e.getSource() == updateTree_jButton)
		{
			if(IPs_ComboBox.getItemAt(IPs_ComboBox.getSelectedIndex()).toString().equals("Select a user from the list"))
			{
				finSound.playSound("Error_Small");
				setStatus("Select an address first");
			}
			else if(IPs_ComboBox.getItemAt(IPs_ComboBox.getSelectedIndex()).toString().equals("No other users connected"))
			{
				finSound.playSound("Error_Small");
				setStatus("there are no user to genread from");
			}
			else
			{
				finTree oneTree = new finTree();
				finTreePanel.updateTree(oneTree.getJTree_Array_Thread(finNetList.getUsersListFromNetList(finNetList.findNetList(IPs_ComboBox.getItemAt(IPs_ComboBox.getSelectedIndex()).toString()))));
			}
		}/*
		else if (e.getSource() == update_jButton)
		{
			
		}*/
		else if (e.getSource() == tabRemove_jButton)
		{
			if(finSearchTabs.getSelectedIndex()>=0)
			{
				setStatus("Removed Search Tab - "+ finSearchTabs.getTitleAt(finSearchTabs.getSelectedIndex()));
				finSearchTabs.removeTabAt(finSearchTabs.getSelectedIndex());
			}
			else
			{
				finSound.playSound("Error_Small");
				setStatus("Select a tab to remove!");
			}
		}
	}
}
