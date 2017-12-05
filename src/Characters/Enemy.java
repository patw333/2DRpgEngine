package Characters;

import java.util.Vector;

public class Enemy extends Character {
	//Flag for determining if the enemy is a boss
	public boolean isBoss;
	
	/**
	 * Generic enemy constructor
	 */
	public Enemy(){
		name="Trash";
		level=1;
		hpMax=15;
		hpRem=15;
		atk=1;
		def=0;
		speed=0;
		isBoss=false;
		exp=10;
		skillSet=new Vector<String>();
		skillSet.add("Attack");
	}
	
	/**
	 * Actual Constructor for an enemy
	 * @param name2 Name
	 * @param level2 Level
	 * @param hp HP
	 * @param atk2 Attack
	 * @param def2 Defense
	 * @param speed2 Speed
	 * @param exp2 Experience given to the player after defeating this enemy
	 */
	public Enemy(String name2,int level2,int hp,int atk2,int def2,int speed2,int exp2) {
		name=name2;
		level=level2;
		hpMax=hp;
		hpRem=hp;
		atk=atk2;
		def=def2;
		speed=speed2;
		exp=exp2;
		isBoss=false;
		skillSet=new Vector<String>();
		skillSet.add("Attack");
	}
	
	/**
	 * Returns stats of enemy
	 * Add more as needed
	 * @return -1 if invalid stat, the corresponding stat otherwise
	 */
	public int getStat(String s) {
		if (s=="hpRem")
			return hpRem;
		if (s=="hpMax")
			return hpMax;
		if (s=="atk")
			return atk;
		if (s=="def")
			return def;
		if (s=="speed")
			return speed;
		return -1;
	}

}
