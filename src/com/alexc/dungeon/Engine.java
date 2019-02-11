package com.alexc.dungeon;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;

/**
*
* @author Alexandre Courtin and Lavayssiere Etienne
*/
public class Engine extends Canvas implements Runnable
{
	private static final long serialVersionUID = 1L;
	
	private boolean tock = false;
	
	public static Game game;
	
	@SuppressWarnings("unused")
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private boolean running = false;
	
	private Input input = new Input(this);
	private Mouse mouse = new Mouse(this);
	
	public Engine()
	{
		game = new Game();
	}
	
	public void start()
	{
		running = true;
		new Thread(this).start();
	}
	
	public void stop()
	{
		running = false;
	}
	
	//Main core Engine
	//PS: Oui ce code vient de nous.
	public void run()
	{
		long lastTime = System.nanoTime();
		double unprocessed = 0;
		double nsPerTick = 1000000000D / Common.TICK_PER_SEC;
		int frames = 0;
		int ticks = 0;
		long lastTimer1 = System.currentTimeMillis();
		
		while (running)
		{
			long now = System.nanoTime();
			unprocessed += (now - lastTime)/nsPerTick;
			lastTime = now;
			boolean shouldRender = false;
			
			while (unprocessed >= 1)
			{
				ticks++;
				tick();
				unprocessed -= 1;
				shouldRender = true;
			}
			
			try 
			{
				Thread.sleep(2);
			} catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			
			if (shouldRender)
			{
				frames++;
				render();
			}
			
			if (System.currentTimeMillis()-lastTimer1 > 1000)
			{
				lastTimer1 += 1000;
				
				if (Common.DEBUG_FPS)
				{
					if (tock) 	{tock = false; System.out.println("----------------------");}
					else 	  	{tock = true;  System.out.println("------------------------");}
					
					System.out.println(frames+" FPS, "+ticks+" Ticks");
				}
				
				frames = 0;
				ticks = 0;
			}
		}
	}
	
	public void tick()
	{
		game.run(input.getl(), input.getr(), input.getu(), input.getd(), input.getSpace(), mouse.getCl(), mouse.getCr(), mouse.getCx(), mouse.getCy());
	}
	
	//Sprite render system
	public void render()
	{
		BufferStrategy bs = getBufferStrategy();
		
		if (bs == null)
		{
			createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D)g;
		
		ArrayList<Sprite> sprites = game.getSprites();
		
		for (int c = 0 ; c < Common.MAX_C ; c++)
			for (int i = 0 ; i < sprites.size() ; i++)
				if (sprites.get(i).isVisible() && sprites.get(i).getC() == c)
					g2d.drawImage(sprites.get(i).getImage(), sprites.get(i).getImageX(), sprites.get(i).getImageY(), this);
		
		g.dispose();
		bs.show();
		
	}
	
	public static void main(String[] args) 
	{
		Engine game = new Engine();
		game.setPreferredSize(new Dimension(Common.WIDTH-10, Common.HEIGHT-10));
		
		JFrame frame = new JFrame(Common.NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(game);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		game.start();
	}
}