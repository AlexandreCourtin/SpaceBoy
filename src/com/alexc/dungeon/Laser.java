package com.alexc.dungeon;

/**
*
* @author Alexandre Courtin and Lavayssiere Etienne
*/
public class Laser extends Character 
{
	public Laser(int x, int y, int d)
	{
		super(x, y, "laser.png", true, 10, 10);
		setDi(d);
	}
	
	public void think()
	{
		//LIMIT BORDER
		if (getX() < Common.LIMIT_LEFT-20) 	this.die();
		if (getY() < Common.LIMIT_UP+10) 		this.die();
		if (getX() > Common.LIMIT_RIGHT+20) 	this.die();
		if (getY() > Common.LIMIT_DOWN+20) 	this.die();
				
		//MOVEMENT
			 if (getDi() == 1) setY(getY()-Common.LASER_SPEED);
		else if (getDi() == 2) setX(getX()+Common.LASER_SPEED);
		else if (getDi() == 3) setY(getY()+Common.LASER_SPEED);
		else if (getDi() == 4) setX(getX()-Common.LASER_SPEED);
	}
	
	public String getId() {return "Laser";}
}
