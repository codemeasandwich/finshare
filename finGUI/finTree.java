 /*************************\
 *                         *
 *     FinShare v1.0       *
 * Brian Shannon X00022027 *
 *   3rd year Computing    *
 *    (ITT Dublin) 2006    *
 *                         *
 \*Class:gui_tool**********/

package finGUI;

import finNetwork.*;
import java.util.ArrayList;
import finController.*;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.tree.DefaultTreeCellRenderer;

import finTools.*;

 public class finTree //extends JTree
{
	private static JTree jTree_Array = null;
	private static String jTree_ArrayIP = new String("");

	private static int 	infoV2_guest_counter = 0;
	private static ArrayList infoV2_guest = new ArrayList();
	private static ArrayList SearchFilter = new ArrayList();
	private static DefaultTreeCellRenderer renderer = null;


	
	final String prif1[] = {"avi","mp2","xls","WMF","TIFF","SWF","RTF","RM","OGG","MPEG","mpg","MP3","MOV","MID","JPEG","SHTML","HTML","HTM","DLL", "CPP", "class", "bat", "bmp","c","exe","font","h","gif","ico","ini","java","jar","jpg","js","jsp","obj","PS","scr","ttf","txt","wav","zip","DIR","DOC","EMF","FLA","GZ","HLP","HT","MDL","MPP",
							 "OCX","PDF","RAR","RAW","REG","SMD","VM","WSZ","vcproj","CUR","eml","ppt","chm","cdr","flv","php","db","ut4mod","umod","CFG","sav","adf","gb","gba","nes","v64","sms","SMD","SMC","Inf","ins","SYS","rmvb","png","lnk","pdb","project","classpath","scm","nfo","scx","mix","ut2","css","rc","MSI","OPC","pal","cpt","m3u",
							 "ai","UID","UKX","UML","UMX","UNX","UPG","UPJ","URLS","UV","UX","UXX","UZ","ANTIFRAG","AOB","ARCH00","ASC","SHAR","SHB","SID","wal","wsz","wmz","MPQ","W3X","W3M","torrent","#","log","WMV"};

	final String prif2[] =
	{"Audio Video Interleave File(High-quality video format)","Audio File using MPEG Layer II compression","Microsoft Excel Worksheet","Windows Metafile (graphics format)","Tagged Image File Format. Universal graphics format","Shockwave Flash file by Macromedia","Rich Text Format","RealAudio video file","Ogg Vorbis digitally encoded music file",
	"Animation file format","Animation file format","MPEG Audio Layer 3","Quicktime movie","MIDI music file","Compression scheme supported by most graphics programs and used predominantly for web use","HTML file that supports Server Side Includes(SSI)","Hyper Text Markup Language. This markup language is used for web design",
	"Hyper Text Markup. This markup language is used for web design","Dynamic Link Library", "C++ Source code file", "Compiled java source code file", "MS-DOS batch file", "Bitmap image file","C language file","executable file","Text Font file","C/C++ header file","Graphics Interchange Format file",
	"Icon file","Initialization file","Java language file","Java classes archive file","Bitmap graphics (Joint Photography Experts Group)","JavaScript code file","Java Server Page file","object file","PostScript file","Windows screen saver executable file","TrueType font file","Basic text file",
	"Windows waveform audio file","Zip archive file","Macromedia Director file","Document format for Microsoft Word","Enhanced Windows Metafile","Macromedia Flash movie format","Unix compressed file","Standard help file","HyperTerminal files","Rose model file. Opens with Visual Modeler or Rational Rose",
	"Microsoft Project File","ActiveX Control: A component of the Windows environment","Portable Document File by Adobe. Viewable with Adobe Acrobat","RAR compressed archive open with WinRar","Raw File Format","Registry file that contains registry settings","SEGA mega drive ROM file","Virtual Memory file",
	"Winamp Skin","Visual Studio C++ Project","Mouse Cursor file","Outlook Express Mail Message","Microsoft PowerPoint Presentation","Compiled HTML Help file","CorelDRAW Graphic file","Macromedia Flash Video File","PHP Script file","Windows Data Base File, a preview file of thumbnails","Unreal Tournament 2004 Module",
	"modification for the Unreal Tournament series of games","a type of configeration file","generic saved game file","Commodore Amiga ROM file","Game Boy ROM file","Game Boy Advance ROM file","Nintendo ROM file","Nintendo 64 ROM file","Sega Master System ROM file","Sega Mega Drive/Genesis ROM file",
	"Super Nintendo ROM file","Setup Information","Internet Communication Settings","System file","RealPlayer Media File","Portable Network Graphics","Shortcut file","Program Debug Database","Java code editer, Ecilpse SDK Project file","Java code editer, Ecilpse SDK Project paths file","StarCraft scenario map",
	"Warez info file","StarCraft Brood war map","Game data file","Unreal Tournament 2004 map","Cascading Style Sheet Document for web pages","LiteStep config. file","Windows Installer Package","Microsoft Clean-up Wizard File","Microangelo Icon editer, Palette file","Corel PHOTO-PAINT image file",
	"Winamp playlist file","Adobe Illustrator Vector Graphic","IBM Voice Type Users File","Unreal Tournament 2003 Animation","Visual UML Class Diagram & Model","Unreal/Unreal Tournament Music File","UNIX text file","Firmware Upgrade File","ULead Project File","GetRight URL List","EA Games Video File","UNIX File",
	"Unreal Tournament 2003 Cache","Unreal Tournament Server File","DC++ Incomplete Download","DVD Audio File","F.E.A.R. Game Archive","ASCII Text","UNIX shar Archive File","Corel Background File","Commodore64 Music File","Winamp 5+ Skin Modern style","winamp skin old style","Windows Media Player 9+ skin",
	"contain a directory structure with compressed files. used for games","Warcraft III: The Frozen Throne World (Expansion Scenario File)","Warcraft III Map File","BitTorrent Metainfo File(this is a pointer to a file on the network)","Directory","a record of events that happend to the system or Program","Microsoft Windows Media Video"};//as you can tel i like my games and roms ;)
	
	public static void jTree_Enabled(boolean input)
	{
		jTree_Array.setEnabled(input);
	}

	public static ArrayList getSearchFilter()
	{
		return SearchFilter;
	}
	private String getDesc(String Desc)
	{		
		int i =0;
		boolean found = false;
		
		while(i<prif1.length)
		{
			if(Desc.equalsIgnoreCase(prif1[i]))
			{
				Desc = prif2[i];
				found = true;
			}
			i++;
		}
		if(false == found)
			Desc = Desc+" file";
			
		return Desc;
	}

	public static DefaultTreeCellRenderer getJTree_CellRenderer()
	{
		boolean icon = false;
    	renderer = new DefaultTreeCellRenderer();

        renderer.setLeafIcon(new ImageIcon("finTools/images/leaf_iconv3.gif"));
        renderer.setOpenIcon(new ImageIcon("finTools/images/branch_iconv3_open.gif"));
        renderer.setClosedIcon(new ImageIcon("finTools/images/branch_iconv3.gif"));
        	
        if(renderer.getLeafIcon().getIconWidth()>0)//test for image
             icon = true;

    	if(true == icon)
        	return renderer; 
    	else
    		return new DefaultTreeCellRenderer();
	}
	public JTree getJTree_Array(ArrayList inputlist)
	{
		infoV2_guest = inputlist;
		getJTree_Array();
	  //jTree_Array.setOpaque(false);//transparent background
		return jTree_Array;
	}
	
	public void getJTree_Array()
	{
		jTree_Array = null;
		
		fileDescript obj = (fileDescript)infoV2_guest.get(infoV2_guest_counter);
    	if (obj.getID().equals(finToolBox.loach_ip()))
    	{
    		jTree_Array = new JTree(new finFileTreeNodes(infoV2_guest,"Shared Files - "+ finSetup.getSharepath()));
    	}
    	else
    	{
    		jTree_Array = new JTree(new finFileTreeNodes(infoV2_guest,"("+ obj.getFilename() + ") " +obj.getID()));
    	}
    	jTree_ArrayIP = obj.getID();

        infoV2_guest_counter = 0;//reset the count from the next tree
        jTree_Array.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        jTree_Array.setCellRenderer(getJTree_CellRenderer());
         
        jTree_Array.addTreeSelectionListener(new TreeSelectionListener() 
        {
            public void valueChanged(TreeSelectionEvent e) 
            {
            	try//im getting any error sometime
            	{
            	  finInterface.setDownload_windowjLabel("File tree");
                  DefaultMutableTreeNode node = (DefaultMutableTreeNode)jTree_Array.getLastSelectedPathComponent();
                  Object nodeInfo = node.getUserObject();
                  finSound.playSound("Click");
                  
                  if(node.isLeaf() && nodeInfo.toString().equals("...") == false)
                  {
	                   fileDescript selectedFile = (fileDescript)nodeInfo;
	                   finTree_Panel.setFileTree_type(getDesc(selectedFile.getType()));
	                 //finDownloadSet.setDownload(selectedFile.getFilename()+"."+selectedFile.getType(),selectedFile.getID(),jTree_ArrayIP);
	                   finDownloadSet.setDownload(selectedFile,jTree_ArrayIP);//(selectedFile.getFilename()+"."+selectedFile.getType(),selectedFile.getID(),jTree_ArrayIP);
                  }
                   else if (nodeInfo.toString().equals("...") == true)
                   {
                	   finTree_Panel.setFileTree_type("empty folder");
                       finDownloadSet.nullDownload();
                   }
                   else
                   {
                	   finTree_Panel.setFileTree_type(nodeInfo.toString() + " Directory");
                	   finDownloadSet.nullDownload();
                   }
            	    	
            	}
            
            catch (Exception et)
            {
            	finError.setError("TreeSelectionEvent Object("+e+")");
            	finError.add2ErrorLog(et.toString());
            }
            }
         });

	        }
 
	public JTree getJTree_Array_Thread(final ArrayList inputlist)//getJTree_Array(inputlist)
	{
		try//try to create Threaded JTree
		{
			finInterface.setStatus("Generating File Tree...");
	    	Object[] options = {"Cancel"};
	    	final JFrame log_frame = new JFrame();
	        Thread t = new Thread(new Runnable()
	        {
	        	//public static final int NORM_PRIORITY
	        	
	            public void run()
	            {
	            //	finInterface.addTreadCount();
		            getJTree_Array(inputlist);
		            log_frame.dispose();
	            }
	        });
	        t.start();
	        t.setPriority(8);//run a little faster
	        int n = JOptionPane.showOptionDialog(log_frame,"Generating File Tree...\n this might take a minute, or two, \ncould be three...","File list",JOptionPane.CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null, options,options[0]); //default button title;
	        								
	        if (1 == n)
	        {
	        	t.interrupt();//t.stop();
	        	
	        	finInterface.setStatus("Generating File Tree, stoped by user.");
	        }
	        else
	        {
	        	finInterface.setStatus("File Tree, Generated.");
	        }
	       // finInterface.removeTreadCount();
		}
		catch(Exception ex)
		{
			finError.setError("trying to create Threaded JTree");
			finError.add2ErrorLog(ex.toString());
		}
        return jTree_Array;
	}
}