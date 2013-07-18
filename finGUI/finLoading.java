 /*************************\
 *                         *
 *     FinShare v1.0       *
 * Brian Shannon X00022027 *
 *   3rd year Computing    *
 *    (ITT Dublin) 2006    *
 *                         *
 \*Object:finLoading*******/

package finGUI;

import java.awt.*;
//import java.awt.BorderLayout;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import javax.swing.ImageIcon;
import java.awt.image.FilteredImageSource;

import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.JPanel;
import javax.swing.JLabel; 

import finTools.finError;

public class finLoading extends JWindow
{

	private static final long serialVersionUID = 1L;
	
	JLabel aphlaImage;
	JLabel finLogo;
	Image imagex;
	Robot Grrr;
	BufferedImage Screenimage;
	JProgressBar infanit;
	String tooltip;

	public finLoading()
	{
		this.setSize(new java.awt.Dimension(316,184));
		this.setLocationRelativeTo(null);
		
		tooltip = "finShare you made the right choice";

		Toolkit kit = Toolkit.getDefaultToolkit();
  		Dimension screenSize = kit.getScreenSize();
    	try
    	{
	  		Grrr = new Robot();
	  		Screenimage = Grrr.createScreenCapture(new Rectangle(screenSize));
    	}
		catch(Exception ex)
		{
			finError.setError("finLoading - Robot");
			finError.add2ErrorLog(ex.toString());
		}

  		imagex = createImage(new FilteredImageSource(Screenimage.getSource(), new CropImageFilter(this.getX(), this.getY(), this.getWidth(), this.getHeight())));

  		aphlaImage = new JLabel();
  		aphlaImage.setToolTipText("");
  		finLogo = new JLabel();
  		aphlaImage.setIcon(new ImageIcon(imagex));
  		JPanel Pane = new JPanel();
		Pane.setLayout(new GridBagLayout());
		
		GridBagConstraints gridBag = new GridBagConstraints();
		gridBag.gridx = 0;
		gridBag.gridy = 0;
		try
		{
			finLogo.setIcon(new ImageIcon(getClass().getResource("/finTools/images/finSharev2.gif")));
		}
		catch (Exception ex)
		{
			aphlaImage.setIcon(null);
			finLogo.setText("finLoading - Logo not found");
			finError.setError("Logo not found image not found");
			finError.add2ErrorLog(ex.toString());
		}
		Pane.add(finLogo,gridBag);
		gridBag.gridy = 1;
		infanit = new JProgressBar();
		infanit.setIndeterminate(true);
		infanit.setStringPainted(true);
		Pane.add(infanit, gridBag);
		gridBag.gridx = 0;
		gridBag.gridy = 0;
		gridBag.gridheight = 2;
		gridBag.fill = java.awt.GridBagConstraints.BOTH;
		//JPanel Aph = new JPanel();Aph.setOpaque(false);
		//Pane.add(Aph,gridBag);
		Pane.add(aphlaImage,gridBag);
		//this.set

  		this.setContentPane(Pane);
  		this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
  		this.setVisible(true);
	}
	public void loadingMassage(String text)
	{
		infanit.setString(text);
	}
	
	public String toString()
	{
		return "A loading Screen designed for finShare";
	}
	
}
