 /*************************\
 *                         *
 *     FinShare v1.0       *
 * Brian Shannon X00022027 *
 *   3rd year Computing    *
 *    (ITT Dublin) 2006    *
 *                         *
 \*Class:fin_Menubar*******/
 
package finGUI;

import finTools.*;
import finMain.*;
import finTools.finError;
import finTools.finSound;
import finController.*;

import java.util.ArrayList;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
//import java.awt.TextArea;
//import javax.swing.JTextPane;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
//import javax.swing.event.HyperlinkEvent;
//import javax.swing.event.HyperlinkListener;

public class fin_Menubar extends JMenuBar implements ActionListener
{

	private static final long serialVersionUID = 1L;
	private String skinHumanName = null;
	private String skinClassName = null;
	private UIManager.LookAndFeelInfo[] info = null;
	  
	private ButtonGroup groupSkin = null;
	
	private JMenuBar MenuBar = null;
	  
	private JMenu Menu_file;
	private static  JRadioButtonMenuItem Menu_File_Connect;
	private static  JRadioButtonMenuItem Menu_File_Disconnect;
	private ButtonGroup groupConnect;
	private JMenuItem Menu_File_Connectto;
	private JMenuItem Menu_File_Exit;
	  
	private JMenu Menu_view;
	private JMenu Menu_view_Skins;
	private JRadioButtonMenuItem skinItem;
	private ImageIcon skinItemIcon;
	private JMenuItem Menu_view_Refresh;
	private JMenuItem Menu_view_ViewLogfile;
	private JMenuItem Menu_view_CleanLogfile;
	  
	private JMenu Menu_tool;
	private JMenu Menu_tool_shareoptions;
	private JMenuItem Menu_tool_shareoptions_Changesharedfolder;
	private JMenuItem Menu_tool_shareoptions_ChangeDownloadfolder;
	private JMenuItem Menu_tool_shareoptions_AddSearchFillter;
	private JMenuItem Menu_tool_shareoptions_RemoveSearchFilter;
	private JCheckBoxMenuItem Menu_tool_shareoptions_SharehiddenfileYN;
	private JCheckBoxMenuItem Menu_tool_encryption;
	private JCheckBoxMenuItem  Menu_tool_Sound;
	  
	private JMenu Menu_help;
	private JMenuItem Menu_help_Aboutus;
	private JMenuItem Menu_help_HelpDoc;
	
	public static void setConnect(boolean connect)
	{
		if (true == connect)
		{
			Menu_File_Connect.setSelected(true);
			Menu_File_Connect.setEnabled(false);
			Menu_File_Disconnect.setEnabled(true);
		}
		else
		{
			Menu_File_Disconnect.setSelected(true);
			Menu_File_Connect.setEnabled(true);
			Menu_File_Disconnect.setEnabled(false);
		}	
	}
	
	public fin_Menubar()//JMenuBar getMenuBar() 
	{
		if (null == MenuBar)
		{
			MenuBar = new JMenuBar();//loading.loadingMassage("adding meau bar - File");
			Menu_file = new JMenu("File");
			Menu_file.setMnemonic(KeyEvent.VK_F);
			this.add(Menu_file);
			
			groupConnect = new ButtonGroup();
			Menu_File_Connect = new JRadioButtonMenuItem("Connect...",new ImageIcon("finTools/images/menu/connect.gif"));
			Menu_File_Connect.setMnemonic(KeyEvent.VK_C);
			Menu_File_Connect.addActionListener(this);
			
			groupConnect.add(Menu_File_Connect);
			Menu_file.add(Menu_File_Connect);
		    
		   Menu_File_Disconnect = new JRadioButtonMenuItem("Disconnect-||-",new ImageIcon("finTools/images/menu/disconnect.gif"));
		   Menu_File_Disconnect.setMnemonic(KeyEvent.VK_D);
		   Menu_File_Disconnect.addActionListener(this);
		   groupConnect.add(Menu_File_Disconnect);
		   Menu_file.add(Menu_File_Disconnect);
		    
		    Menu_file.addSeparator();
		    
		    Menu_File_Connectto = new JMenuItem("Connect to..",new ImageIcon("finTools/images/menu/connect to.gif"));
		    Menu_File_Connectto.setMnemonic(KeyEvent.VK_O);
		    Menu_File_Connectto.addActionListener(this);
		    Menu_file.add(Menu_File_Connectto);
		    
		    Menu_file.addSeparator();
		    
		    Menu_File_Exit = new JMenuItem("Exit",new ImageIcon("finTools/images/menu/exit.gif"));
		    Menu_File_Exit.setMnemonic(KeyEvent.VK_X);
		    Menu_File_Exit.addActionListener(this);
		    Menu_file.add(Menu_File_Exit);
		    
		    Menu_view = new JMenu("View");//loading.loadingMassage("adding meau bar - View");
		    Menu_view.setMnemonic(KeyEvent.VK_V);
			this.add(Menu_view);
		    
			//loading.loadingMassage("loading Skins");
			Menu_view_Skins = new JMenu("Skins");
			Menu_view.add(Menu_view_Skins);

			groupSkin = new ButtonGroup();
			info = UIManager.getInstalledLookAndFeels();//make radio buttons
			 
			    for (int i=0; i<info.length; i++) 
			    {
			       skinHumanName = info[i].getName();
			    	skinClassName = info[i].getClassName();
			        
			        if(skinClassName.equals("javax.swing.plaf.metal.MetalLookAndFeel"))
			        {
			        	skinItemIcon = new ImageIcon("finTools/images/menu/skin1v2.gif");
			        }
			        else if (skinClassName.equals("com.sun.java.swing.plaf.motif.MotifLookAndFeel"))
			        {
			        	skinItemIcon = new ImageIcon("finTools/images/menu/skin2v2.gif");
			        }
			        else if (skinClassName.equals("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"))
			        {
			        	skinItemIcon = new ImageIcon("finTools/images/menu/skin3v2.gif");
			        }
			        else if (skinClassName.equals("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel"))
			        {
			        	skinItemIcon = new ImageIcon("finTools/images/menu/skin4v2.gif");
			        }
			        else
			        {
			        	skinItemIcon = new ImageIcon("finTools/images/menu/skin5v0.gif");
			        }

			        skinItem = new JRadioButtonMenuItem(skinHumanName,skinItemIcon);
			        skinItem.addActionListener(new ActionListener() 
				    {
		    			public void actionPerformed(ActionEvent e) 
		    			{
		    				finSound.playSound("Click");
		    				int i = 0;
		    				boolean findSkin = false;
		    				while(i<groupSkin.getButtonCount() && false == findSkin)
		    				{
		    					if (info[i].getName().equals(e.getActionCommand()))
		    					{
		    						findSkin = true;
				    				finInterface.changeSkin(i);
				    				finInterface.setStatus("Skin changed to " + e.getActionCommand());
		    					}
		    					else
		    					{
		    						i++;
		    					}
		    				}
		    				finInterface.Refresh_screen();
		    			}}
		    		);
			        groupSkin.add(skinItem);
			        Menu_view_Skins.add(skinItem);
			        
			        if(finSetup.getSkin() == i)
			        {
			        	skinItem.setSelected(true);
			        }
			 }

			Menu_view_Refresh = new JMenuItem("Refresh",new ImageIcon("finTools/images/menu/refresh.gif"));
			Menu_view_Refresh.setMnemonic(KeyEvent.VK_R);
			Menu_view_Refresh.addActionListener(this);
		    Menu_view.add(Menu_view_Refresh);
		    
		    Menu_view.addSeparator();
		    
		    Menu_view_ViewLogfile = new JMenuItem("View Log file",new ImageIcon("finTools/images/menu/log.gif"));
		    Menu_view_ViewLogfile.setMnemonic(KeyEvent.VK_L);
		    Menu_view_ViewLogfile.addActionListener(this);
		    Menu_view.add(Menu_view_ViewLogfile);

		    Menu_view_CleanLogfile = new JMenuItem("Clean Log file",new ImageIcon("finTools/images/menu/Clear_log.gif"));
		    Menu_view_CleanLogfile.setMnemonic(KeyEvent.VK_C);
		    Menu_view_CleanLogfile.addActionListener(this);
		    Menu_view.add(Menu_view_CleanLogfile);
		    
		    Menu_tool = new JMenu("Tools");//loading.loadingMassage("adding meau bar - Tool");
		    Menu_tool.setMnemonic(KeyEvent.VK_T);
		    this.add( Menu_tool);
		    Menu_tool_shareoptions = new JMenu("Share Options");//,new ImageIcon("finTools/images/menu/share.gif"));
		    Menu_tool_shareoptions.setMnemonic(KeyEvent.VK_O);
		    
		    Menu_tool_shareoptions_Changesharedfolder = new JMenuItem("Change Shared folder?",new ImageIcon("finTools/images/menu/change folder.gif"));
		    Menu_tool_shareoptions_Changesharedfolder.setMnemonic(KeyEvent.VK_C);
		    Menu_tool_shareoptions_Changesharedfolder.addActionListener(this);
		    Menu_tool_shareoptions.add(Menu_tool_shareoptions_Changesharedfolder);
		    
		    Menu_tool_shareoptions_ChangeDownloadfolder = new JMenuItem("Change Download folder?",new ImageIcon("finTools/images/menu/share.gif"));
		    Menu_tool_shareoptions_ChangeDownloadfolder.setMnemonic(KeyEvent.VK_D);
		    Menu_tool_shareoptions_ChangeDownloadfolder.addActionListener(this);
		    Menu_tool_shareoptions.add(Menu_tool_shareoptions_ChangeDownloadfolder);
		    
		    Menu_tool_shareoptions.addSeparator();
		    
		    Menu_tool_shareoptions_AddSearchFillter = new JMenuItem("Add Search Fillter",new ImageIcon("finTools/images/menu/file+.gif"));
		    Menu_tool_shareoptions_AddSearchFillter.setMnemonic(KeyEvent.VK_A);;
		    Menu_tool_shareoptions_AddSearchFillter.addActionListener(this);
		    Menu_tool_shareoptions.add(Menu_tool_shareoptions_AddSearchFillter);
		    
		    Menu_tool_shareoptions_RemoveSearchFilter = new JMenuItem("Remove Search Filter",new ImageIcon("finTools/images/menu/file-.gif"));
		    Menu_tool_shareoptions_RemoveSearchFilter.setMnemonic(KeyEvent.VK_R); //item24.setMnemonic(KeyEvent.VK_MINUS);
		    Menu_tool_shareoptions_RemoveSearchFilter.addActionListener(this);
		    Menu_tool_shareoptions.add(Menu_tool_shareoptions_RemoveSearchFilter);
		    
		    Menu_tool_shareoptions.addSeparator();

		    Menu_tool_shareoptions_SharehiddenfileYN = new JCheckBoxMenuItem("Share hidden file Y/N",new ImageIcon("finTools/images/menu/hide.gif"));
		    Menu_tool_shareoptions_SharehiddenfileYN.setMnemonic(KeyEvent.VK_S);
		    Menu_tool_shareoptions_SharehiddenfileYN.setState(finSetup.getHideState());
		    Menu_tool_shareoptions_SharehiddenfileYN.addActionListener(this);
		    Menu_tool_shareoptions.add(Menu_tool_shareoptions_SharehiddenfileYN);

		    Menu_tool.add(Menu_tool_shareoptions);
		    Menu_tool.addSeparator();
		    
		    Menu_tool_encryption = new JCheckBoxMenuItem("encryption ON/OFF",new ImageIcon("finTools/images/menu/encryption.gif"));
		    Menu_tool_encryption.setMnemonic(KeyEvent.VK_E);
		    Menu_tool_encryption.setState(finSetup.getEncrypState());
		    Menu_tool_encryption.addActionListener(this);
		    Menu_tool.add(Menu_tool_encryption);

		    Menu_tool_Sound = new JCheckBoxMenuItem("Sound ON/OFF",new ImageIcon("finTools/images/menu/sound.gif"));
		    Menu_tool_Sound.setMnemonic(KeyEvent.VK_S);
		    Menu_tool_Sound.setState(finSound.getSound());
		    Menu_tool_Sound.addActionListener(this);
		    Menu_tool.add(Menu_tool_Sound);

			Menu_help = new JMenu("Help");//loading.loadingMassage("adding meau bar - Help");
			Menu_help.setMnemonic(KeyEvent.VK_H);
			this.add(Menu_help);
			
			Menu_help_Aboutus = new JMenuItem("About us!",new ImageIcon("finTools/images/menu/about.gif"));
			Menu_help_Aboutus.setMnemonic(KeyEvent.VK_A);
			Menu_help_Aboutus.addActionListener(this);
		    Menu_help.add(Menu_help_Aboutus);
		    
		    Menu_help_HelpDoc = new JMenuItem("Help Doc",new ImageIcon("finTools/images/menu/help.gif"));
		    Menu_help_HelpDoc.setMnemonic(KeyEvent.VK_HELP);
		    Menu_help_HelpDoc.addActionListener(this);
    			
		    Menu_help.add(Menu_help_HelpDoc);
		}
		//return this;
	}
	
//	-----------------------------------------------------------------------------------

	public void actionPerformed(ActionEvent e) 
	{
		finSound.playSound("Click");
		
		if (e.getSource() == Menu_File_Connect)
		{
			//if(true == Menu_File_DisCon)
			//{
				//Menu_File_DisCon = false;
			Menu_File_Connect.setEnabled(false);
			Menu_File_Disconnect.setEnabled(true);
				finInterface.setStatus("connecting...");
				crossOverLayer2.connect();
				Tab_finChat.add2chat("<center><b>Reconnected!</b> ("+finToolBox.getTime()+")</center>");
			//}
			//else
			//{
			//	finInterface.setStatus("you are already connected");
			//}
		}
		else if (e.getSource() == Menu_File_Disconnect)
		{
			Menu_File_Connect.setEnabled(true);
			Menu_File_Disconnect.setEnabled(false);

				finInterface.setStatus("Disconnected...");
				crossOverLayer2.Disconnect();
				Tab_finChat.add2chat("<center><b>Disconnected!</b> ("+finToolBox.getTime()+")</center>");		
	
		}
		else if (e.getSource() == Menu_File_Connectto)
		{
			finInterface.setStatus("connect to...");
			
			JPanel connect2Pane = new JPanel();
			connect2Pane.setLayout(new GridBagLayout());
			connect2Pane.setBorder(javax.swing.BorderFactory.createEmptyBorder(5,5,5,5));
			
			final JFrame connect2field = new JFrame("connect to...");
			connect2field.setSize(new java.awt.Dimension(275,110));
			connect2field.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
			connect2field.setIconImage(new ImageIcon("finTools/images/finShare_iconSmall.gif").getImage());
			connect2field.setResizable(false);
			
			GridBagConstraints gridBagX = new GridBagConstraints();
			gridBagX.gridx = 0;
			gridBagX.gridy = 0;
			connect2Pane.add(new JLabel("connect to:"), gridBagX);
			
			gridBagX.gridx = 0;
			gridBagX.gridwidth = 3;
			gridBagX.gridy = 1;
			JRadioButton ipRadioButton = new JRadioButton("IP Address");
			ipRadioButton.setSelected(true);
			connect2Pane.add(ipRadioButton, gridBagX);
			
			gridBagX.gridx = 0;
			gridBagX.gridwidth = 3;
			gridBagX.gridy = 2;
			JRadioButton hostNameRadioButton = new JRadioButton("Computer Name");
			connect2Pane.add(hostNameRadioButton, gridBagX);

			gridBagX.gridx = 4;
			gridBagX.gridheight = 3;
			gridBagX.gridy = 1;
			JButton cancelButton = new JButton("Cancel");
			
			cancelButton.addActionListener(new ActionListener() 
    			    {
    	    			public void actionPerformed(ActionEvent e) 
    	    			{
    	    				finInterface.setStatus("connect to... Canceled");
    	    				
    	    				finSound.playSound("Click");
    	    				connect2field.dispose();
    	    			}});
			
			connect2Pane.add(cancelButton, gridBagX);

			gridBagX.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagX.gridy = 0;
			gridBagX.weightx = 1.0;
			gridBagX.gridheight = 1;
			gridBagX.gridwidth = 6;
			gridBagX.gridx = 1;
			
			final JTextField inputTextField = new JTextField();
			connect2Pane.add(inputTextField, gridBagX);
			
			gridBagX.gridx = 3;
			gridBagX.gridwidth = 1;
			gridBagX.gridheight = 2;
			gridBagX.gridy = 1;
			JButton okButton = new JButton("Ok");
			okButton.addActionListener(new ActionListener() 
    			    {
    			public void actionPerformed(ActionEvent e) 
    			{
    				finInterface.setStatus("connectting...");
    				
    				finSound.playSound("Click");
    				crossOverLayer2.connect2(inputTextField.getText());
    				connect2field.dispose();
    			}});
			connect2Pane.add(okButton, gridBagX);
			
			ButtonGroup Group_RadioButton = new ButtonGroup();
			
			Group_RadioButton.add(ipRadioButton);
			Group_RadioButton.add(hostNameRadioButton);
			
			connect2field.setContentPane(connect2Pane);
			
			connect2field.setVisible(true);
		}
		else if (e.getSource() == Menu_File_Exit)
		{
			finInterface.end();
		}

		else if (e.getSource() == Menu_view_Refresh)
		{
			finInterface.Refresh_screen();
			finInterface.setStatus("Screen Refreshed");
		}
		else if (e.getSource() == Menu_view_ViewLogfile)
		{
			finInterface.setStatus("Log file");
			File lost = new File(".");
			String help_url = new String("file:"+lost.getPath().toString()+"/"+finError.getlogFileName());
			JEditorPane Htmlview = null;
			
			JFrame jFrame = new JFrame("Log File");

			jFrame.setIconImage(new ImageIcon("finTools/images/menu/log.gif").getImage());
			jFrame.setSize(600, 300);
			jFrame.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
			
			JPanel jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			try 
			{
				Htmlview = new JEditorPane(help_url);
				Htmlview.setContentType("text/html");//type of file to dispaly
				Htmlview.setEditable(false);// view only 

				//Htmlview.addHyperlinkListener(new MyHyperlinkListener());
				jPanel.add(new JScrollPane(Htmlview), java.awt.BorderLayout.CENTER);
			} 

			catch (IOException e1)
			{
				jPanel.add(new JLabel("log file not found"),java.awt.BorderLayout.CENTER);
				finError.setError("Log file not found");
				finError.add2ErrorLog(e1.toString());
			}
			catch(Exception e2)
			{
				finError.setError("Log viewer system");
				finError.add2ErrorLog(e2.toString());
			}
			
			jFrame.setContentPane(jPanel);
			jFrame.setLocationRelativeTo(null);//center of the screen
			jFrame.setVisible(true);
		}
		else if (e.getSource() == Menu_view_CleanLogfile)
		{
			finInterface.setStatus("Do you really want to clean out the log file?");
			finError.clearLogFile();
		}
		else if (e.getSource() == Menu_tool_shareoptions_Changesharedfolder)
		{
			finInterface.setStatus("Change shared folder?");
			finToolBox.changeFolder(true,0);
		}
		else if (e.getSource() == Menu_tool_shareoptions_ChangeDownloadfolder)
		{
			finInterface.setStatus("Change Download folder?");
			//finToolBox.changeSharedFolder(true);
		}
		
		else if (e.getSource() == Menu_tool_shareoptions_AddSearchFillter)
		{
			finInterface.setStatus("Add a fillter");
			String val = new String("");
			boolean PrefixExistes = false;
				val = JOptionPane.showInputDialog("Plase enter the short prefix of the file. e.g. mp3");
				finSound.playSound("Click");
			if(	null == val || val.equals("") || "null" == val  || val.equals(" "))
			{
				finSound.playSound("Error_Small");
				finInterface.setStatus("No prefix added!");
			}
			else//if (val != null && val.equals("") == false && val.equals(" ") == false)
			{
				ArrayList vals = finSetup.getSearchFilter();
				
				for(int i = 0;i<vals.size(); i++)
				{
				  if(vals.get(i).toString() != null)
					if(vals.get(i).toString().equalsIgnoreCase(val))
					{
						PrefixExistes = true;
						finError.setError("Prefix existes");
					}
				} 
				if (false == PrefixExistes)
				{
					finSetup.updateSetup("[Search filter]",val,false);
					Tab_finSearch.addFilter2ComboBox(val);
    				finSetup.addSearchFilter(val);
    				finInterface.setStatus("Prefix add!");
				}
			}
		}
		else if (e.getSource() == Menu_tool_shareoptions_RemoveSearchFilter)
		{
			finInterface.setStatus("Remove a filter");
			final JFrame jFrame = new JFrame("Remove filter");
			jFrame.setSize(new java.awt.Dimension(180,250));
			//jFrame.setTitle("Remove fillter");
			jFrame.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
			jFrame.setResizable(false);
			
			jFrame.setIconImage(new ImageIcon("finTools/images/finShare_iconSmall.gif").getImage());
			
			JPanel jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			jPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5,5,5,5));
			
			GridBagConstraints gridBagX = new GridBagConstraints();
			
			gridBagX.gridx = 0;
			gridBagX.gridy = 3;
			JButton jButton = new JButton("Remove!");
			jPanel.add(jButton, gridBagX);
			
			gridBagX.gridx = 1;
			gridBagX.gridy = 3;
			JButton jButton2 = new JButton("Cancel");
			jPanel.add(jButton2, gridBagX);
			
			jButton2.addActionListener(new ActionListener() 
		    {
    			public void actionPerformed(ActionEvent e) 
    			{
    				finSound.playSound("Click");
    				jFrame.dispose();
    			}});
			
			gridBagX.gridx = 0;
			gridBagX.gridy = 0;
			gridBagX.gridwidth = 2;
			JLabel jLabel = new JLabel("Select a filter from the list to remove");
			jPanel.add(jLabel, gridBagX);
			
			gridBagX.gridx = 0;
			gridBagX.gridy = 2;
			gridBagX.gridwidth = 2;
			final JLabel jLabel2 = new JLabel("");
			jPanel.add(jLabel2, gridBagX);
			
			gridBagX.fill = java.awt.GridBagConstraints.BOTH;
			gridBagX.gridy = 1;
			gridBagX.weightx = 1.0;
			gridBagX.weighty = 1.0;
			gridBagX.gridheight = 1;
			gridBagX.gridx = 0;

			final DefaultListModel DefaultList = new DefaultListModel();
			final JList jList = new JList(DefaultList);
			jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

			ArrayList vals = finSetup.getSearchFilter();
			
			for(int i = 0;i<vals.size(); i++)
			{
				if(vals.get(i).toString() != null)
					DefaultList.add(i,vals.get(i).toString());
			}
			
			JScrollPane jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(jList);
			jPanel.add(jScrollPane, gridBagX);
			
			jButton.addActionListener(new ActionListener() 
		    {
    			public void actionPerformed(ActionEvent e) 
    			{
    				if(jList.getSelectedIndex()>=0)
    				{
    					finSound.playSound("Click");
    					jLabel2.setText((String)jList.getSelectedValue()+" Removed");
    					finSetup.removeSetup("[Search filter]",(String)jList.getSelectedValue());
	    				finSetup.removeSearchFilter((String)jList.getSelectedValue());
	    				Tab_finSearch.updateFilter_ComboBox();
	    				DefaultList.remove(jList.getSelectedIndex());
    				}
    				else
    				{
    					finSound.playSound("Error_Small");
    					jLabel2.setText("No item selected!");
    					//JOptionPane.showMessageDialog(null,"No item selected!");
    				}
    			}}
			);
			jFrame.setContentPane(jPanel);
			jFrame.setLocationRelativeTo(null);
			jFrame.setVisible(true);
		}
		else if (e.getSource() == Menu_tool_shareoptions_SharehiddenfileYN)
		{
			if (true == Menu_tool_shareoptions_SharehiddenfileYN.getState())
			{
				finInterface.setStatus("Share hidden files!");
				finSetup.updateSetup("[Hide]","ON",true);
			}
			else if(false == Menu_tool_shareoptions_SharehiddenfileYN.getState())
			{
				finInterface.setStatus("Don't share hidden files!");
				finSetup.updateSetup("[Hide]","OFF",true);
			}
		}
		else if (e.getSource() == Menu_tool_encryption)
		{
			if (true == Menu_tool_encryption.getState())
			{
				finInterface.setStatus("encryption ON!");
				finSetup.setEncrypState(true);
				finSetup.updateSetup("[Encryption]","ON",true);
			}
			else if(false == Menu_tool_encryption.getState())
			{
				finInterface.setStatus("encryption OFF!");
				finSetup.setEncrypState(false);
				finSetup.updateSetup("[Encryption]","OFF",true);
			}	
		}
		else if (e.getSource() == Menu_tool_Sound)
		{
			if (true == Menu_tool_Sound.getState())
			{
				finInterface.setStatus("Sound ON!");
				finSound.setSound(true);
				finSetup.updateSetup("[Sound]","ON",true);
				finSound.playSound("Click");
			}
			else if(false == Menu_tool_Sound.getState())
			{
				finInterface.setStatus("Sound OFF!");
				finSound.setSound(false);
				finSetup.updateSetup("[Sound]","OFF",true);
			}	
		}
		else if (e.getSource() == Menu_help_Aboutus)//--------------------------------------------------------------------------------------
		{
			finInterface.setStatus("About us");
			
			JLabel jLabel = new JLabel();
			try
			{
				jLabel.setIcon(new ImageIcon(getClass().getResource("/finTools/images/finShare2_txt.gif")));
				//jLabel.setText(crossOverLayer2.versionInformation());
			}
			catch (Exception ex)
			{
				jLabel.setText("About us, image not found. Sorry :'(");
				finError.setError("About us, image not found:");
				finError.add2ErrorLog(ex.toString());
			}
			JPanel jContentPane = new JPanel();
			jContentPane.setLayout(new GridBagLayout());
			jContentPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(5,5,5,5));

			String[] verInfo = crossOverLayer2.versionInformation();
			GridBagConstraints gridBagPanel = new GridBagConstraints();
			
			gridBagPanel.weighty = 1.0;
			gridBagPanel.gridx = 0;
			gridBagPanel.gridy = 0;
			
			for(int i = 0; i<verInfo.length; i++)
			{
				System.out.print(i);
				gridBagPanel.gridy = i;
				jContentPane.add(new JLabel(verInfo[i]),gridBagPanel);
			}
			gridBagPanel.gridx = 0;
			gridBagPanel.gridy = 0;
			gridBagPanel.gridheight = verInfo.length;
			jContentPane.add(jLabel,gridBagPanel);
			
			JFrame jFrame = new JFrame();
			jFrame.setSize(new java.awt.Dimension(334,236));
			jFrame.setTitle("About US");
			jFrame.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
			jFrame.setResizable(false);
			
			jFrame.setContentPane(jContentPane);
			jFrame.setVisible(true);
		}
		else if (e.getSource() == Menu_help_HelpDoc)
		{
			finInterface.setStatus("Help file");
		try 
			{
				Runtime r=  Runtime.getRuntime();
				 r.exec(new String[] {"cmd.exe", "/c", "finHelp.chm"});
			} 

			catch (IOException e1)
			{	
				finError.setError("Help file I/O");
				finError.add2ErrorLog(e1.toString());
			}
			catch(Exception e2)
			{
				finError.setError("Help file process");
				finError.add2ErrorLog(e2.toString());
			}
		}
	}
}