package com.alexc.dungeon;

import java.awt.event.*;

/**
*
* @author Alexandre Courtin and Lavayssiere Etienne
*/
public class Input extends KeyAdapter
{
	private boolean left = false, right = false, up = false, down = false, space = false;
	
	public Input(Engine eng)
	{
		//Adding Input system in the Engine core
		eng.addKeyListener(this);
	}
	
	public void keyPressed(KeyEvent e) 
	{
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT && !left)
        {
        	if (Common.DEBUG_KEYBOARD)
        		System.out.println("GAUCHE");
        	left = true;
        }

        if (key == KeyEvent.VK_RIGHT && !right) 
        {
        	if (Common.DEBUG_KEYBOARD)
        		System.out.println("DROITE");
        	right = true;
        }

        if (key == KeyEvent.VK_UP && !up) 
        {
        	if (Common.DEBUG_KEYBOARD)
        		System.out.println("HAUT");
        	up = true;
        }

        if (key == KeyEvent.VK_DOWN && !down) 
        {
        	if (Common.DEBUG_KEYBOARD)
        		System.out.println("BAS");
        	down = true;
        }
        
        if (key == KeyEvent.VK_SPACE && !space) 
        {
        	if (Common.DEBUG_KEYBOARD)
        		System.out.println("SPACE");
        	space = true;
        }
    }

    public void keyReleased(KeyEvent e) 
    {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT && left) 
        {
        	if (Common.DEBUG_KEYBOARD)
        		System.out.println("GAUCHE_");
        	left = false;
        }

        if (key == KeyEvent.VK_RIGHT && right) 
        {
        	if (Common.DEBUG_KEYBOARD)
        		System.out.println("DROITE_");
        	right = false;
        }

        if (key == KeyEvent.VK_UP && up)
        {
        	if (Common.DEBUG_KEYBOARD)
        		System.out.println("HAUT_");
        	up = false;
        }

        if (key == KeyEvent.VK_DOWN && down) 
        {
        	if (Common.DEBUG_KEYBOARD)
        		System.out.println("BAS_");
        	down = false;
        }
        
        if (key == KeyEvent.VK_SPACE && space) 
        {
        	if (Common.DEBUG_KEYBOARD)
        		System.out.println("SPACE_");
        	space = false;
        }
    }
    
    public boolean getl() {return left;} public boolean getr() {return right;} public boolean getu() {return up;} public boolean getd() {return down;}
    public boolean getSpace() {return space;}
}
