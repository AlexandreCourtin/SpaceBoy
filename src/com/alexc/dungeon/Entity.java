package com.alexc.dungeon;

/**
*
* @author Alexandre Courtin and Lavayssiere Etienne
*/
public abstract class Entity
{
	private int x, y, c;
	private int hi, wi;
	private String resFile;
	private boolean isVisible;
	
	public Entity(int x, int y, int wi, int hi, int c, String resFile, boolean isVisible)
	{
		this.x = x; this.y = y; this.c = c; this.resFile = resFile; this.isVisible = isVisible;
		this.hi = hi; this.wi = wi;
	}
	
	public void think() {}
	public void setD(int d, boolean b) {}
	
	public int getX() {return x;} public int getY() {return y;} public int getWi() {return wi;} public int getHi() {return hi;}
	public void setX(int x) {this.x = x;} public void setY(int y) {this.y = y;}
	public void addX(int dx) {this.x += dx;} public void addY(int dy) {this.y += dy;}
	public String getRes() {return resFile;} public void setRes(String res) {resFile = res;}
	public Sprite getSprite() {return new Sprite(x-(wi/2) + 50, y-(hi/2) + 50, c, resFile, isVisible);}
	public boolean getVisibility() {return isVisible;}
	public String getId() {return "ENTITY";}
}
