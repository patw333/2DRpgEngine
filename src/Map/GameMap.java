package Map;

import java.util.*;

public class GameMap {
	/**
	 * The 2x2 grid representing a game map
	 */
	private MapTile[][] tiles;
	/**
	 * The map codes of the maps if any that are connected to this one.
	 */
	private String mapName;
	private String nMap,sMap,wMap,eMap;
	private int mapWidth;
	private int mapLength;
	//Add a vector for monsters that appear
	
	/**
	 * Default Constructor, creates a 16x16 with no connections
	 * Also sets all tiles to "Field" Type
	 */
	public GameMap(){
		mapName="SampleMap";
		mapWidth=16;
		mapLength=16;
		tiles=new MapTile[mapWidth][mapLength];
		for (int i=0;i<16;i++) {
			for (int j=0;j<16;j++) {
				tiles[i][j]=new MapTile("Field",null);
			}
		}
	}
	
	/**
	 * Custom length creator
	 * @param w
	 * @param l
	 */
	public GameMap(String name,int w,int l) {
		mapName=name;
		mapWidth=w;
		mapLength=l;
		tiles=new MapTile[mapWidth][mapLength];
		for(int i=0;i<w;i++) {
			for(int j=0;j<l;j++) {
				tiles[i][j]=new MapTile("Field",null);
			}
		}
	}
	
	/**
	 * Adds maps connected to the current map in case the player reaches the edge of it.
	 * @param mapName The map name used to access it in GameWorld
	 * @param Dir The desired direction to be set
	 */
	public void addConnection(String mapName, String Dir) {
		if (Dir=="N") {
			nMap=mapName;
		}
		if (Dir=="S") {
			sMap=mapName;
		}
		if (Dir=="E") {
			eMap=mapName;
		}
		if (Dir=="W") {
			wMap=mapName;
		}
	}
	
	/**
	 * Same as above but getter
	 * @param Dir
	 * @return the map connected in the direct if any, else "Doesn't exist is returned";
	 */
	public String getConnection(String Dir) {
		String res=null;
		if (Dir=="N") {
			res= nMap;
		}
		if (Dir=="S") {
			res= sMap;
		}
		if (Dir=="E") {
			res= eMap;
		}
		if (Dir=="W") {
			res= wMap;
		}
		//**
		return res;
	}
	
	/**
	 * Getter for mapwidth
	 * @return map width
	 */
	public int getWidth() {
		return mapWidth;
	}
	
	/**
	 * Getter for mapLength
	 * @return map length
	 */
	public int getLength() {
		return mapLength;
	}
	
	/**
	 * Getter for name of map
	 * @return
	 */
	public String getName() {
		return mapName;
	}
	
}
