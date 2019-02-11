package com.alexc.dungeon;

/**
*
* @author Alexandre Courtin and Lavayssiere Etienne
*/
public class LaserGun extends Item
{
	public LaserGun(int x, int y, boolean canPick)
	{
		super(x, y, "item_gun.png", true, 50, 50, canPick, true);
	}
	
	public String getId() {return "LaserGun";}
}
