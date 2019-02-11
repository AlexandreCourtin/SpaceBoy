package com.alexc.dungeon;

/**
*
* @author Alexandre Courtin and Lavayssiere Etienne
*/
public class Treasure extends Item
{
	public Treasure(int x, int y)
	{
		super(x, y, "item_treasure.png", true, 50, 50, true, true);
	}
	
	public String getId() {return "Treasure";}
}
