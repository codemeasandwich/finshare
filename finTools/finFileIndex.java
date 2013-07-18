package finTools;

//import inter_X00022027.*;
import finNetwork.*;
import java.util.ArrayList;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import finController.*;
//import finGUI.finInterface;
import finMain.crossOverLayer2;
//import other_tool.*;

public class finFileIndex// extends ArrayList
{
	private static fileVal twiceString;	//[0] = hash code & [1] = path
	public static ArrayList temppathList = new ArrayList();
	private static ArrayList tempfileList = new ArrayList();		//all the files Im shareing
	private static ArrayList fileList = new ArrayList();
	
	public static void setRoot()//this shoud be the first time
	{
		//fileList.clear();
		tempfileList.add(new fileDescript(finToolBox.loach_ip(),finToolBox.loach_name(),"Root",/*Integer.toString(finSetup.getPort())*/"0",0));
	}
	
	/*finFileIndex*/static public void filetable_create(String HD_path)
	{
		File dir = new File(HD_path);
		String []dirList = dir.list();
		File tempFile = null;
		String folderID = new String();
		String type = null;
		
		for(int i = 0;i<dirList.length;i++)//http://java.sun.com/j2se/1.4.2/docs/api/java/io/File.html#hashCode()
		{
		tempFile = new File(dir.getPath() + "/"+ dirList[i]);
		type = new String(getFileExtension(dirList[i]));															//	link file to local file not need on another PC // the review file will be generated any way
			if((tempFile.isDirectory() == false) && 
			   (finSetup.getHideState() == true || tempFile.isHidden() == false) &&
			   (type.equalsIgnoreCase("lnk") == false && type.equalsIgnoreCase("db") == false) &&
			   (dirList[i].equals( finSetup.getSetupFileName())== false && dirList[i].equals(finError.getlogFileName())== false) && dirList[i].equals(finError.getBackuplogFileName())== false)	
			{	
									//[ID] [description] [file name] [type]  [size]
				tempfileList.add(new fileDescript(Integer.toString(tempFile.hashCode()),dirList[i],type,finToolBox.bytes2megabytes(tempFile.length()),tempFile.length()));
				
				//twiceString.[0] = Integer.toString(tempFile.hashCode());twiceString[1] = dir.getPath() + "/"+ dirList[i];
				twiceString = new fileVal(Integer.toString(tempFile.hashCode()),dir.getPath() + "/"+ dirList[i]);
				temppathList.add(twiceString);
				//System.out.println(twiceString[0]);
			}
			else if(tempFile.isDirectory() == true)
			{	
				folderID = Integer.toString(tempFile.hashCode());
				
				tempfileList.add(new fileDescript(folderID ,dirList[i],"#",finToolBox.bytes2megabytes(tempFile.length()),tempFile.length()));
				//twiceString[0] = folderID;twiceString[1] = dir.getPath() + "/"+ dirList[i];
				twiceString = new fileVal(folderID,dir.getPath() + "/"+ dirList[i]);
				
				temppathList.add(twiceString);
				filetable_create(tempFile.getPath());
				tempfileList.add(new fileDescript(folderID ,dirList[i],"##",finToolBox.bytes2megabytes(tempFile.length()),tempFile.length()));
			}
			else if(tempFile.isHidden() == true)//this file the hidden, Keep it that way!
			{
				//...
			}
			else if(type.equalsIgnoreCase("lnk") == true || type.equalsIgnoreCase("db") == true)//this file the are not need, Keep it that way!
			{
				//...
			}
			else if(dirList[i].equals( finSetup.getSetupFileName()) == true || dirList[i].equals(finError.getlogFileName()) == true || dirList[i].equals(finError.getBackuplogFileName()) == true)//this file the are not need, Keep it that way!
			{
				//...
			}
			else 
			{
				finError.setError("filetable_create");
				finError.add2ErrorLog("filetable_create","file:"+ dirList[i]);
			}
			//fileList = tempfileList;
		}
	}
	private static String getFileExtension(String input_FileName)
	{
		char FileName[] = input_FileName.toCharArray();
		input_FileName = "";
		int i = FileName.length - 1;
		
		while(FileName[i] != '.' && i>0)// *.bmp + *.class
		{
			input_FileName = new Character(FileName[i]).toString() + input_FileName;
			i--;
		}

		if(input_FileName.equals("Root"))//root of tree
			input_FileName = "Root_";
		else if(input_FileName.equals("#") || input_FileName.equals("##"))//folder
			input_FileName = "_";
		
		return input_FileName;
	}


	public static void filetable_create_Thread(final String dir_url)
	{
			finInterface.setStatus("Generating shared list...");

	    	final JFrame OptionFrame = new JFrame();
      
	        Thread t2 = new Thread(new Runnable()
	        {
	            public void run()
	            {
	            	//finInterface.addTreadCount();
	            	
	            	tempfileList = new ArrayList();
	            	temppathList = new ArrayList();

	            	setRoot();
	            	
	            	filetable_create(dir_url);
	            	
	            	upDateArrayList(); 
	            	finDownloadSet.upPathList();
	            	crossOverLayer2.newFileList(fileList);
		        	finInterface.setStatus("Generated shared list - " + dir_url);
		        	
		        	finSetup.setSharepath(dir_url);
		        	finSound.playSound("");

		        	OptionFrame.dispose();
	            }});
	        t2.start();
	        //int n = 0;
	       // for(int x = 0; x<5; x++)
	       // {					//0 = Run			1 = can		-1 = exit/dispose
	    	Object[] options = {"Run in Background","Cancel"};
	    	 int n = JOptionPane.showOptionDialog(OptionFrame,"Generating shared list...\n this might take a minute, or two...","File list",JOptionPane.CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null, options,options[0]); //default button title;
	        //System.out.println(n);
	      //  }
	        if (n != 0)// 0 = cancel & -1 = exit fame/dispose
	        {
	        	t2.interrupt();//t2.stop();//if the user clicks the top x OR if dispose is called from the thread = -1
	        	if(1 == n)
	        	finInterface.setStatus("Generating shared list, stoped by user.");
	        }
	        else//0 = background
	        {
	        	OptionFrame.dispose();
	        	finInterface.setStatus("Generating shared list, Running are Background Thread");
	        }
	        //finInterface.removeTreadCount();
	       // tempfileList.clear();//reset the temp fileList
	}
	public static ArrayList getArrayList()
	{
		return fileList;
	}
	public static void upDateArrayList()
	{
		fileList = tempfileList;
	}
}
