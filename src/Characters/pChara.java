package Characters;

import java.util.Vector;

/**
 * Playable Characters
 * @author pat
 *
 */
public class pChara extends Character{
	
	public pChara() {
		name="Person";
		level=1;
		hpMax=30;
		hpRem=30;
		atk=10;
		def=10;
		luck=5;
		speed=10;
		exp=0;
		skillSet=new Vector<String>();
		skillSet.add("Attack");
	}
	
	public pChara(String name2,int lv,int hp,int atk2,int def2,int luck2,int speed2) {
		name=name2;
		level=lv;
		hpMax=hp;
		hpRem=hp;
		atk=atk2;
		def=def2;
		luck=luck2;
		speed=speed2;
		//Temp value, make a better level equation later.
		exp=lv*100;
		skillSet=new Vector<String>();
		skillSet.add("Attack");
	}
	
	/**
	 * Random stat gains after level up
	 */
	public void levelUp() {
		level++;
		hpMax+=(int)(3*(Math.random()*10));
		atk+=(int)((Math.random()*4));
		def+=(int)((Math.random()*4));
		speed+=(int)((Math.random()*4));
		luck+=(int)((Math.random()*3));
	}
	
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
		if(s=="luck")
			return luck;
		return -1;
	}
	
	

}
