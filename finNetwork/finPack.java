 /*************************\
 *                         *
 *     FinShare v1.0       *
 * Brian Shannon X00022027 *
 *   3rd year Computing    *
 *    (ITT Dublin) 2006    *
 *                         *
 \*Object:finPack**********/

package finNetwork;
import java.util.ArrayList;
import  java.io.Serializable;

public class finPack implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private ArrayList outGoingList;
	private boolean sendBackAList;
	private String message; 
	private String type;
	
	public finPack(String type,String message)
	{
		outGoingList = null;
		sendBackAList = false;
		this.type = type;
		this.message = message;
	}
	public finPack(ArrayList outGoingList, boolean sendBackAList)
	{
		this.outGoingList = outGoingList;
		this.sendBackAList = sendBackAList;
		type = "FileList";
		message = null;
	}
	
	public finPack(ArrayList outGoingList, boolean sendBackAList,String type,String message)
	{
		this.outGoingList = outGoingList;
		this.sendBackAList = sendBackAList;
		this.type = type;
		this.message = message;
	}
	
	public ArrayList getArrayList()
	{
		return outGoingList;
	}
	
	public boolean getSendBackAList()
	{
		return sendBackAList;
	}
	public String getType()
	{
		return type;
	}
	public String getMessage()
	{
		if(null != message)
		{
			return message;
		}
		else
		{
			return "";
		}
	}
	public String toString()
	{
		return "This is a FinPack Object";
	}
}
