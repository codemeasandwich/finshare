 /*************************\
 *                         *
 *     FinShare v1.0       *
 * Brian Shannon X00022027 *
 * Brian McGreal X00002099 *
 *   3rd year Computing    *
 *    (ITT Dublin) 2006    *
 *                         *
 \*Class:networkInterface**/

package finNetwork;
 
import java.net.*;
import java.io.*;
//import java.util.zip.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

import finMain.*;
import finTools.*;
import finController.*;

public class networkInterface 
{
	private static boolean firstTime = false;
	
	private static MulticastSocket broadcastSocket;
	private static MulticastSocket chatBroadcastSocket;
	private static InetAddress group;
	private static InetAddress groupChat;
	private static ServerSocket socket4Listen2User = null;
	private static Socket incomingListen2User = null;
	private static Socket socket4connect2User_OUT = null;
	
	private static Thread ListenChatThread;
	private static Thread ListenThread;
	private static Thread Listen4connect;
	private static boolean connected = false;
	private static Crypt chatCrypt;
	
	//private static Thread 
	
//	-------------------------------------------------------------------------------------------
//	-------------------------------------------------------------------------------------------
	public static boolean getConnectedState()
	{
		return connected;
	}
	
	public static void setConnectedState(boolean onORoff)
	{
		connected = onORoff;
	}
	
	public static void first()//The chat port number is 1 added to the front of the broadcast
	{
		chatCrypt = new Crypt();
		 try
		 {
			 group = InetAddress.getByName(crossOverLayer2.getGroupIP());
			 
			 broadcastSocket = new MulticastSocket(crossOverLayer2.getPort());
			 chatBroadcastSocket = new MulticastSocket(crossOverLayer2.getChatPort());
			 
			 groupChat = InetAddress.getByName(crossOverLayer2.getgroupChatIP());
			 
			 broadcastSocket.joinGroup(group);
			 chatBroadcastSocket.joinGroup(groupChat);
		 }
		 catch (Exception e)
		 {
			 crossOverLayer2.Error("Asinging MulticastSockets",e.toString());
		 }
	}

//	connect2----------------------------------------------------------------------------------
//	-------------------------------------------------------------------------------------------
//	-------------------------------------------------------------------------------------------
	
	//public static void DownloadFromUser(String Hash,String TargetIP,String fileName)
	public static void DownloadFromUser(fileDescript file2Download,String IPofFiletoGet)
	{	
		String fileName = file2Download.getFilename();
		
		FileOutputStream fos;
		BufferedOutputStream out;
		BufferedInputStream in;
		try
		{
			int contine = 0;
			File DownloadFile = new File(finSetup.getDownloadpath()+"/"+fileName);
			do
			{
				if(DownloadFile.exists())
				{
									//    0    1      2         -1 = X
					Object[] options = {"Yes","No","Rename"};
					int n = JOptionPane.showOptionDialog(new JFrame(),"The file "+fileName+" already exists!\n" +
					"would you like to overwrite?","Already exists!",JOptionPane.CANCEL_OPTION,JOptionPane.WARNING_MESSAGE,null, options,options[0]);
					//System.out.println(n);
					//}
					if(0 == n)
					{
						//DownloadFile.delete();
						contine = 1;
					}
					else if(1 == n || -1 == n)
					{
						contine = 2;
					}
					else if(2 == n)
					{
						//System.out.println("fileName"+fileName);
						fileName = JOptionPane.showInputDialog("Plase enter the new name for the file.",fileName);
						if(null == fileName)
						{
							contine = 2;
						}
					//	System.out.println("fileName"+fileName);
						DownloadFile = new File(finSetup.getDownloadpath()+"/"+fileName);
						
					}
				}
				else
				{
					contine = 1;
				}
			}
			while(/*DownloadFile.exists() &&*/ 0 == contine);
		//	System.out.println("out");
			
			//DownloadFile.
			
			if(1 == contine)
			{
				//"File Name","User","Status","Progress","Down/Total","Time Remaining"
				JProgressBar Progress = new JProgressBar();
				
				Object[] Transfer =  new Object[]
				{fileName,IPofFiletoGet,Progress,"",""};
				
				finDownload_Panel.Download_TableModel.addRow(Transfer);
				
				InetAddress inet = InetAddress.getByName(IPofFiletoGet);
				//connect2User(inet,true);
				socket4connect2User_OUT = new Socket(inet,crossOverLayer2.getPortMessage());//.getPortDownload());//Download Error
				finPack down = new finPack("Download",file2Download.getID());
				ObjectOutputStream oos = new ObjectOutputStream(socket4connect2User_OUT.getOutputStream());
				oos.writeObject(down);
				//System.out.println("send request Download ("+fileName+")");
				
				oos.flush();
				oos.close();
				socket4connect2User_OUT.close();
				
				//-------------------------------------- Receve the file
				
				ServerSocket socket4connect2User_IN = new ServerSocket(crossOverLayer2.getPortDownload()); 
				Socket skt = socket4connect2User_IN.accept(); 
				crossOverLayer2.userMessager(fileName+" is been send..."); 
				/*FileOutputStream*/ fos = new FileOutputStream(/*lost.getPath().toString()+*/finSetup.getDownloadpath()+"/"+fileName+".finTemp"); 
				/*BufferedInputStream*/ in = new BufferedInputStream(/*new GZIPInputStream(*/skt.getInputStream()/*)*/); 
				//File lost = new File("."); //lost.getPath().toString(); 
		
				/*BufferedOutputStream*/ out = new BufferedOutputStream(fos); 
				int i = 0; 
				while ((i = in.read()) != -1) 
				{ 	
					out.write(i); 
				} 
				out.flush();
				out.close();
				in.close();
				fos.close();
				skt.close();
				
				socket4connect2User_IN.close();//the file is now saved was .finTemp -------------------
				
				File DownloadedFile = new File(finSetup.getDownloadpath()+"/"+fileName+".finTemp");
				
				if(DownloadFile.exists())//file with out .finTemp 
				{
					DownloadFile.delete();//Race condition
				}
				
				DownloadedFile.renameTo(new File(finSetup.getDownloadpath()+"/"+fileName));
				
				crossOverLayer2.userMessager(fileName+" has Downloaded!");
			}
			//else if()
			//{
				
			//}
			else
			{//System.out.println("2");
				crossOverLayer2.userMessager("Download aborted!");
			}
		}
		catch (Exception e)
		{
			//File finTempFile = new File(finSetup.getDownloadpath()+"/"+fileName+".finTemp");
			//finTempFile.deleteOnExit();
			//if(finTempFile.exists())
			//{
			//	System.out.print("exists");
			//	finTempFile.delete();
			//}
			//crossOverLayer2.Error("connect2User String Input",e.toString());
			crossOverLayer2.Error("error with the Download Stream",e.toString());
		}
	}
	
	public static void connect2User(InetAddress Address, boolean sendback)
	{
		try
		{
			socket4connect2User_OUT = new Socket(Address,crossOverLayer2.getPortMessage());
			
			finPack myList = new finPack(crossOverLayer2.requestFileList(),sendback);
			ObjectOutputStream oos = new ObjectOutputStream(socket4connect2User_OUT.getOutputStream());
			oos.writeObject(myList);
			oos.flush();
			oos.close();
			socket4connect2User_OUT.close();
		}
		catch (Exception e)
		{
			crossOverLayer2.Error("connect2User InetAddress Input",e.toString());
		}
	}
//	-------------------------------------------------------------------------------------------
//	-------------------------------------------------------------------------------------------

	private static void Listen4connect2()
	{
        try 
        {
        	socket4Listen2User = new ServerSocket(crossOverLayer2.getPortMessage());
        }
        catch (IOException e) 
        {
        	crossOverLayer2.Error("Could not listen on port:"+crossOverLayer2.getPort(),e.toString());
        }
        //------------------------------------------------------------------------------------------

        Listen4connect = new Thread(new Runnable()
		{
			public void run()
		    {
				try 
				{
					while(true)
					{
						incomingListen2User = socket4Listen2User.accept();
						DataInputStream dis = new DataInputStream(incomingListen2User.getInputStream());
						ObjectInputStream ois = new ObjectInputStream(dis);
						finPack incomingObject = (finPack)ois.readObject();
					  
						if(incomingObject.getType().equals("FileList"))
						{
						    crossOverLayer2.incomingFileList(incomingObject.getArrayList());
						    
						    if(incomingObject.getSendBackAList() == true)
						    {
						    	connect2User(incomingListen2User.getInetAddress(),false);
						    }
						}
						else if (incomingObject.getType().equals("Download"))
						{ 
						   String path = crossOverLayer2.requestHash2Path(incomingObject.getMessage());
					       	if(path.equals("") == false)
					       	{
					       		
					       		crossOverLayer2.userMessager("Request 4 dowload");
					       		
					       		Socket skt = new Socket(incomingListen2User.getInetAddress(),crossOverLayer2.getPortDownload()); 
					       		FileInputStream fis = new FileInputStream(path); 
					       		BufferedInputStream in = new BufferedInputStream(fis); 
					       		BufferedOutputStream out = new BufferedOutputStream(/*new GZIPOutputStream(*/skt.getOutputStream()/*)*/); 
					       		crossOverLayer2.userMessager("BufferedOutputStream... file");
					       		
					       		int f; //System.out.println("in.read() "+ in.read()); 
					       		while ((f = in.read()) != -1)
					       		{
					       			out.write(f);
					       		}
					       		crossOverLayer2.userMessager("file sent!");
					       		
					       		out.flush();
					       		out.close();
					       		in.close();
					       		fis.close();
					       		skt.close();
					       		//System.out.println("send Back a file");
					       	}
					        else
					        {
					        	crossOverLayer2.Error("illegal Download by remote User","illegal Download from:"+incomingListen2User.getInetAddress());
					        }
						}
						
						ois.close();
						dis.close();
						incomingListen2User.close();
						//Thread.sleep(1000);
					}
				  }
				  catch (IOException e) 
				  {
					  e.getCause();
					  crossOverLayer2.Error("Listen for connect with in a Thread - IO",e.toString());
				  }
				  catch(ClassNotFoundException e1)
				  {
				      crossOverLayer2.Error("Listen for connect with in a Thread - Class Not Found",e1.toString());
				  }
				  catch(Exception e2)
				  {
					  crossOverLayer2.Error("Listen for connect with in a Thread"+crossOverLayer2.getPort(),e2.toString());
				   }
				}
		});
		Listen4connect.start();
	}
	
//	Broadcast----------------------------------------------------------------------------------
//	-------------------------------------------------------------------------------------------
//	-------------------------------------------------------------------------------------------
	
	public static void startBroadcasts()//send out a broudcast
	{
		first();
		
		 try
		 {
			 String ver = crossOverLayer2.version();//"Hello from "+crossOverLayer2.getMyIP();
			 DatagramPacket hi = new DatagramPacket(ver.getBytes(), ver.length(), group, crossOverLayer2.getPort());
			 broadcastSocket.send(hi);//HAY IM ON THE NETWORK- be my friend!!
			 Listen4broadcasts();
			 Listen4Chat();
			 Listen4connect2();
		 }
		 catch (IOException e) 
		 {
			 crossOverLayer2.Error("Sending broadcast",e.toString());
		 }
	}
//	-------------------------------------------------------------------------------------------
//	-------------------------------------------------------------------------------------------
	private static void Listen4broadcasts()
	{
		ListenThread = new Thread(new Runnable()
		{
			public void run()
            {
	        	while(true)
	            {
	            	byte[] buf = new byte[crossOverLayer2.version().length()];
	                DatagramPacket incomingPacket = new DatagramPacket(buf, buf.length);
	           
	                try
	                {
	                	broadcastSocket.receive(incomingPacket);
	                	String incomingData = new String(incomingPacket.getData());
	                			
	                	if(incomingPacket.getAddress().equals(crossOverLayer2.getMyinetIP()) == false && incomingData.equals(crossOverLayer2.version()))
	                	{
	                		System.out.println(incomingData + incomingPacket.getAddress());
	                		crossOverLayer2.userMessager("Connecting to "+incomingPacket.getAddress()+"...");
	                		connect2User(incomingPacket.getAddress(),true);
	                		
	                	}
	                }
	                catch(Exception ex)
	                {
	                	crossOverLayer2.Error("Sending Listen for broadcasts",ex.toString());
	                } 	
	            }
            }});
		ListenThread.start();
	}

//	Chat---------------------------------------------------------------------------------------
//	-------------------------------------------------------------------------------------------
//	-------------------------------------------------------------------------------------------

	public static void sendChat(String text)//send out a broudcast
	{
		crossOverLayer2.incomingChat(text);

		if(crossOverLayer2.getEncrypState() == true)
		{
			text = chatCrypt.InCrypt(text);
			crossOverLayer2.userMessager("Chat message send crypted:"+text);
			text = text+"1";
		}
		else
		{
			text = text+"0";
			crossOverLayer2.userMessager("Chat message send");
		}

		 try
		 {
			 DatagramPacket hi = new DatagramPacket(text.getBytes(), text.length(), groupChat, crossOverLayer2.getChatPort());
			if(crossOverLayer2.getConnectedState() == true)
			{
				chatBroadcastSocket.send(hi);
			}
		 }
		 catch (IOException e) 
		 {
			 crossOverLayer2.Error("Sending Chatbroadcast",e.toString());
		 }
	}
	
//	-------------------------------------------------------------------------------------------
//	-------------------------------------------------------------------------------------------	

	private static void Listen4Chat()
	{
		ListenChatThread = new Thread(new Runnable()
        {
        	public void run()
            {
	        	while(true)
	            {
	            	byte[] buf = new byte[500];//nice and big
		        	DatagramPacket incomingPacket = new DatagramPacket(buf, buf.length);
		       
		        	try
		            { 
	                	chatBroadcastSocket.receive(incomingPacket);

	                	if(incomingPacket.getAddress().equals(crossOverLayer2.getMyinetIP()) == false)
		                {
		                	String text = new String(incomingPacket.getData()).trim();

		                	char textarray[] = text.toCharArray();
		                	if('1' == textarray[textarray.length-1])
		                	{
		                		text = chatCrypt.DeCrypt(textarray);
		                		textarray[textarray.length-1] = ' ';
		                	}
		                	else //if('0' == textarray[0])
		                	{
		                		textarray[textarray.length-1] = ' ';
		                	}

		                	crossOverLayer2.incomingChat(new String(textarray));
		                }
		            }
		            catch(Exception ex)
		            {
		            	crossOverLayer2.Error("Listen for Chat broadcasts",ex.toString());
		            }
	            }
            }});
		ListenChatThread.start();
	}
//	-------------------------------------------------------------------------------------------
//	-------------------------------------------------------------------------------------------
	
	public static void disconnect()
	{
		crossOverLayer2.setConnectedState(false);
        try
        {
        	chatBroadcastSocket.leaveGroup(groupChat);
        	broadcastSocket.leaveGroup(group);
        }
        catch(Exception ex)
        {
            crossOverLayer2.Error("Disconnect",ex.toString());
        }
	}
	
	public static void connect()
	{
		crossOverLayer2.setConnectedState(true);
		
		if(false == firstTime)
		{
			startBroadcasts();
		}
		else
		{
	        try
	        {
				chatBroadcastSocket.joinGroup(groupChat);
		        broadcastSocket.joinGroup(group);
	        }
	        catch(Exception ex)
	        {
	            crossOverLayer2.Error("else connect",ex.toString());
	        }
		}
	}
	
//	-------------------------------------------------------------------------------------------
//	-------------------------------------------------------------------------------------------
	
}
