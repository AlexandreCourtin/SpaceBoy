package com.alexc.dungeon;

/**
*
* @author Alexandre Courtin and Lavayssiere Etienne
*/
public abstract class Item extends Entity
{
	private boolean canPick, takeable, clockb;
	private int clock;
	
	public Item(int x, int y, String resFile, boolean isVisible, int hi, int wi, boolean canPick, boolean takeable)
	{
		super(x, y, wi, hi, 2, resFile, isVisible);
		clock = 0;
		this.canPick = canPick;
		clockb = canPick;
		this.takeable = takeable;
	}
	
	public void think()
	{
		//Pickable clock system for the drop fonction
		if (!clockb)
		{
			clock = 150;
			clockb = true;
		}
		
		if (clock > 0)
			clock--;
		else if (clock <= 0)
			canPick = true;
	}
	
	public boolean canBePick() {return canPick;} public boolean takeable() {return takeable;}
	public Sprite getSprite() {return new Sprite(getX()-(getWi()/2) + 100, getY()-(getHi()/2) + 100, 1, getRes(), getVisibility());}
}
