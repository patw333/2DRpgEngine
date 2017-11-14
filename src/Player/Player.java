package Player;
import java.util.Vector;

import Characters.*;
import Map.*;

public class Player {
	//The player's current party of characters
	public Vector<pChara> party;
	//The gameworld the player is in. CurrentMap holds the map the player is currently in
	private GameWorld World;
	//The coords of the player in the current GameWorld and GameMap;
	private int xCoord,yCoord;
	
	public Player() {
		party=new Vector<pChara>();
		World=new GameWorld();
		xCoord=0;
		yCoord=0;
	}
	
	public boolean addPlayer(pChara member) {
		if (party.size()<4){
			party.add(member);
			return true;
		}
		return false;
	}
	
	/**
	 * Add map to the world
	 * @param map
	 */
	public void addMap(GameMap map) {
		World.addMapToWorld(map);
	}
	
	public int getX() {
		return xCoord;
	}
	
	public int getY() {
		return yCoord;
	}
	
	/**
	 * @param x The value to move in the x direction
	 * @param y The value to move in the y direction
	 */
	public boolean move(int x,int y) {
		if(((xCoord+x)>=0)&& ((xCoord+x)<World.getCurrentMap().getWidth())) {
			if(((yCoord+y)>=0)&& ((yCoord+y)<World.getCurrentMap().getLength())) {
				xCoord+=x;
				yCoord+=y;
				return true;
			}
		}
		return false;
	}
	
	
	
	
	
}
