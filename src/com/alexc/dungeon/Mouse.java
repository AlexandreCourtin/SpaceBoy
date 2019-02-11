package com.alexc.dungeon;

import java.awt.event.*;

/**
*
* @author Alexandre Courtin and Lavayssiere Etienne
*/
public class Mouse extends MouseAdapter
{
	private int x = 0, y = 0;
	private boolean clickl = false, clickr = false;
	
	public Mouse(Engine eng)
	{
		//Adding Mouse system in the Engine core
		eng.addMouseListener(this);
	}
	
	public void mousePressed(MouseEvent e)
	{
		int key = e.getButton();
		x = e.getX();
		y = e.getY();
		
		if (key == MouseEvent.BUTTON1) 
        {
        	if (Common.DEBUG_MOUSE && !clickl)
        		System.out.println("Left click at x: "+x+", y: "+y);
        	clickl = true;
        }
		
		if (key == MouseEvent.BUTTON3 && !clickr) 
        {
        	if (Common.DEBUG_MOUSE)
        		System.out.println("Right click at x: "+x+", y: "+y);
        	clickr = true;
        }
	}
	
	public void mouseReleased(MouseEvent e)
	{
		clickl = false;
		clickr = false;
	}
	
	public boolean getCl() {return clickl;} public boolean getCr() {return clickr;}
	public int getCx() {return x;} public int getCy() {return y;}
}
