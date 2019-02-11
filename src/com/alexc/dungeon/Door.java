package com.alexc.dungeon;

/**
*
* @author Alexandre Courtin and Lavayssiere Etienne
*/
public class Door 
{
	private Room front, behind;
	private boolean closed;
	private char color;
	
	public Door()
	{
		front = null; behind = null;
		closed = false;
		color = 'o';
	}
	public Door(char c)
	{
		front = null; behind = null;
		closed = true;
		color = c;
	}
	
	public void open()
	{
		closed = false;
	}
	
	public void addRoom(Room r, int i)
	{
		if (i == 0)
			front = r;
		else
			behind = r;
	}
	
	public Room otherRoom(Room r)
	{
		if (front == r)
			return behind;
		else if (behind == r)
			return front;
		else
			return null;
	}
	
	public boolean isClosed() {return closed;}
	public char getColor() {return color;}
}