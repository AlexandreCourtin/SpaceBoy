package com.alexc.dungeon;

/**
*
* @author Alexandre Courtin and Lavayssiere Etienne
*/
public class HealthPotion extends Item
{
	public HealthPotion(int x, int y, boolean canPick)
	{
		super(x, y, "item_health.png", true, 50, 50, canPick, true);
	}
	
	public String getId() {return "HealthPotion";}
}
