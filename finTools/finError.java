package finTools;

//import finGUI.Controller3;
//import inter_X00022027.*;
import finController.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public abstract class finError //static
{
	private static File logFile = null;
	private static File logBackupFile = null;
	
	private final static String logFileName  = new String("finLog.log");
	private final static String logBackupFileName  = new String("finLog_Backup.log");
	private static String Error_type = new String();
	
	private static boolean error = false;
	
	public static String getBackuplogFileName()
	{
		return logBackupFileName;
	}
	public static String getlogFileName()
	{
		return logFileName;
	}
	public static void add2ErrorLog(String Exception)	//save the java error to the log file
	{
		add2ErrorLog(Error_type,Exception);				//get the coder error and the java error and save them
	}
	public static void testErrorLog()					//run test to see if the log file is there
	{
		logFile = new File(logFileName);
		if(logFile.exists() == false)
		{
			setError("Missing file "+logFileName);		//will call the add to log wiech will create a new log file
		}
	}
	
	public static void clearLogFile()
	{
		int delete = 0;
		delete = JOptionPane.showConfirmDialog(new JFrame(),
		    "Do you want to clear the log file?",// \n once cleard it can not be ercherved!!",
		    "erase log file",
		    JOptionPane.YES_NO_OPTION);
		
		finSound.playSound("Click");
		
		if(0 == delete)
		{
			
			logBackupFile = new File(logBackupFileName);
			if(logBackupFile.exists())
				logBackupFile.delete();
			
			logFile = new File(logFileName);
			if(logFile.exists())
				logFile.renameTo(new File(logBackupFileName));
			
			buildlogFile();
			add2ErrorLog("Log file erasted by user","");
			finInterface.setStatus("Log file erasted");
		}
		else
		{
			finInterface.setStatus("no change to log file");
		}
	}
	public static void add2ErrorLog(String message,String Exception)
	{
		if ("" == Exception)
			Exception = "none";

		try
		{
			logFile = new File(logFileName);
			
			if(logFile.exists())
			{
				BufferedWriter buf = new BufferedWriter(new FileWriter(logFileName, true));
				String user = System.getProperty("user.name","not specified");
				
				String txt;
				String errorColour = new String("bgcolor=#FF0000");
				String normalColour = new String("bgcolor=#FFFFFF");
				
				if(getError() == false)
				{
					txt = new String ("<TR><TH "+normalColour+">"+finToolBox.getTime()+"</TH><TH "+normalColour+">"+finToolBox.getDate()+"</TH><TH "+normalColour+">"+user+"</TH><TH "+normalColour+">"+message+"</TH><TH "+normalColour+">"+Exception+"</TH></TR>");
				}
				else
				{
					txt = new String ("<TR><TH "+errorColour+">"+finToolBox.getTime()+"</TH><TH "+errorColour+">"+finToolBox.getDate()+"</TH><TH "+errorColour+">"+user+"</TH><TH "+errorColour+">"+message+"</TH><TH "+errorColour+">"+Exception+"</TH></TR>");
				}

				buf.write(txt);
				
				buf.flush();
				buf.close();
			}
			else
			{
				buildlogFile();
		        
		        add2ErrorLog("Missing file "+logFileName,"");
		        add2ErrorLog(message,Exception);
			}
		}
		catch(FileNotFoundException null_ex)//file not found
		{
			setError("Missing file "+logFileName);
		}
		
		catch (Exception e3)
		{
			setError("reader file "+logFileName);
		
		}
	}
	public static void buildlogFile()
	{
		try
		{
			BufferedWriter buf = new BufferedWriter(new FileWriter(logFileName));
			buf.write("<body><div align=&quot;center&quot;>");		buf.newLine();//start of html
			buf.write("<h1>finShare v1 - Error log viewer</h1>");	buf.newLine();//titel
			buf.write("<TABLE width=100% border= 1  summary=&quot;This table gives the user info about the histry of the programe.&quot;>");		buf.newLine();//start of table
			buf.write("<TR><TD>Time</TD><TD>Date</TD><TD>User</TD><TD>message</TD><TD>System print out</TD></TR>");	buf.newLine();//table head
	
			buf.flush();
	        buf.close();
		}
		catch (Exception e3)
		{
			setError("reader file "+logFileName);
		}
	}
	public static boolean getError()
	{
		return error;
	}
	
	public static String getError_type()
	{
		return Error_type;
	}

	public static void setError()//call the error spoces
	{
		error = true;				// glode val
		finInterface.setStatus("");	// desplay the error
		finSound.playSound("Error_Big");// play an error sound
	}
	
	public static void setError(String txt_type)//set the error message to desplay for the user
	{
		Error_type = new String(txt_type);
		setError();
	}
}
