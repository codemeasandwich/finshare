package finController;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import finMain.crossOverLayer2;
import finTools.finSound;
import finTools.finToolBox;

public class Tab_finChat extends JPanel implements ActionListener
{

	private static final long serialVersionUID = 1L;
	private JTextField ChatInput_jTextField = null;
	private static JEditorPane ChatOutput_jTextPane = null;
	private static String htm;
	private static String htmEnd;
	private static String chatText;
	private static String hostcolour = "#FF0000";
	
	private JButton Chat_jButton = null;
	private JButton chatButton_bold = null;
	private JButton chatButton_Italic = null;
	private JButton chatButton_underlined = null;
	private JButton chatButton_colour = null;
	
	public Tab_finChat()
	{
		this.setLayout(new GridBagLayout());
		this.setBorder(javax.swing.BorderFactory.createEmptyBorder(5,5,5,5));
		
		GridBagConstraints gridBag = new GridBagConstraints();
		gridBag.gridx = 4;
		gridBag.anchor = java.awt.GridBagConstraints.EAST;
		gridBag.gridy = 2;
		this.add(getChat_jButton(), gridBag);
		
		//GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
		gridBag.gridx = 3;
		gridBag.anchor = java.awt.GridBagConstraints.WEST;
		//gridBag.gridy = 2;
		this.add(getChatButton_colour(), gridBag);
		
		//GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
		gridBag.gridx = 2;
		//gridBag.anchor = java.awt.GridBagConstraints.WEST;
	//	gridBag.gridy = 2;
		this.add(getChatButton_underlined(), gridBag);
		
		//GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
		gridBag.gridx = 1;
		//gridBag.anchor = java.awt.GridBagConstraints.WEST;
	//	gridBag.gridy = 2;
		this.add(getChatButton_Italic(), gridBag);
		
		//GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
		gridBag.gridx = 0;
		//gridBag.anchor = java.awt.GridBagConstraints.WEST;
	//	gridBag.gridy = 2;
		this.add(getChatButton_bold(), gridBag);
		
		//GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
		gridBag.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBag.gridy = 1;
		gridBag.weightx = 1.0;
		gridBag.gridwidth = 5;
		//gridBag.gridx = 0;
		this.add(getChatInput_jTextField(), gridBag);
		
		//GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
		gridBag.fill = java.awt.GridBagConstraints.BOTH;
		gridBag.gridy = 0;
		//gridBag.weightx = 1.0;
		gridBag.weighty = 1.0;
		//gridBag.gridwidth = 5;
		//gridBag.gridx = 0;
		this.add(new JScrollPane(getChatOutput_jTextPane()), gridBag);
	}
	private JTextField getChatInput_jTextField() 
	{
		if (null == ChatInput_jTextField) 
		{
			ChatInput_jTextField = new JTextField();
			ChatInput_jTextField.setToolTipText("type in this box");
			ChatInput_jTextField.addActionListener(this);
		}
		return ChatInput_jTextField;
	}

	private JEditorPane getChatOutput_jTextPane() 
	{
		if (null == ChatOutput_jTextPane) 
		{
			ChatOutput_jTextPane = new JEditorPane();
			ChatOutput_jTextPane.setEditable(false);
			ChatOutput_jTextPane.setToolTipText("NeoHippyHo");
			ChatOutput_jTextPane.setContentType("text/html");//type of file to dispaly
			htm = "<html><head></head><body>";
			htmEnd = "</body></html>";
			chatText = "";
		}
		return ChatOutput_jTextPane;
	}
	private JButton getChat_jButton() 
	{
		if (null == Chat_jButton) 
		{
			Chat_jButton = new JButton("Chat");
			Chat_jButton.setToolTipText("Send chat message!");
			Chat_jButton.addActionListener(this);
		}
		return Chat_jButton;
	}
	public static void add2chat(String text)
	{
		//System.out.println("add2chat");
		chatText += text +"<br/";
		ChatOutput_jTextPane.setText(htm+chatText+htmEnd);
	}
	public static void sethostcolour(String colour)
	{
		hostcolour = colour;
	}
	private JButton getChatButton_bold() 
	{
		if (null == chatButton_bold) 
		{
			chatButton_bold = new JButton("B");
			chatButton_bold.addActionListener(this);
			chatButton_bold.setToolTipText("Bold");
			chatButton_bold.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
		}
		return chatButton_bold;
	}

	private JButton getChatButton_Italic() 
	{
		if (null == chatButton_Italic) 
		{
			chatButton_Italic = new JButton("I");
			chatButton_Italic.setToolTipText("Italic");
			chatButton_Italic.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD | java.awt.Font.ITALIC, 12));
			chatButton_Italic.addActionListener(this);
		}
		return chatButton_Italic;
	}

	private JButton getChatButton_underlined() 
	{
		if (null == chatButton_underlined) 
		{
			chatButton_underlined = new JButton("U");
			chatButton_underlined.setToolTipText("Underlined");
			chatButton_underlined.addActionListener(this);
		}
		return chatButton_underlined;
	}

	private JButton getChatButton_colour() 
	{
		if (null == chatButton_colour) 
		{
			chatButton_colour = new JButton("Colour");
			chatButton_colour.addActionListener(this);
			chatButton_colour.setToolTipText("Add some coloured text");
		}
		return chatButton_colour;
	}	
//	-----------------------------------------------------------------------------------

	public void actionPerformed(ActionEvent e) 
	{
		finSound.playSound("Click");
		if (e.getSource() == Chat_jButton || e.getSource() == ChatInput_jTextField)
		{
			crossOverLayer2.outgoingChat("<B>(<font color="+hostcolour+">"+finToolBox.loach_name()+"</font>)</B>"+ChatInput_jTextField.getText());
			//this.add2chat();
			ChatInput_jTextField.selectAll();
		}
		else if (e.getSource() == chatButton_colour)
		{
			Color txtColor = JColorChooser.showDialog(new JFrame(), "Choose Text Colour",Color.black);
		    if(txtColor != null)
		    {
			    String str = Integer.toHexString(txtColor.getRGB() & 0xFFFFFF );
			    ChatInput_jTextField.setText(ChatInput_jTextField.getText()+"<font color="+str+"> </font>");
		    }
		}
		else if (e.getSource() == chatButton_underlined)
		{
			ChatInput_jTextField.setText(ChatInput_jTextField.getText()+"<u> </u>");
		}
		else if (e.getSource() == chatButton_Italic)
		{
			ChatInput_jTextField.setText(ChatInput_jTextField.getText()+"<i> </i>");
		}
		else if (e.getSource() == chatButton_bold)
		{
			ChatInput_jTextField.setText(ChatInput_jTextField.getText()+"<b> </b>");
		}
	}
}
