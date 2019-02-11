package com.alexc.dungeon;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
*
* @author Alexandre Courtin and Lavayssiere Etienne
*/
public class Sound {
	public static Sound shoot = loadSound("res/shoot.wav");
	public static Sound mdeath = loadSound("res/mdeath.wav");
	public static Sound pickup = loadSound("res/pickup.wav");
	public static Sound moving = loadSound("res/moving.wav");
	public static Sound items = loadSound("res/items.wav");

	public static Sound loadSound(String fileName) 
	{
		Sound sound = new Sound();
		try 
		{
			AudioInputStream ais = AudioSystem.getAudioInputStream(Sound.class.getResource(fileName));
			Clip clip = AudioSystem.getClip();
			clip.open(ais);
			sound.clip = clip;
		} catch (Exception e) {
			System.out.println(e);
		}
		return sound;
	}

	private Clip clip;
	
	public void play() 
	{
		try 
		{
			if (clip != null) 
			{
				new Thread() 
				{
					public void run()
					{
						synchronized (clip) 
						{
							clip.stop();
							clip.setFramePosition(0);
							clip.start();
						}
					}
				}.start();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void init() {}
}