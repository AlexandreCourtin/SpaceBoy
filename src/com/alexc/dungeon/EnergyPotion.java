package com.alexc.dungeon;

/**
*
* @author Alexandre Courtin and Lavayssiere Etienne
*/
public class EnergyPotion extends Item
{
	public EnergyPotion(int x, int y, boolean canPick)
	{
		super(x, y, "item_energy.png", true, 50, 50, canPick, true);
	}
	
	public String getId() {return "EnergyPotion";}
}
