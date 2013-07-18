package finGUI;

import java.util.ArrayList;
import javax.swing.tree.DefaultMutableTreeNode;
//import finTools.fileDescript;
import finTools.*;
import finController.*;

public class finFileTreeNodes extends DefaultMutableTreeNode
{
	private static final long serialVersionUID = 1L;
	
	private ArrayList infoV2_guest = new ArrayList();
	private int infoV2_guest_counter = 0;
	private static int treeFiles = 0;
	private static int treeDirs = 0;
	private static long treeDeeped = 0;
	
	public finFileTreeNodes(ArrayList inputList,String Root)
	{
		super(Root);
	//	System.out.println(Root);
		infoV2_guest = inputList;

		fileDescript obj = (fileDescript)infoV2_guest.get(infoV2_guest_counter);
		
		String fol = new String();	
    	
    	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Folder
    	
    	for(infoV2_guest_counter = 1;infoV2_guest_counter<infoV2_guest.size();infoV2_guest_counter++)
        {
        	obj = (fileDescript)infoV2_guest.get(infoV2_guest_counter);
        	
    		if(obj.getType().equals("#"))
    		{
    			treeDirs++;
    			this.add(getJTree_Array_node());
    		}
        }
    	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Files

    	for(infoV2_guest_counter = 1;infoV2_guest_counter<infoV2_guest.size();infoV2_guest_counter++)
        {
        	obj = (fileDescript)infoV2_guest.get(infoV2_guest_counter);
        	
    		if(obj.getType().equals("#") == false)
    		{
    			treeDeeped += obj.getByteSize();
    			treeFiles++;
    			this.add(new DefaultMutableTreeNode(obj));
    		}
    		else// skip folders and there files
    		{
    			fol = obj.getID();
    			
				infoV2_guest_counter++;
				obj = (fileDescript)infoV2_guest.get(infoV2_guest_counter);
    			
				while((obj.getType().equals("##") == false) || (obj.getID().equals(fol) == false))//The God code!!! :*
				{
					infoV2_guest_counter++;
    				obj = (fileDescript)infoV2_guest.get(infoV2_guest_counter);
    			}
        }
      }
		
    	if(0 == treeDirs)
    	{
    		finTree_Panel.setFileTree_count("File List: "+treeFiles+" Files");
        	//treeFiles = 0;
    	}
        else
        {
        	finTree_Panel.setFileTree_count("File List: "+treeFiles+" Files, "+treeDirs+" Folders");
        	//treeDirs = 0;
        	//treeFiles = 0;
        }

    	//System.out.println(finToolBox.bytes2megabytes(treeDeeped));
    	finTree_Panel.setFileTree_type(finToolBox.bytes2megabytes(treeDeeped));
    	treeDeeped = 0;
    	treeDirs = 0;
    	treeFiles = 0;
    	infoV2_guest_counter = 0;
	}
	
	public DefaultMutableTreeNode getJTree_Array_node()//(ArrayList inputlist/*<fileDescript>*/)
	{
		DefaultMutableTreeNode root  = null;
		DefaultMutableTreeNode kid  = null;

		fileDescript obj = (fileDescript)infoV2_guest.get(infoV2_guest_counter);
		
		String fol = new String();		

	try//try to create node
	{
			root  = new DefaultMutableTreeNode (obj.getFilename());
			
			infoV2_guest_counter++;
			obj = (fileDescript)infoV2_guest.get(infoV2_guest_counter);
			
			if(obj.getType().equals("##"))
			{
    			kid = new DefaultMutableTreeNode("...");
    			root.add(kid);
			}//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< WORKS
			else//next file in the folder
			{
				int tempCounter = infoV2_guest_counter;//get back to start of folder
			
				//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
				
				//NOTE: on the 2nd Run -> ("##" != obj.getType()) will nerver be false
				
//				<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
				
				while("##" != obj.getType())//do//&& infoV2_guest_counter<infoV2_guest.size())
				{
					if(obj.getType().equals("#"))
					{
						//System.out.println("++++++++++++treeDirs");
						treeDirs++;
						root.add(getJTree_Array_node());
					}
					infoV2_guest_counter++;
					obj = (fileDescript)infoV2_guest.get(infoV2_guest_counter);
					/*
					if(false == ("##" == obj.getType()))
					{
						System.out.println("false:"+obj.getType());
					}
					else
					{
						System.out.println("true:"+obj.getType());
					}*/
					
				}//while(false == ("##" == obj.getType()));
				
				//System.out.println("OUT");
				
				infoV2_guest_counter = tempCounter;
				obj = (fileDescript)infoV2_guest.get(infoV2_guest_counter);
				
				while(obj.getType() != "##")
				{
					if(obj.getType().equals("#") == false)
					{
						treeDeeped += obj.getByteSize();
						treeFiles++;
						kid = new DefaultMutableTreeNode(obj);
						root.add(kid);
        			
					}
					else// skip folders and there files
					{
						fol = obj.getID();
						
						while((obj.getType().equals("##") == false) || (obj.getID().equals(fol) == false))
						{
							infoV2_guest_counter++;
							obj = (fileDescript)infoV2_guest.get(infoV2_guest_counter);
						}
        			
					}
					infoV2_guest_counter++;
					obj = (fileDescript)infoV2_guest.get(infoV2_guest_counter);
				}
        	}
	}
	//	}End of try
	
		catch(Exception ex)
		{
			finError.setError("trying to create node for File Tree");
			finError.add2ErrorLog(ex.toString());
			//System.out.println(ex.toString().printStackTrace());
			root = new DefaultMutableTreeNode("BAD Leaf!");
		}//End
		
		//infoV2_guest_counter = 0;
		return root;
	}
}
