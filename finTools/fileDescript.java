 /*************************\
 *                         *
 *     FinShare v1.0       *
 * Brian Shannon X00022027 *
 *   3rd year Computing    *
 *    (ITT Dublin) 2006    *
 *                         *
 \*Object:fileDescript*****/

package finTools;

import java.io.Serializable;

public class fileDescript implements Serializable 
{		
	private static final long serialVersionUID = 1L;

	//[IP] [root path] 	[host name] ["Root"] [Port]
	//[ID] [description] [file name] [type]  [size]
	private String ID,filename,type,size;
	private long Byte;

	public fileDescript(String input_ID,String input_filename,String input_type,String input_size, long input_Byte)
	{
		ID = input_ID;
		if(input_type.equals("#") == false && input_type.equals("##") == false && input_type.equals("Root") == false)
			filename = finToolBox.subtract_FileExtension(input_filename,input_type);
		else
			filename = input_filename;
		
		size = input_size;
		type = input_type;//dir = #
		Byte = input_Byte;
	}
	
	public String getID()
	{
		return ID;
	}

	public String toString() 
	{
		if(getType().equals("#") == true)
			return filename;
		else
			return filename+"."+ type + " ("+size+")";
	}
	
	public String getFilename()
	{
		return filename;
	}
	public String getType()
	{
		return type;
	}
	
	public String getSize()
	{
		return size;
	}
	public long getByteSize()
	{
		return Byte;
	}
	
}
