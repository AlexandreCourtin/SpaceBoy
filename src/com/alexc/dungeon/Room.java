package com.alexc.dungeon;

import java.util.ArrayList;

/**
*
* @author Alexandre Courtin and Lavayssiere Etienne
*/
public class Room 
{
	private Door north, south, east, west;
	private ArrayList<Item> items;
	private ArrayList<Character> characters;
	private boolean discover, treasure;
	
	public Room()
	{
		north = null; south = null; east = null; west = null;
		items = new ArrayList<Item>();
		characters = new ArrayList<Character>();
		discover = false; treasure = false;
	}
	
	public void addItem(Item i)
	{
		items.add(i);
	}
	
	public void addNPC(Character c)
	{
		characters.add(c);
	}
	
	public void addDoor(Door d, Room r, char c, int re)
	{
		if (c == 'n')
		{
			north = d;
			north.addRoom(this, 0);
			north.addRoom(r, 1);
			
			if (re == 0)
				r.addDoor(d, this, 's', 1);
		}
		else if (c == 's')
		{
			south = d;
			south.addRoom(this, 0);
			south.addRoom(r, 1);
			if (re == 0)
				r.addDoor(d, this, 'n', 1);
		}
		else if (c == 'w')
		{
			west = d;
			west.addRoom(this, 0);
			west.addRoom(r, 1);
			if (re == 0)
				r.addDoor(d, this, 'e', 1);
		}
		else if (c == 'e')
		{
			east = d;
			east.addRoom(this, 0);
			east.addRoom(r, 1);
			if (re == 0)
				r.addDoor(d, this, 'w', 1);
		}
	}
	
	public Door getDoor(char c)
	{
		if (c == 'n') return north;
		else if (c == 'e') return east;
		else if (c == 's') return south;
		else if (c == 'w') return west;
		
		return null;
	}
	
	public boolean openDoor(char c)
	{
		boolean open = false;
		
		if (north != null) 	if (north.isClosed() && north.getColor() == c)	{north.open(); open = true;}
		if (east != null) 	if (east.isClosed() && east.getColor() == c)	{east.open(); open = true;}
		if (south != null)	if (south.isClosed() && south.getColor() == c)	{south.open(); open = true;}
		if (west != null)	if (west.isClosed() && west.getColor() == c)	{west.open(); open = true;}
		
		return open;
	}
	
	public void isFinal() {treasure = true;}
	public boolean gotTreasure() {return treasure;}
	
	public void explore() {discover = true;}
	public boolean discovered() {return discover;}
	public ArrayList<Item> getItems() {return items;}
	public ArrayList<Character> getNPC() {return characters;}
}
