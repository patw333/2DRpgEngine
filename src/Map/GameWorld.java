package Map;

import java.util.*;

/**
 * The class that represents the world of the game
 * Handles transitions 
 * @author pat
 *
 */
public class GameWorld {
	/**
	 * Wondering If i should change this to a hashmap
	 */
	private Vector<GameMap> mapVect;
	
	/**
	 * Current GameMap so we can avoid having to get it every time
	 */
	private GameMap currentMap;
	
	/**
	 * Default cons that just makes the vector
	 */
	public GameWorld() {
		mapVect=new Vector<GameMap>();
		currentMap=null;
	}
	
	/**
	 * Attempts to find a map with the code inputted
	 * @param name
	 * @return The map with the corresponding name, else null if not found
	 */
	public GameMap findMap(String name) {
		for(int i=0;i<mapVect.size();i++) {
			if(mapVect.get(i).getName()==name) {
				return mapVect.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Adds a Map if it doesn't exist
	 * @param map
	 */
	public void addMapToWorld(GameMap map) {
		if(findMap(map.getName())==null) {
			mapVect.add(map);
			if(currentMap==null) {
				setMap(mapVect.firstElement());
			}
	}
	}
		
	
	public void setMap(GameMap mappu) {
		currentMap=mappu;
	}
	
	public GameMap getCurrentMap() {
		if(currentMap!=null) {
			return currentMap;
		}
		return null;
	}
		
	
	
	
	
}
	

