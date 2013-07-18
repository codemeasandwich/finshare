package finTools;

//import finGUI.finTree;
//import finGUI.Controller3;
//import inter_X00022027.*;
//import inter_X00022027.gui_tool;
import finController.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
//import java.util.ArrayList;
//import java.util.Iterator;
import java.util.*;

public abstract class finSetup
{
	private final static String setupFileName  = new String("finSetup.ini");
	private static ArrayList tempList = new ArrayList();	//stor the line of the file  - updateSetup() & removeSetup()
	private static String sharePath = new String (".");
	private static String downloadPath = new String (".");
	private static boolean encryp = false;
	private static boolean hide = false;
	//private static int port = 7700;
	private static int skinNum = 0;
	private static ArrayList SearchFilter = new ArrayList();
	
	public static String getSetupFileName()
	{
		return setupFileName;
	}
	
	public static void setup()//[...] are Heads
	{
		try
		{
			boolean file = false;									//a boolean to treak if there is something in the file
			BufferedReader Buffer = new BufferedReader(new FileReader(setupFileName));//read in the file to a buffer

			String counter;											//a veradale to keep each line of the text file
			
			while ((counter = Buffer.readLine()) != null)			//loop true the file while the line being read in is not null
			{
				if (counter.equalsIgnoreCase("[Shared Dir]"))		//find the Directory head in the file
				{
					sharePath = new String(Buffer.readLine());			//read in the shared Directory
					
					File Dir = new File(sharePath);						//string "path" to Directory file
					if(Dir.exists() == false)						//test to see if the Directory exists
					{												//if not
						sharePath = ".";									//set the path to the folder with as the finshare file in it
						finError.add2ErrorLog("Shared folder can't be found!.","");
						while(finToolBox.changeFolder(false,0) == false);//loop till the user pick a new Directory to share
					}
				}
				else if (counter.equalsIgnoreCase("[Download Dir]"))		//find the Directory head in the file
				{
					downloadPath = new String(Buffer.readLine());			//read in the shared Directory
					
					File Dir = new File(downloadPath);						//string "path" to Directory file
					if(Dir.exists() == false)						//test to see if the Directory exists
					{												//if not
						downloadPath = ".";									//set the path to the folder with as the finshare file in it
						finError.add2ErrorLog("Download folder can't be found!.","");
						while(finToolBox.changeFolder(false,1) == false);//loop till the user pick a new Directory to share
					}
				}
				else if (counter.equalsIgnoreCase("[Search filter]"))//find the Search filter head
				{
					while ((counter = Buffer.readLine()).equals("~") == false)//keep adding items to the search filter untel it gets to the end "~" 
					{
						addSearchFilter(counter);			//add to list
					}
				}
			/*	else if (counter.equalsIgnoreCase("[Port]"))		//find the Search filter head
				{
					port = Integer.parseInt(Buffer.readLine());
					
					if(port<1024 || port>9150)//if not valid
					{
						finError.setError("the port value in the setup file is out of range (1024-9151) NOT " + port);
						port = 7700;						//reset the port
						updateSetup("[Port]","7700",true);	//save the new port
					}
				}*/
				else if (counter.equalsIgnoreCase("[Sound]"))//find the Sound head//test for sound first!!
				{
					counter = Buffer.readLine();
					if (counter.equalsIgnoreCase("OFF")||counter.equalsIgnoreCase("NO")||counter.equalsIgnoreCase("false"))
						finSound.setSound(false);
					else if (counter.equalsIgnoreCase("ON")||counter.equalsIgnoreCase("YES")||counter.equalsIgnoreCase("true"))
						finSound.setSound(true);
				}
				else if (counter.equalsIgnoreCase("[Encryption]"))//find the Encryption head
				{
					counter = Buffer.readLine();
					if (counter.equalsIgnoreCase("OFF")||counter.equalsIgnoreCase("NO")||counter.equalsIgnoreCase("false"))
						encryp = false;
					else if (counter.equalsIgnoreCase("ON")||counter.equalsIgnoreCase("YES")||counter.equalsIgnoreCase("true"))
						encryp = true;
				}
				else if (counter.equalsIgnoreCase("[Hide]"))//find the Hide head
				{
					counter = Buffer.readLine();
					if (counter.equalsIgnoreCase("OFF")||counter.equalsIgnoreCase("NO")||counter.equalsIgnoreCase("false"))
						setHideState(false);
					else if (counter.equalsIgnoreCase("ON")||counter.equalsIgnoreCase("YES")||counter.equalsIgnoreCase("true"))
						setHideState(true);
				}
				else if (counter.equalsIgnoreCase("[Skin]"))//find the Skin head
				{
					counter = Buffer.readLine();
					setSkin(Integer.parseInt(counter));
				}
				else if (counter.equalsIgnoreCase("[Chat hostcolour]"))//find the Chat hostcolour head
				{
					counter = Buffer.readLine();
					Tab_finChat.sethostcolour(counter);
					//gui_tool.setSkin(Integer.parseInt(counter));
				}
				file = true;
			}//end of while
			Buffer.close();//release the file

			if(false == file)// if blank remake the file
			{
				finError.setError("Blank "+setupFileName);
				if(defaultSetup())// if the remake is ok, run the setup based on the new file
					setup();
			}
		}
		catch(FileNotFoundException null_ex)//file not found
		{
			finError.setError("Missing file "+setupFileName);
			finError.add2ErrorLog(null_ex.toString());
			
			if(defaultSetup())
				setup();
			else
				finError.setError("Unable to build Default "+setupFileName);
		}
		catch (Exception e3)
		{
			finError.setError("reader file set.ini");
			finError.add2ErrorLog(e3.toString());
			
			if(defaultSetup())
				setup();
			else
				finError.setError("Unable to build Default "+setupFileName);
		}
	}
	public static boolean defaultSetup()//rebuild the setup file
	{
		boolean fileMakeOK = false;
		
		try// easy to read ;)
		{
			BufferedWriter buf = new BufferedWriter(new FileWriter(setupFileName)); // open/overwirth the setup.ini file
			
			buf.write("[Shared Dir]");		buf.newLine();
			buf.write(".");					buf.newLine();
			buf.write("[Download Dir]");	buf.newLine();
			buf.write(".");					buf.newLine();
			buf.write("[Search filter]");	buf.newLine();
			buf.write("Avi");				buf.newLine();
			buf.write("Exe");				buf.newLine();
			buf.write("Mp3");				buf.newLine();
			buf.write("~");					buf.newLine();
	//		buf.write("[Port]");			buf.newLine();
	//		buf.write("7700");				buf.newLine();
			buf.write("[Encryption]");		buf.newLine();
			buf.write("OFF");				buf.newLine();
			buf.write("[Sound]");			buf.newLine();
			buf.write("OFF");				buf.newLine();
			buf.write("[Hide]");			buf.newLine();
			buf.write("NO");				buf.newLine();
			buf.write("[Skin]");			buf.newLine();
			buf.write("0");					buf.newLine();
			buf.write("[Chat hostcolour]");	buf.newLine();
			buf.write("#FF0000");			buf.newLine();
			 
			buf.flush();//flush the output buffer
	        buf.close();//close the output buffer
	        
	        fileMakeOK = true;
		}
		catch(Exception ex)
		{
			finError.setError("createshon error "+setupFileName);
			finError.add2ErrorLog(ex.toString());
		}
		return fileMakeOK;
	}
	public static void removeSetup(String head, String val)	//remore an item from the setup file
	{
		String text = new String();							//a temp val to store the lines coming in from the file
		
		try
		{
			BufferedReader Buffer = new BufferedReader(new FileReader(setupFileName));
			text = Buffer.readLine();						//get the first line
			
			while(null != text && (text.equalsIgnoreCase(head)) == false)//loop true the file till you find the [head]
			{
				tempList.add(text);
				text = Buffer.readLine();
			}
			while(text != null && text.equalsIgnoreCase(head))
			{
				tempList.add(text);						//keep [... head ...]
				text = Buffer.readLine();
				
				while(null != text && text.equals(val)== false)
				{
					tempList.add(text);
					text = Buffer.readLine();
				}
				text = Buffer.readLine();
			}
			while(text != null)	//move all the line in the file up one till you get to the end of the file
			{
				tempList.add(text);
				text = Buffer.readLine();
			}
			Buffer.close();	
		}
		
		catch(Exception ex)
		{
			finError.setError("Reading file "+setupFileName);
			finError.add2ErrorLog(ex.toString());
		}
		
		try
		{
			BufferedWriter buf = new BufferedWriter(new FileWriter(setupFileName));
	        Iterator i = tempList.iterator();		//get size of the arraylist
	        
	        while (i.hasNext()) 						//loop arraylist
	        {
	            buf.write(i.next().toString());
	            buf.newLine();
	        }
	        
	        tempList.clear();						//clear out the arraylist beacuse its used in two funckasion
	        buf.flush();
	        buf.close();
		}
		catch (Exception ex2)
		{
			finError.setError("writing file "+setupFileName);
			finError.add2ErrorLog(ex2.toString());
		}
	}
	public static void updateSetup(String head, String val, boolean replace)
	{
		String text = new String();
		boolean existsInFile = false;
		
		try
		{
			BufferedReader Buffer = new BufferedReader(new FileReader(setupFileName));
			text = Buffer.readLine();//get the first line
			
			while(null != text && (text.equalsIgnoreCase(head)) == false)
			{
				tempList.add(text);
				text = Buffer.readLine();
			}
			while(text != null && text.equalsIgnoreCase(head))
			{
				tempList.add(text);//add [... head ...]
				
				if (true == replace)
				{
					Buffer.readLine();//next
					text = val;
				}
				else if(false == replace)
				{
					tempList.add(val);
					text = Buffer.readLine();
				}
				existsInFile = true;
			}
			while(null != text)
			{
				tempList.add(text);
				text = Buffer.readLine();
			}
			if (false == existsInFile)
			{
				tempList.add(head);
				tempList.add(val);
			}
			Buffer.close();	
		}
		 
		catch(Exception ex)
		{
			finError.setError("Reading file "+setupFileName);
			finError.add2ErrorLog(ex.toString());
		}
		
		try
		{
			BufferedWriter buf = new BufferedWriter(new FileWriter(setupFileName));
	        Iterator i = tempList.iterator();
	        
	        while (i.hasNext()) 
	        {
	            buf.write(i.next().toString());
	            buf.newLine();
	        }
	        
	        tempList.clear();
	        buf.flush();
	        buf.close();
		}
		catch (Exception ex2)
		{
			finError.setError("writing file "+setupFileName);
			finError.add2ErrorLog(ex2.toString());
		}
	} 
	public static boolean getEncrypState()
	{
		return encryp;
	}
	public static void setEncrypState(boolean state)
	{
		encryp = state;
	}
	public static boolean getHideState()
	{
		return hide;
	}
	public static void setHideState(boolean HideInput)
	{
		hide = HideInput;
	}
	public static void setSharepath(String addr)				//get the shared dir
	{
		sharePath = addr; 
		updateSetup("[Directory]",addr,true);			//save the new shared dir to file
	}
	public static void setDownloadpath(String addr)				//get the shared dir
	{
		downloadPath = addr; 
		updateSetup("[Download Dir]",addr,true);			//save the new shared dir to file
	}/*
	public static int getPort()
	{
		return port;
	}*/
	public static String getSharepath()
	{
		return sharePath;
	}
	public static String getDownloadpath()
	{
		return downloadPath;
	}
	public static int getSkin()
	{
		return skinNum;
	}

	public static void setSkin(int val)
	{
		skinNum = val;
	}
	public static void removeSearchFilter(String prefix)
	{
		String pre = new String();
		
		for (int i = 0;i<SearchFilter.size();i++)
		{
			pre = (String)SearchFilter.get(i);
			
			if(pre.equalsIgnoreCase(prefix))
			{
				SearchFilter.remove(i);
			}
		}
	}
	
	public static void addSearchFilter(String prefix)
	{
		SearchFilter.add(prefix);
	}
	public static ArrayList getSearchFilter()
	{
		return SearchFilter;
	}
}
