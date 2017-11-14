package Map;

/**
 */
public class MapTile {
	
	/**
	*Type of type like "Town","Field","Cave"
	*Determines if battles are capable of triggering on this tile.
	*/
	private String tileType;
	/**
	 *A map object, if any on this tile 
	 */
	private MapObj tileObj;
	
	/**
	 * Cons for Maptile
	 * @param type The desired type of this tile
	 * @param obj The object, if any, that is on this tile
	 */
	public MapTile(String type, MapObj obj) {
		tileType=type;
		tileObj=obj;
	}
	/**
	 * Getter for Type of the tile
	 * @return string containing the type.
	 */
	public String getType() {
		return tileType;
	}
	
	/**
	 * Set the type of tile it is
	 * @param t
	 */
	public void setType(String t) {
		tileType=t;
	}
	
	/**
	 * Getter for the object so interacting on the map is possible
	 * @return
	 */
	public MapObj getObj() {
		return tileObj;
	}
	
	
	/**
	 * Set the mapObj
	 * @param t
	 */
	public void setObj(MapObj other) {
		tileObj=new MapObj(other);
	}
	

}
