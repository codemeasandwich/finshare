package finNetwork;

//import finGUI.Controller3;
import finTools.*;

import java.util.ArrayList;
import finController.*;

public abstract class finDownloadSet 
{
	private static fileVal pathLists;
	private static ArrayList pathList = new ArrayList();	//list of hash codes and there URLs
	//private static String IDofFiletoGet = null;				//hash code of the file
	private static String IPofFiletoGet = null;				//IP address of the PC with the file the user wante
	private static fileDescript file2Download;
	
	//public static String getIDofFiletoGet()
	//{
	//	return IDofFiletoGet;
	//}
	public static fileDescript getFileDescript()
	{
		return file2Download;
	}
	public static String getIPofFiletoGet()
	{
		return IPofFiletoGet;
	}
	public static void setDownload(fileDescript file/*,String fileName,String fileID*/,String IP)
	{
		file2Download = file;
		IPofFiletoGet = IP;
		finInterface.setDownload_ThisFile(file2Download.getFilename());
	}
	public static void nullDownload()
	{
		//IDofFiletoGet = null;
		IPofFiletoGet = null;
		file2Download = null;
		finInterface.setDownload_ThisFile("");
	}
	public static String rezivelHash2path(String ID)
	{
		int count = 0;
		boolean found = false;
		String path = new String();

		 while(count<pathList.size() && false == found)
		{
			 pathLists = (fileVal)pathList.get(count);
			if(pathLists.getHash().equals(ID))
			{
				 found = true;
				// System.out.println("FOUND!");
				 path = pathLists.getAddress();
			}
			else
			{
				count++;
			}
		}
		if(false == found)
		{
			//System.out.println("NOT FOUND!");
			path = "";
		}
			return path;
	}
	public static void upPathList()//(ArrayList temppathList)
	{
		pathList = finFileIndex.temppathList;
		fileVal.reSetCounter();
	}
}

