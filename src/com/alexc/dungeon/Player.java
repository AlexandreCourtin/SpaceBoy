package com.alexc.dungeon;

/**
*
* @author Alexandre Courtin and Lavayssiere Etienne
*/
public class Player extends Character
{
	private int an, clock_hurt;
	private int life, energy;
	private Item[] items;
	private boolean isVisible, canShoot, hurt, win, dieOnce;
	
	public Player(int x, int y)
	{
		super(x, y, "pl_down1.png", true, 74, 60);
		items = new Item[4]; items[0] = null; items[1] = null; items[2] = null; items[3] = null;
		an = 0;
		life = 4;
		energy = 6;
		clock_hurt = 0;
		hurt = true;
		isVisible = true; canShoot = true; win = false; dieOnce = false;
	}
	
	public void think()
	{
		super.think();
		if (Common.DEBUG_PLAYER) System.out.println(getX()+", "+getY());
		
		//DAMAGE
		if (clock_hurt <= 0) {hurt = true; isVisible = true;}
		else clock_hurt--;
		
		//DAMAGE TICKHOE
		if (!this.isDead())
		{if (clock_hurt > 0 && clock_hurt <= 10) isVisible = false; if (clock_hurt > 10 && clock_hurt <= 20) isVisible = true;
		if (clock_hurt > 20 && clock_hurt <= 30) isVisible = false; if (clock_hurt > 30 && clock_hurt <= 40) isVisible = true;
		if (clock_hurt > 40 && clock_hurt <= 50) isVisible = false; if (clock_hurt > 50 && clock_hurt <= 60) isVisible = true;
		if (clock_hurt > 60 && clock_hurt <= 70) isVisible = false; if (clock_hurt > 70 && clock_hurt <= 80) isVisible = true;
		if (clock_hurt > 80 && clock_hurt <= 90) isVisible = false; if (clock_hurt > 90 && clock_hurt <= 100) isVisible = true;}
		
		//DEATH SOUND
		if (this.isDead() && !dieOnce)
		{
			Sound.mdeath.play();
			dieOnce = true;
		}
	}
	
	public void pain()
	{
		if (hurt && !this.isDead()) 
		{
			Sound.mdeath.play();
			setLife(getLife()-1); 
			clock_hurt = 100; 
			hurt = false;
		}
	}
	
	public void addItem(Item it)
	{
		if (Common.DEBUG_FPS) System.out.println("Ajout Item : "+it);
		
		for (int i = 0 ; i < 4 ; i++)
			if (items[i] == null)
			{
				items[i] = it;
				break;
			}
	}
	
	public int walkDoor()
	{
		if (getY() == Common.LIMIT_UP 		&& getX() >= 220 && getX() <= 280) return 1; //N
		if (getX() == Common.LIMIT_RIGHT 	&& getY() >= 220 && getY() <= 280) return 2; //E
		if (getY() == Common.LIMIT_DOWN 	&& getX() >= 220 && getX() <= 280) return 3; //S
		if (getX() == Common.LIMIT_LEFT 	&& getY() >= 220 && getY() <= 280) return 4; //W
		return 0;
	}
	
	public void useItem(int i, Room r)
	{
		if (items[i] != null)
		{
			if 		(items[i].getId().equals("Sword")) 												{}
			else if (items[i].getId().equals("EnergyPotion") && getEnergy() < 6)					{setEnergy(6); items[i] = null; Sound.items.play();}
			else if (items[i].getId().equals("HealthPotion") && getLife() < 4)						{setLife(4); items[i] = null; Sound.items.play();}
			else if (items[i].getId().equals("Key")) if (r.openDoor(((Key) items[i]).getColor()))	{items[i] = null; Sound.items.play();}
		}
	}
	
	public void dropItem(int i, Room r)
	{
		if (items[i] != null)
		{
			if 		(items[i].getId().equals("LaserGun")) 			r.addItem(new LaserGun(getX()-50, getY()-50, false));
			else if (items[i].getId().equals("EnergyPotion"))	r.addItem(new EnergyPotion(getX()-50, getY()-50, false));
			else if (items[i].getId().equals("HealthPotion"))	r.addItem(new HealthPotion(getX()-50, getY()-50, false));
			else if (items[i].getId().equals("Key"))			r.addItem(new Key(getX()-50, getY()-50, ((Key) items[i]).getColor(), false));
			
			items[i] = null;
		}
	}
	
	public void setD(int d, boolean b)
	{
		super.setD(d, b);
		
		if (b) an++;
		if (an >= 20) an = 0;
	}
	
	public Sprite getSprite() 
	{
		//INIT SPRITE
		String res = "pl_down1.png";
		int ani = 0;
		
		//COUNT ANIMATION
		if (an >= 0 && an < 10) ani = 1;
		if (an >= 10 && an < 20) ani = 2;
		
		//ANIMATION
		if (isDead())
			res = "pl_dead.png";
		else if (win)
			res = "pl_win.png";
		else
		{
			if 		(getDi() == 1) res = "pl_up"+ani+".png";
			else if (getDi() == 2) res = "pl_right"+ani+".png";
			else if (getDi() == 3) res = "pl_down"+ani+".png";
			else if (getDi() == 4) res = "pl_left"+ani+".png";
		}
		
		return new Sprite(getX()-(getWi()/2) + 50, getY()-(getHi()/2) + 50, 3, res, isVisible);
	}
	
	public boolean gotGun()
	{
		boolean b = false;
		if (items[0] != null) if (items[0].getId().equals("LaserGun")) b = true;
		if (items[1] != null) if (items[1].getId().equals("LaserGun")) b = true;
		if (items[2] != null) if (items[2].getId().equals("LaserGun")) b = true;
		if (items[3] != null) if (items[3].getId().equals("LaserGun")) b = true;
		return b;
	}
	
	public boolean gotTreasure()
	{
		boolean b = false;
		if (items[0] != null) if (items[0].getId().equals("Treasure")) b = true;
		if (items[1] != null) if (items[1].getId().equals("Treasure")) b = true;
		if (items[2] != null) if (items[2].getId().equals("Treasure")) b = true;
		if (items[3] != null) if (items[3].getId().equals("Treasure")) b = true;
		return b;
	}
	
	public void win() {win = true;}
	public int getLife() {return life;}
	public void setLife(int l) {life = l;}
	public int getEnergy() {return energy;}
	public void setEnergy(int e) {energy = e;}
	public boolean canShoot() {return canShoot;} public void canShoot(boolean b) {canShoot = b;}
	public Item[] getItems() {return items;}
	public String getId() {return "Player";}
}
