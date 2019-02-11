package com.alexc.dungeon;

/**
*
* @author Alexandre Courtin and Lavayssiere Etienne
*/
public class Medic extends Character
{
	private int dx, dy, an;
	private int clock;
	private boolean heal;
	
	public Medic(int x, int y)
	{
		super(x, y, "npc_medic1.png", true, 60, 60);
		heal = false;
		clock = 0;
		dx = 0; dy = 0; an = 0;
	}
	
	public void think()
	{
		super.think();
		
		//ANIMATION
		an++;
		if (an >= 20) an = 0;
		
		//CAN HEAL?
		if (clock <= 0)
			{clock = 60; heal = false;}
		else if (clock == 1)
			{clock--; heal = true;}
		else
			{clock--; heal = false;}
		
		//MOVEMENT
		if (Common.random(0, 1) == 0) dx = 1; else dx = -1;
		if (Common.random(0, 1) == 0) dy = 1; else dy = -1;
		
		setX(getX() + (dx*Common.MEDIC_SPEED));
		setY(getY() + (dy*Common.MEDIC_SPEED));
	}
	
	public Sprite getSprite() 
	{
		//INIT SPRITE
		String res = "npc_medic1.png";
		
		//COUNT ANIMATION
		if (an >= 0 && an < 10) res = "npc_medic1.png";
		if (an >= 10 && an < 20) res = "npc_medic2.png";
		
		return new Sprite(getX()-(getWi()/2) + 50, getY()-(getHi()/2) + 50, 2, res, true);
	}
	
	public boolean canHeal() {return heal;}
	public String getId() {return "Medic";}
}
