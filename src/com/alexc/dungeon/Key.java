package com.alexc.dungeon;

/**
*
* @author Alexandre Courtin and Lavayssiere Etienne
*/
public class Key extends Item
{
	private char color;
	
	public Key(int x, int y, char color, boolean canPick)
	{
		super(x, y, "item_keyr.png", true, 50, 50, canPick, true);
		this.color = color;
		setRes("item_key"+color+".png");
	}
	
	public char getColor() {return color;}
	public String getId() {return "Key";}
}
