 /*************************\
 *                         *
 *     FinShare v1.0       *
 * Brian Shannon X00022027 *
 *   3rd year Computing    *
 *    (ITT Dublin) 2006    *
 *                         *
 \*Class:crossOverLayer2***/

package finMain;
 

import finController.*;
import finNetwork.networkInterface;
import finTools.*;
import finNetwork.*;

import java.net.InetAddress;
import java.util.ArrayList;

public class crossOverLayer2//"y" in char mases with the in cripshon
{
	private final static String ver = "fin_v2.0";
	private final static String[] verInfo= 
	{"FinShare v2.0.3 ~ Windows x86",
	"Desginer and programer -=Brian Shannon=-","",
	"FinShare is built with Java Technology"};
	
	private final static String finGroupIP = "224.224.10.10";
	private final static String finGroupChat = "224.224.10.11";
	
	private final static int port 			= 7700; //Broadcast
	private final static int portDownload 	= 7703;	//File
	private final static int portChat 		= 7701;	//Chat
	private final static int portMessage 	= 7702;	//finPack
	
	public static String version()
	{
		return ver;
	}
	public static String[] versionInformation()
	{
		return verInfo;
	}
	
	public static void connect()
	{
		networkInterface.connect();
	}
	
	public static void connect2(String input)
	{
		System.out.println("connect2");
	}
	
	public static void Disconnect()
	{
		networkInterface.disconnect();
	}
	
	public static void DownloadThisfile(fileDescript file2Download,String IPofFiletoGet)//client
	{
		networkInterface.DownloadFromUser(file2Download,IPofFiletoGet);
	}
	public static void outgoingChat(String text)
	{
		//incomingChat(text);
		networkInterface.sendChat(text);
	}
	public static void newFileList(ArrayList newfileList)
	{
		System.out.println("Send out New File List");
	}

//	ones U need to call--------------------------------------------------------DONT EDIT JUST CALL AS IS

	public static void incomingFileList(ArrayList fileList)//server
	{
		finNetList.add2netList(fileList);
	}

	public static ArrayList requestFileList()//client
	{
		return finFileIndex.getArrayList();
	}
	
	public static String requestHash2Path(String Hash)//client
	{
		return finDownloadSet.rezivelHash2path(Hash); //returns the path!! C:\...\...\..
	}
	
	public static void Error(String YourDescripcion, String javaException)
	{
		finError.setError(YourDescripcion);
		finError.add2ErrorLog(javaException);
	}
	
	public static int getPort()
	{
		return port;
	}
	public static int getChatPort()
	{
		return portChat;
	}
	
	public static int getPortDownload()
	{
		return portDownload;
	}
	
	public static int getPortMessage()
	{
		return portMessage;
	}
	
	public static InetAddress getMyinetIP()
	{
		return finToolBox.getinetIP();
	}
	
	public static String getGroupIP()
	{
		return finGroupIP;
	}
	public static String getgroupChatIP()
	{
		return finGroupChat;
	}
	
	public static String getMyIP()
	{
		return finToolBox.getIP();
	}
	
	public static boolean getEncrypState()//thats fun
	{
		return finSetup.getEncrypState();
	}
	public static void incomingChat(String text)
	{
		Tab_finChat.add2chat(text);
	}
	public static void userMessager(String text)
	{
		finInterface.setStatus(text);
	}
	
	public static boolean getConnectedState()
	{
		return networkInterface.getConnectedState();
	}
	public static void setConnectedState(boolean onOROff)
	{
		networkInterface.setConnectedState(onOROff);
	}
}
