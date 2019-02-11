package com.alexc.dungeon;

/**
*
* @author Alexandre Courtin and Lavayssiere Etienne
*/
public class Common 
{
	final static String NAME = "SPACEBOY";
	final static int HEIGHT = 600; //HAUTEUR
	final static int WIDTH = 800; //LARGEUR
	
	final static int LIMIT_LEFT = 55;
	final static int LIMIT_RIGHT = 445;
	final static int LIMIT_UP = 50;
	final static int LIMIT_DOWN = 425;
	
	final static int PL_SPEED = 4;
	final static int MEDIC_SPEED = 1;
	final static int MONSTER_SPEED = 4;
	final static int LASER_SPEED = 7;
	final static double TICK_PER_SEC = 60D;
	final static int MAX_C = 5;
	
	final static boolean DEBUG_FPS = true;
	final static boolean DEBUG_PLAYER = false;
	final static boolean DEBUG_KEYBOARD = false;
	final static boolean DEBUG_MOUSE = true;
	
	public static int random(int min, int max)
    {
    	int rand = min + (int)(Math.random() * ((max - min) + 1));
    	return rand;
    }
}
