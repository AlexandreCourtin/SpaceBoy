package com.alexc.dungeon;

/**
*
* @author Alexandre Courtin and Lavayssiere Etienne
*/
public class Panel extends Item
{
	private int s;
	
	//THIS 'ITEM' IS FOR DECORATION PURPOSE ONLY
	
	public Panel(int x, int y, int s)
	{
		super(x, y, "panelmove.png", true, 2, 2, true, false);
		this.s = s;
		init();
	}
	
	private void init()
	{
		if (s == 0) setRes("panelmove.png");
		if (s == 1) setRes("panelrule.png");
		if (s == 2) setRes("panelshoot.png");
	}
	
	public String getId() {return "Panel";}
}
