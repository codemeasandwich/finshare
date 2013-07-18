package finTools;

//import inter_X00022027.*;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

public abstract class finSound //NEED TO B SO IT CANT BECUASE AN OBJECT!!!
{
	private static File sound_Start = null;
	private static File sound_Click = null;
	private static File sound_Download = null;
	private static File sound_Upload = null;
	private static File sound_Default = null; 
	private static File sound_Error_Small = null;
	private static File sound_Error_Big = null;
	
	private static boolean sound = false;
	
	private static AudioInputStream stream;
	
	public static void setSound(boolean ONorOFF)
	{
		sound = ONorOFF;
	}
	public static boolean getSound()
	{
		return sound;
	}

	public static void playSound(String Sound)//From my 2nd year project ^_^
	{
		if(true == sound)
		{
			try
			{
				stream = null;
				
				if(null == sound_Start)
				{
					sound_Start = 		new File("finTools/sounds/Start.wav");
					sound_Click = 		new File("finTools/sounds/Click.wav");
					sound_Download = 	new File("finTools/sounds/Download.wav");
					sound_Upload = 		new File("finTools/sounds/Upload.wav");
					sound_Default = 	new File("finTools/sounds/Default.wav");
					sound_Error_Small = new File("finTools/sounds/Error_Small.wav");
					sound_Error_Big = 	new File("finTools/sounds/Error_Big.wav");
				}
				 // Create a stream from the given file.
				
				if(Sound.equals("Click"))
				{
					stream = AudioSystem.getAudioInputStream(sound_Click);
				}

				else if(Sound.equals("Download"))
				{
					stream = AudioSystem.getAudioInputStream(sound_Download);
				}
				else if(Sound.equals("Upload"))
				{
					stream = AudioSystem.getAudioInputStream(sound_Upload);
				}
				else if(Sound.equals("Start"))//this only hapens ones
				{
					stream = AudioSystem.getAudioInputStream(sound_Start);
				}
				else if(Sound.equals("Error_Small"))
				{
					stream = AudioSystem.getAudioInputStream(sound_Error_Small);
				}
				else if(Sound.equals("Error_Big"))
				{
					stream = AudioSystem.getAudioInputStream(sound_Error_Big);
				}
				else
				{
					stream = AudioSystem.getAudioInputStream(sound_Default);
				}
				
			    // Open a data line to play our type of sampled audio.
			    // Use SourceDataLine for play and TargetDataLine for record.
				DataLine.Info info = null;
				info = new DataLine.Info(Clip.class, stream.getFormat()); 
				
				Clip clip = (Clip) AudioSystem.getLine(info);
				 clip.open(stream);      
				 clip.start();
			}
			catch (IOException e1)
			{
				setSound(false);
				finError.setError("Sound file not found");
				finError.add2ErrorLog(e1.toString());
			}
			catch (Exception e)// Throws IOException or UnsupportedAudioFileException
			{      
				setSound(false);
				finError.setError("Sound System");  
				finError.add2ErrorLog(e.toString());
			} 
		}
	}

}
