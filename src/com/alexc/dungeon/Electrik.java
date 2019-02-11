package com.alexc.dungeon;

/**
*
* @author Alexandre Courtin and Lavayssiere Etienne
*/
public class Electrik extends Character
{
	private int dx, dy, an;
	private int health;
	
	public Electrik(int x, int y)
	{
		super(x, y, "npc_electrik1.png", true, 60, 60);
		dx = 0; dy = 0; an = 0;
		health = 6;
	}
	
	public void think()
	{
		super.think();
		
		//ANIMATION
		an++;
		if (an >= 20) an = 0;
		
		//MOVEMENT
		if (Common.random(0, 1) == 0) dx = 1; else dx = -1;
		if (Common.random(0, 1) == 0) dy = 1; else dy = -1;
		
		setX(getX() + (dx*Common.MONSTER_SPEED));
		setY(getY() + (dy*Common.MONSTER_SPEED));
		
		//DEATH
		if (health <= 0) this.die();
	}
	
	public Sprite getSprite() 
	{
		//INIT SPRITE
		String res = "npc_electrik1.png";
		
		//COUNT ANIMATION
		if (an >= 0 && an < 5) res = "npc_electrik1.png";
		if (an >= 5 && an < 10) res = "npc_electrik2.png";
		if (an >= 10 && an < 15) res = "npc_electrik3.png";
		if (an >= 15 && an < 20) res = "npc_electrik4.png";
		
		return new Sprite(getX()-(getWi()/2) + 50, getY()-(getHi()/2) + 50, 2, res, true);
	}
	
	public void die() {Sound.mdeath.play(); super.die();}
	
	public void pain() {health--;}
	public String getId() {return "Electrik";}
}
