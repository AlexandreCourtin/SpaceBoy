package com.alexc.dungeon;

/**
*
* @author Alexandre Courtin and Lavayssiere Etienne
*/
public abstract class Character extends Entity
{	
	private int di;
	private boolean dead;
	
	public Character(int x, int y, String resFile, boolean isVisible, int hi, int wi)
	{
		super(x, y, wi, hi, 2, resFile, isVisible);
		di = 3;
		dead = false;
	}
	
	public void think()
	{
		//LIMIT BORDER
		if (getX() < Common.LIMIT_LEFT) setX(Common.LIMIT_LEFT);
		if (getY() < Common.LIMIT_UP) setY(Common.LIMIT_UP);
		if (getX() > Common.LIMIT_RIGHT) setX(Common.LIMIT_RIGHT);
		if (getY() > Common.LIMIT_DOWN) setY(Common.LIMIT_DOWN);
	}
	
	public void setD(int d, boolean b)
	{
		if (d == 1 && b) {addY(-Common.PL_SPEED); di = 	d;}
		if (d == 2 && b) {addX(Common.PL_SPEED); di = 	d;}
		if (d == 3 && b) {addY(Common.PL_SPEED); di = 	d;}
		if (d == 4 && b) {addX(-Common.PL_SPEED); di = 	d;}
	}
	
	public boolean isDead() {return dead;} public void die() {dead = true;}
	public void setDi(int d) {di = d;} public int getDi() {return di;}
	public String getId() {return "character";}
}
