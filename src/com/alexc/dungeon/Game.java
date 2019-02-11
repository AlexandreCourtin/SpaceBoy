package com.alexc.dungeon;

import java.util.ArrayList;

/**
*
* @author Alexandre Courtin and Lavayssiere Etienne
*/
public class Game 
{	
	private ArrayList<Sprite> sprites;
	private Room actualRoom;
	
	private Player pla;
	private int player_life, player_energy;
	private int clock_level, clock_black, clock_gui, black_level;
	private Item[] player_items;
	
	private boolean canMove, black_transition, win;
	
	public Game()
	{
		pla = new Player(250, 250);
		sprites = new ArrayList<Sprite>();
		player_life = 0; player_energy = 0; player_items = null;
		clock_level = -100; clock_black = 40; clock_gui = 0; black_level = 0; black_transition = false;
		canMove = true; win = false;
		init();
	}
	
	public void run(boolean l, boolean r, boolean u, boolean d, boolean space, boolean cl, boolean cr, int cx, int cy)
	{	
		think(l, r, u, d, space, cl, cr, cx, cy); //INPUT implementation
		render();
	}
	
	private void init()
	{
		//LEVEL CREATION
		Room rstart = new Room();
		Room r1 = new Room(); Room r2 = new Room();
		Room r3 = new Room(); Room r4 = new Room();
		Room r5 = new Room(); Room r6 = new Room();
		Room rfinal = new Room();
		actualRoom = rstart;
		
		//DOORS
		rstart.addDoor(new Door('r'), rfinal, 'n', 0); rstart.addDoor(new Door(), r1, 's', 0);
		r1.addDoor(new Door(), r2, 'e', 0); r1.addDoor(new Door(), r3, 'w', 0); r1.addDoor(new Door('g'), r4, 's', 0);
		r3.addDoor(new Door('b'), r5, 'w', 0);
		r4.addDoor(new Door(), r6, 'e', 0);
		
		//PANEL TUTO
		rstart.addItem(new Panel(0, 20, 0));
		rstart.addItem(new Panel(100, 225, 1));
		r2.addItem(new Panel(140, 160, 2));
		
		//POTIONS
		rstart.addItem(new EnergyPotion(400, 30, true)); rstart.addItem(new HealthPotion(5, 375, true));
		r5.addItem(new HealthPotion(400, 30, true));
		
		//KEY
		r3.addItem(new Key(10, 210, 'g', true));
		r5.addItem(new Key(10, 210, 'r', true));
		r6.addItem(new Key(300, 250, 'b', true));
		
		//LASERGUN
		r2.addItem(new LaserGun(360, 200, true));
		
		//TREASURE
		rfinal.addItem(new Treasure(200, 100));
		rfinal.isFinal();
		
		//CHARACTER
		r3.addNPC(new Electrik(200, 100)); r3.addNPC(new Electrik(200, 400));
		r5.addNPC(new Electrik(150, 100)); r5.addNPC(new Electrik(150, 400)); r5.addNPC(new Electrik(150, 250));
		r5.addNPC(new Electrik(200, 100)); r5.addNPC(new Electrik(200, 400)); r5.addNPC(new Electrik(200, 250));
		r4.addNPC(new Medic(200, 300));
		r6.addNPC(new Electrik(200, 100)); r6.addNPC(new Electrik(200, 400)); r6.addNPC(new Electrik(300, 250));
		//END OF THE LEVEL CREATION
		
		
		//INIT SOUND
		Sound.shoot.init(); Sound.mdeath.init(); Sound.pickup.init(); Sound.moving.init(); Sound.items.init();
	}
	
	private void think(boolean l, boolean r, boolean u, boolean d, boolean space, boolean cl, boolean cr, int cx, int cy)
	{
		actualRoom.explore();
		
		//MOVING
		if (u && canMove) pla.setD(1, true);
		if (r && canMove) pla.setD(2, true);
		if (d && canMove) pla.setD(3, true);
		if (l && canMove) pla.setD(4, true);
		if (!u) pla.setD(1, false);
		if (!r) pla.setD(2, false);
		if (!d) pla.setD(3, false);
		if (!l) pla.setD(4, false);
		
		//SHOOT
		if (space && pla.canShoot() && pla.gotGun()) {actualRoom.addNPC(new Laser(pla.getX(), pla.getY(), pla.getDi())); pla.canShoot(false);
													  Sound.shoot.play();}
		if (!space) pla.canShoot(true);
		
		//THINK
		pla.think();
		
		//TAKING ITEMS
		for (int j = 0 ; j < actualRoom.getItems().size() ; j++)
			if (pla.getX() <= actualRoom.getItems().get(j).getX() + 75 && pla.getX() >= actualRoom.getItems().get(j).getX() + 25)
				if (pla.getY() <= actualRoom.getItems().get(j).getY() + 75 && pla.getY() >= actualRoom.getItems().get(j).getY() + 25)
					if (actualRoom.getItems().get(j).takeable())
						takeItem(pla, actualRoom.getItems(), j);
		
		//USE ITEMS
		if (cl && cx >= 610 && cx <= 690 && cy >= 420 && cy <= 500 && canMove) pla.useItem(0, actualRoom);
		if (cl && cx >= 705 && cx <= 785 && cy >= 420 && cy <= 500 && canMove) pla.useItem(1, actualRoom);
		if (cl && cx >= 610 && cx <= 690 && cy >= 510 && cy <= 600 && canMove) pla.useItem(2, actualRoom);
		if (cl && cx >= 705 && cx <= 785 && cy >= 510 && cy <= 600 && canMove) pla.useItem(3, actualRoom);
		
		//DROP ITEMS
		if (cr && cx >= 610 && cx <= 690 && cy >= 420 && cy <= 500 && canMove) pla.dropItem(0, actualRoom);
		if (cr && cx >= 705 && cx <= 785 && cy >= 420 && cy <= 500 && canMove) pla.dropItem(1, actualRoom);
		if (cr && cx >= 610 && cx <= 690 && cy >= 510 && cy <= 600 && canMove) pla.dropItem(2, actualRoom);
		if (cr && cx >= 705 && cx <= 785 && cy >= 510 && cy <= 600 && canMove) pla.dropItem(3, actualRoom);
		
		//MEDIC HEAL
		for (int i = 0 ; i < actualRoom.getNPC().size() ; i++)
			if (actualRoom.getNPC().get(i).getId().equals("Medic"))
				if (pla.getX() <= actualRoom.getNPC().get(i).getX() + 200 && pla.getX() >= actualRoom.getNPC().get(i).getX() - 200)
					if (pla.getY() <= actualRoom.getNPC().get(i).getY() + 200 && pla.getY() >= actualRoom.getNPC().get(i).getY() - 200)
						if (((Medic) actualRoom.getNPC().get(i)).canHeal() && !pla.isDead())
						{
							if (pla.getLife() < 4)
								pla.setLife(pla.getLife() + 1);
							if (pla.getEnergy() < 6)
								pla.setEnergy(pla.getEnergy() + 1);
						}
		
		//GOING OTHER ROOM
		if (pla.walkDoor() > 0) 
			if (pla.walkDoor() == 1 && actualRoom.getDoor('n') != null)
				if (!actualRoom.getDoor('n').isClosed())
					changeRoom(pla, actualRoom.getDoor('n').otherRoom(actualRoom), pla.walkDoor());
		
			if (pla.walkDoor() == 2 && actualRoom.getDoor('e') != null)
				if (!actualRoom.getDoor('e').isClosed())
					changeRoom(pla, actualRoom.getDoor('e').otherRoom(actualRoom), pla.walkDoor());
			
			if (pla.walkDoor() == 3 && actualRoom.getDoor('s') != null)
				if (!actualRoom.getDoor('s').isClosed())
					changeRoom(pla, actualRoom.getDoor('s').otherRoom(actualRoom), pla.walkDoor());
			
			if (pla.walkDoor() == 4 && actualRoom.getDoor('w') != null)
				if (!actualRoom.getDoor('w').isClosed())
					changeRoom(pla, actualRoom.getDoor('w').otherRoom(actualRoom), pla.walkDoor());
		
		player_life = pla.getLife();
		player_energy = pla.getEnergy();
		player_items = pla.getItems();
		
		//WIN
		if (pla.gotTreasure())
		{
			canMove = false;
			win = true;
			pla.win();
		}
		
		//DEATH
		if (player_life <= 0)
		{
			canMove = false;
			pla.die();
		}
		
		//ITEMS THINK
		ArrayList<Item> items = actualRoom.getItems();
		for (int i = 0 ; i < items.size() ; i++)
			items.get(i).think();
		
		//NPC THINK
		ArrayList<Character> characters = actualRoom.getNPC();
		for (int i = 0 ; i < characters.size() ; i++)
			if (characters.get(i).isDead())
				characters.remove(i);
			else
				characters.get(i).think();
		
		//COLISION LASER/ELECTRIK
		for (int i = 0 ; i < characters.size() ; i++) for (int j = 0 ; j < characters.size() ; j++)
			if (characters.get(i).getId().equals("Electrik") && characters.get(j).getId().equals("Laser"))
				if (characters.get(j).getX()+5 <= characters.get(i).getX()+30 && characters.get(j).getX()-5 >= characters.get(i).getX()-30)
					if (characters.get(j).getY()+5 <= characters.get(i).getY()+30 && characters.get(j).getY()-5 >= characters.get(i).getY()-30)
						{characters.get(j).die();
						 ((Electrik) characters.get(i)).pain();}
		
		//COLISION PLAYER/ELECTRIK
		for (int i = 0 ; i < characters.size() ; i++)
			if (characters.get(i).getId().equals("Electrik"))
				if (pla.getX() <= characters.get(i).getX()+30 && pla.getX() >= characters.get(i).getX()-30)
					if (pla.getY() <= characters.get(i).getY()+30 && pla.getY() >= characters.get(i).getY()-30)
						pla.pain();
		
		//WIN GUI
		if (win || pla.isDead())
		{
			if (clock_gui <= 0) clock_gui = 50;
			else 				clock_gui--;
		}
		
		//BLACK SCREEN
		if (black_transition)
		{
			clock_black--;
			if (clock_black <= 40 && clock_black > 35) black_level = 1;
			else if (clock_black <= 35 && clock_black > 30) black_level = 2;
			else if (clock_black <= 30 && clock_black > 25) black_level = 3;
			else if (clock_black <= 25 && clock_black > 15) black_level = 4;
			else if (clock_black <= 15 && clock_black > 10) black_level = 3;
			else if (clock_black <= 10 && clock_black > 5) black_level = 2;
			else if (clock_black <= 5 && clock_black > 0) black_level = 1;
			
			if (clock_black == 0)
			{
				clock_black = 40;
				black_transition = false;
				black_level = 0;
			}
		}
	}
	
	private void takeItem(Player pl, ArrayList<Item> items, int i)
	{
		if (canMove)
			if (pl.getItems()[0] == null || pl.getItems()[1] == null || pl.getItems()[2] == null || pl.getItems()[3] == null)
				if (items.get(i).canBePick())
				{
					Sound.pickup.play();
					pl.addItem(items.get(i));
					items.remove(i);
				}
	}
	
	private void changeRoom(Player pla, Room r, int d)
	{
		if (clock_level == -100)
		{
			Sound.moving.play();
			clock_level = 25;
			black_transition = true;
			canMove = false;
		}
		else if (clock_level <= 10 && clock_level > -100)
		{
			clock_level = -100; black_level = 0;
			canMove = true;
			actualRoom = r;
			pla.setDi(d);
			if (pla.getEnergy() > 0) pla.setEnergy(pla.getEnergy() - 1);
			else					 pla.setLife(pla.getLife() - 1);
			
			if (d == 1) pla.setY(420);
			if (d == 2) pla.setX(60);
			if (d == 3) pla.setY(55);
			if (d == 4) pla.setX(440);
		}
		else
		{
			clock_level--;
		}
	}
	
	private void render()
	{	
		sprites.clear();  	//Erase all previous sprites
		renderGui(); 	  	//Render gui -> Health, Energy, Map, etc...
		renderWorld(); 	  	//Render the world -> Room, Doors, etc...
		renderEntities(); 	//Render entities -> Player, Monster, Items, etc...
	}
	
	private void renderWorld()
	{
		sprites.add(new Sprite(0, 0, 0, "area.png", true));
		
		//DOORS
		if (actualRoom.getDoor('n') != null) if (!actualRoom.getDoor('n').isClosed()) 
			sprites.add(new Sprite(260, 38, 1, "doorn.png", true));
		else sprites.add(new Sprite(260, 38, 1, "doorn"+actualRoom.getDoor('n').getColor()+".png", true));
		
		if (actualRoom.getDoor('e') != null) if (!actualRoom.getDoor('e').isClosed()) 
			sprites.add(new Sprite(508, 270, 1, "doore.png", true));
		else sprites.add(new Sprite(508, 270, 1, "doore"+actualRoom.getDoor('e').getColor()+".png", true));
		
		if (actualRoom.getDoor('s') != null) if (!actualRoom.getDoor('s').isClosed()) 
			sprites.add(new Sprite(260, 493, 1, "doors.png", true));
		else sprites.add(new Sprite(260, 493, 1, "doors"+actualRoom.getDoor('s').getColor()+".png", true));
		
		if (actualRoom.getDoor('w') != null) if (!actualRoom.getDoor('w').isClosed()) 
			sprites.add(new Sprite(12, 270, 1, "doorw.png", true));
		else sprites.add(new Sprite(12, 270, 1, "doorw"+actualRoom.getDoor('w').getColor()+".png", true));
	}
	
	private void renderGui()
	{
		sprites.add(new Sprite(600, 0, 0, "gui.png", true));
		
		// RENDER HEARTS
		if (player_life >= 1) sprites.add(new Sprite(610, 240, 1, "heart.png", true)); else sprites.add(new Sprite(610, 240, 1, "emptyheart.png", true));
		if (player_life >= 2) sprites.add(new Sprite(655, 240, 1, "heart.png", true)); else sprites.add(new Sprite(655, 240, 1, "emptyheart.png", true));
		if (player_life >= 3) sprites.add(new Sprite(700, 240, 1, "heart.png", true)); else sprites.add(new Sprite(700, 240, 1, "emptyheart.png", true));
		if (player_life >= 4) sprites.add(new Sprite(745, 240, 1, "heart.png", true)); else sprites.add(new Sprite(745, 240, 1, "emptyheart.png", true));
		
		// RENDER ENERGY
		if (player_energy >= 1) sprites.add(new Sprite(610, 310, 1, "energy.png", true)); else sprites.add(new Sprite(610, 310, 1, "emptyenergy.png", true));
		if (player_energy >= 2) sprites.add(new Sprite(655, 310, 1, "energy.png", true)); else sprites.add(new Sprite(655, 310, 1, "emptyenergy.png", true));
		if (player_energy >= 3) sprites.add(new Sprite(700, 310, 1, "energy.png", true)); else sprites.add(new Sprite(700, 310, 1, "emptyenergy.png", true));
		if (player_energy >= 4) sprites.add(new Sprite(610, 340, 1, "energy.png", true)); else sprites.add(new Sprite(610, 340, 1, "emptyenergy.png", true));
		if (player_energy >= 5) sprites.add(new Sprite(655, 340, 1, "energy.png", true)); else sprites.add(new Sprite(655, 340, 1, "emptyenergy.png", true));
		if (player_energy >= 6) sprites.add(new Sprite(700, 340, 1, "energy.png", true)); else sprites.add(new Sprite(700, 340, 1, "emptyenergy.png", true));

		//RENDER INVENTORY
		sprites.add(new Sprite(610, 420, 1, "inv_empty.png", true));
		sprites.add(new Sprite(705, 420, 1, "inv_empty.png", true));
		sprites.add(new Sprite(610, 510, 1, "inv_empty.png", true));
		sprites.add(new Sprite(705, 510, 1, "inv_empty.png", true));
		if (player_items[0] != null) sprites.add(new Sprite(625, 434, 2, player_items[0].getRes(), true));
		if (player_items[1] != null) sprites.add(new Sprite(720, 434, 2, player_items[1].getRes(), true));
		if (player_items[2] != null) sprites.add(new Sprite(625, 524, 2, player_items[2].getRes(), true));
		if (player_items[3] != null) sprites.add(new Sprite(720, 524, 2, player_items[3].getRes(), true));
		
		//RENDER MAP
		renderMap(actualRoom, 0, 0, 0);
		
		//RENDER YOU WIN AND GAME OVER
			 if (win && clock_gui > 20) 		 sprites.add(new Sprite(80, 100, 4, "youwin.png", true));
		else if (pla.isDead() && clock_gui > 20) sprites.add(new Sprite(80, 100, 4, "gameover.png", true));
		
		//RENDER BLACK SCREEN
		if (black_level > 0) sprites.add(new Sprite(0, 0, 4, "black"+black_level+".png", true));
	}
	
	private void renderMap(Room r, int x, int y, int re)
	{
		if (re == 0)
			sprites.add(new Sprite(x+695, y+110, 1, "actualroom.png", true));
		else if (r.gotTreasure())
			sprites.add(new Sprite(x+695, y+110, 1, "finalroom.png", true));
		else if (r.discovered())
			sprites.add(new Sprite(x+695, y+110, 1, "otherroom.png", true));
		
		if (r.getDoor('n') != null && re != 3)
			renderMap(r.getDoor('n').otherRoom(r), x, y-11, 1);
		
		if (r.getDoor('e') != null && re != 4)
			renderMap(r.getDoor('e').otherRoom(r), x+11, y, 2);
		
		if (r.getDoor('s') != null && re != 1)
			renderMap(r.getDoor('s').otherRoom(r), x, y+11, 3);
		
		if (r.getDoor('w') != null && re != 2)
			renderMap(r.getDoor('w').otherRoom(r), x-11, y, 4);
	}
	
	private void renderEntities()
	{
		//PLAYER
		if (pla.getVisibility())
			sprites.add(pla.getSprite());
		
		//ITEMS
		ArrayList<Item> items = actualRoom.getItems();
		for (int i = 0 ; i < items.size() ; i++)
			if (items.get(i).getVisibility())
				sprites.add(items.get(i).getSprite());
		
		//CHARACTERS
		ArrayList<Character> characters = actualRoom.getNPC();
		for (int i = 0 ; i < characters.size() ; i++)
			if (characters.get(i).getVisibility())
				sprites.add(characters.get(i).getSprite());
	}
	
	public ArrayList<Sprite> getSprites() {return sprites;}
}
