package Characters;

import java.util.Vector;

public abstract class Character {
	//Universal Stats, add as needed
	//Basic stats such as level,hp,atk,def,speed
	//Exp for pChara indicates exp gained, whereas enemies have it indicate exp given.
	public int hpMax,hpRem,atk,def,luck,speed,level,exp;
	public String name;
	public Vector<String> skillSet;
	
	/**
	 * Returns a specific stat based on the input
	 * Abstract because characters and enemies have unique attributes
	 * @param A
	 */
	public abstract int getStat(String A);
	
	
	/**
	 * Exp is used a lot so it gets its own getter
	 * @return exp of character
	 */
	public int getExp() {
		return exp;
	}
	
	/**
	 * same case as the exp
	 * @return
	 */
	public int getLevel() {
		return level;
	}
	
	/**
	 * Getter for Name
	 * @return String containing name of character
	 */
	public String getName() {
		return name;
	}
	
	public void addSkill(String skill) {
		skillSet.add(skill);
	}
	
	public Vector<String> getSkills(){
		return skillSet;
	}
	
}
