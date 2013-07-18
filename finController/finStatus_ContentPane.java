package finController;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import finTools.finError;

import java.awt.BorderLayout;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;

public class finStatus_ContentPane extends JPanel// implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private JLabel status_TreadCount = null;
	private int TreadCount = 0;
	private JLabel status = new JLabel("");
	
	public finStatus_ContentPane()
	{
		this.setLayout(new BorderLayout());
		this.add(status, BorderLayout.WEST);
		JLabel status_image = new JLabel();
		status_TreadCount = new JLabel();
		this.add(status_TreadCount, BorderLayout.CENTER);
		try//add a nice image to the bar
		{
			status_image.setIcon(new ImageIcon(getClass().getResource("/finTools/images/line.gif")));
		}
		catch (Exception ex)
		{
			finError.setError("image for status Bar not found");
			finError.add2ErrorLog(ex.toString());
		}
		this.add(status_image, BorderLayout.EAST);
		this.setBorder(new BevelBorder(BevelBorder.LOWERED));
		this.setToolTipText("Status Bar");
	}
	public void addTreadCount()
	{
		TreadCount++;
		status_TreadCount.setText("...Running Treads " + TreadCount+" ");
	}
	public void removeTreadCount()
	{
		TreadCount--;
		if(TreadCount>0)
		{
			status_TreadCount.setText("...Running Treads " + TreadCount+" ");
		}
		else
		{
			status_TreadCount.setText("");
		}
	}
	public void setStatus(String input)
	{
		status.setText(input);
	}
	
	//public void actionPerformed(ActionEvent e) 
	//{
		
	//}
}
