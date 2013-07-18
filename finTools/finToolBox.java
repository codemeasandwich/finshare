package finTools;

//import finGUI.Controller3;
import finController.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;

import javax.swing.JFileChooser;

public abstract class finToolBox 
{
	private static String PC_ID = null;						//the host name
	private static String PC_ADD = null;					//the host IP
	private static Calendar now = null;
	private static InetAddress addr;
	private static JFileChooser fc = new JFileChooser();
	
	public static String getTime()
	{
		now = Calendar.getInstance();
		return now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE)+"."+now.get(Calendar.SECOND);
	}
	
	public static String getDate()
	{
		//if(now == null)
			now = Calendar.getInstance();
		
		return now.get(Calendar.DAY_OF_MONTH)+"/"+now.get(Calendar.MONTH)+"/"+now.get(Calendar.YEAR);
	}
	
	public static String loach_name()
	{
		if(null == PC_ID)
		{
		    try 
		    {
		    	setAddr();
		        PC_ID = addr.getHostName(); // Get IP Address
		    } 
		    catch (Exception e) 
		    {
		    	finError.setError("getting Local Host name :-loach_name()");
		    	finError.add2ErrorLog(e.toString());
		    }
		//PC_ID = "HomePC";
		}
		
	return PC_ID;
	}
	public static void setAddr()
	{
		if (null == addr)
		{
		    try 
		    {
		    	//addr = InetAddress.getByName("");
		    	addr = InetAddress.getLocalHost();
		    } 
		    catch (UnknownHostException e) 
		    {
		    	
		    	finError.setError("InetAddress in other tools-setAddr");
		    	finError.add2ErrorLog(e.toString());
		    }
		}
		//return addr.getHostAddress();
	}
	
	public static String loach_ip()
	{
		if(null == PC_ADD)
		{
		    try 
		   {
		    	setAddr();
		        PC_ADD = addr.getHostAddress(); //Get IP Address
		    } 
		    catch (Exception e) 
		    {
		    	finError.setError("getting Local IP address :-loach_ip()");
		    	finError.add2ErrorLog(e.toString());
		    }
		    //PC_ADD = "127.0.0.1";
		}
		return PC_ADD;
	}
	
	public static String subtract_FileExtension(String input1FileName,String input2Extension)
	{
		String output = new String();
		
		char input1_char[] = input1FileName.toCharArray();
		char input2_char[] = input2Extension.toCharArray();
		int size = input1_char.length - input2_char.length - 1;
		
		for(int i = 0; i<size; i++)
		{
			output += input1_char[i];
		}
		return output;
	}
	
	public static InetAddress getinetIP()
	{
		setAddr();
		return addr;
	}
	
	
	public static String getIP()
	{
		return PC_ADD;
	}
	
	/* Exact Conversion
	 * 1,024 Byte = 1 Kilobyte (KB) 
	 * 1,024 Kilobyte (KB) = 1 Megabyte (MB)
	 * 1,073,741,824 Bytes = 1 Gigabyte (GB) 
	 * 1 Gigabyte (GB) = 1,024 Megabyte (MB)
	 */
	public static String bytes2megabytes (long bytes)
	{
		String num;
		double temp_num;
		
		if(bytes<1048576)
		{
			temp_num = bytes/1024.0;//kilobytes
			num = "Kb ";
		}
		else if(bytes<1073741824)
		{
			temp_num = bytes/1048576.0;
			num = "Mb ";
		}
		else//(bytes>=1048576)
		{
			temp_num = bytes/1073741824.0;
			num = "Gb ";
		}
		return (num + round(temp_num,2));
	}
	
	private static double round(double value, int decimalPlace) 
	{
		double power_of_ten = 1;
		while (decimalPlace-- > 0)
			power_of_ten *= 10.0;
		return Math.round(value * power_of_ten)/ power_of_ten;
	}
	
	public static boolean changeFolder(boolean callFiletable_create,int dirSet)
	{
		//dirSet 0 = Shared Dir / 1 = Download Dir
		boolean file = false;
		
		fc.setCurrentDirectory(new java.io.File(finSetup.getSharepath()));
		fc.setDialogTitle("Change shared folder ??");
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		//System.out.println("x");
		 if (fc.showOpenDialog(fc) == JFileChooser.APPROVE_OPTION) 
		 {
			 finSound.playSound("Click");//to play after the diealog
			 
			 if(fc.isTraversable(fc.getSelectedFile()) == true)
			 {
				 if(true == callFiletable_create)
				 {
					 finFileIndex.filetable_create_Thread(fc.getSelectedFile().toString());
				 }
				 else
				 {
					 if(0 == dirSet)
					 {
						 finSetup.setSharepath(fc.getSelectedFile().toString());
					 }
					 else if (1 == dirSet)
					 {
						 finSetup.setDownloadpath(fc.getSelectedFile().toString());
					 }
				 }
			  
			 file = true;
			 }
			 else
			 {
				 finInterface.setStatus("Folder does not exist!");
				 finSound.playSound("Error_Small");
				 file = false;
			 }
		 }
		 else 
		 {
			 finSound.playSound("Click");//to play after the diealog
		    	
		    finInterface.setStatus("No Folder Selected");
		    file = false;
		 }
	 //playSound("Click");//to play after the diealog
	 return  file;
	}
	
}
