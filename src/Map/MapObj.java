package Map;
/**
 * MapObject class for things like signs, NPCs
 * @author pat
 *
 */
public class MapObj {
	//May convert class to abstract later, but keep like this for now.
	/**
	 * objType denotes things like if it is an NPC, rock, or whatever
	 */
	private String objType;
	
	/**
	 * The description for when you inspect it.
	 */
	private String description;
	
	/**
	 *Default constructor for when im lazy 
	 */
	public MapObj() {
		objType="Default";
		description="This is a default map object";
	}
	
	/**
	 *Constructor for map objects 
	 * @param type
	 * @param desc
	 */
	public MapObj(String type, String desc) {
		objType=type;
		description=desc;
	}
	
	/**
	 * Copy Constructor
	 * @param other
	 */
	public MapObj(MapObj other) {
		objType=other.objType;
		description=other.description;
	}
	
	/**
	 *Getter for type 
	 * @return
	 */
	public String getType() {
		return objType;
	}
	
	/**
	 *Getter for description
	 * @return
	 */
	public String getDesc() {
		return description;
	}
	

}
