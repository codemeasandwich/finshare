package finNetwork;


import java.util.ArrayList;
import finController.*;
//import finGUI.finInterface;
import finTools.fileDescript;
import finTools.finSound;

public class finNetList 
{
	public static ArrayList netList = new ArrayList();			//all network users file are add to this!
	
	public static void add2netList(ArrayList newList)
	{
		fileDescript temp = (fileDescript)newList.get(0);
		int find = findNetList(temp.getID());
		
		if(find>=0)
		{
			netList.remove(find);
			netList.add(newList);
		}
		else
		{
			netList.add(newList);
			finInterface.add2IPs_ComboBox(temp.getID());//------add 2 network drop downlist
			
			//Play sound if new user
			finSound.playSound("");//play a sound when someone connects to the network
		}
	}
	
	public static ArrayList getUsersListFromNetList(int num)
	{
		return (ArrayList)netList.get(num);
	}
	public static int findNetList(String inputIP)
	{
		ArrayList tempArray  = new ArrayList();
		fileDescript tempfile;
		
		int i =0;
		boolean found = false;
		
		while(false == found && i<netList.size())
		{
			//System.out.println(netList.toString());
			tempArray = (ArrayList)netList.get(i);
			
			tempfile = (fileDescript)tempArray.get(0);
			if(tempfile.getID().equals(inputIP))
			{
				found = true;
			}
			else
			{
				i++;	
			}
		}
		if(false == found)
		{
			i = -1;
		}
		return i;
		
		
	}
}
