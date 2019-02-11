package com.alexc.dungeon;

import java.awt.Image;

import javax.swing.ImageIcon;

/**
*
* @author Alexandre Courtin and Lavayssiere Etienne
*/
public class Sprite 
{
	private int ix, iy, c;
	private boolean isVisible;
	private Image image;
	
	public Sprite(int ix, int iy, int c, String resFile, boolean isVisible)
	{
		this.ix = ix;
		this.iy = iy;
		this.c = c;
		this.isVisible = isVisible;
		
		ImageIcon i1 = new ImageIcon(this.getClass().getResource("res/"+resFile));
        image = i1.getImage();
	}

	public int getImageX() {return ix;}
	public void setImageX(int ix) {this.ix = ix;}
	public int getImageY() {return iy;}
	public void setImageY(int iy) {this.iy = iy;}
	public int getC() {return c;}
	public boolean isVisible() {return isVisible;}
	public void setVisible(boolean isVisible) {this.isVisible = isVisible;}
	public Image getImage() {return image;}
}
