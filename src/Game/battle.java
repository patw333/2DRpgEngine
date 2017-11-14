package Game;
import Player.*;
import Map.*;
import Characters.*;
import Characters.Character;

import java.util.*;
/**
 * Battles take 2 vectors: The party and a vector of enemies.
 * Battles take the following steps:
 * @author pat
 *
 */
public class battle {
	public Queue<Character> turnOrder;
	public Vector<Character> all;
	
	//The eventual party that is returned to update the player's party
	//Also used to display party stats easily
	public Vector<Character> retParty;
	public Vector<Character> enemyParty;
	public Vector<Character>dead;
	//Variables to help indicate when a battle ends
	
	public battle(Vector<pChara> party,Vector<Enemy> cohorts) {
		all=new Vector<Character>();
		
		//Vectors to be used through the battle for book keeping
		dead=new Vector<Character>();
		retParty=new Vector<Character>();
		enemyParty=new Vector<Character>();
		
		turnOrder=new LinkedList<>();
		//Add all elements from party and cohorts into "all"
		Character temp;
		for(int i=0;i<party.size();i++) {
			temp=party.elementAt(i);
			all.add(temp);
			retParty.add(temp);
			
			
		}
		Character temp2;
		for(int j=0;j<cohorts.size();j++) {
			temp2=cohorts.elementAt(j);
			all.add(temp2);
			enemyParty.add(temp2);
		}
	}
	
	/**
	 * Populates turnOrder with the characters in the proper order by speed.
	 * @param all
	 */
	public void populateQueue(Vector<Character>all) {
		Vector<Character>temp=all;
		while(temp.size()>0) {
			int index=maxSpeed(temp);
			turnOrder.add(temp.elementAt(index));
			temp.remove(index);
			//System.out.println(index);
		}
	}
	
	/**
	 * Returns the index of the character with the max speed
	 * Will optimize later, this is some hack job
	 * @param vec
	 * @return
	 */
	public int maxSpeed(Vector<Character> vec) {
		int maxSpeed=vec.elementAt(0).getStat("speed");
		int index=0;
		for(int i=0;i<vec.size();i++) {
			if (vec.elementAt(i).getStat("speed")>maxSpeed) {
				index=i;
				maxSpeed=vec.elementAt(i).getStat("speed");
			}
		}
		return index;
	}
	
	/**
	 * Remove a specific character by name from the queue and their corresponding Vector
	 * Used after a character is defeated in battle.
	 * @param targetName
	 */
	public void removeFromQueue(String targetName,boolean isPartyMember) {
		Queue<Character>temp=new LinkedList<>();
		for(Character c : turnOrder) {
			if(c.getName()!=targetName) {
				temp.add(c);
			}
		}
		turnOrder=temp;
		if(isPartyMember){
			//Remove the character from battle, and add to dead list
			removeFromVector(targetName,retParty,true);
		}
		else {
			
			removeFromVector(targetName,enemyParty,false);
		}
	}
	
	//Removes a character from a vector by name
	public void removeFromVector(String targetName,Vector<Character> vect,boolean isParty) {
		Character temp;
		for(int i=0;i<vect.size();i++) {
			temp=vect.elementAt(i);
			if(temp.getName()==targetName) {
				//Character is removed from the vector
				vect.remove(i);
				//Character is added to the dead list so they can be revived later
				if(isParty) {
					dead.addElement(temp);	
				}
				break;
			}
		}	
	}
	
	/**
	 * Prints the turn order Queue
	 */
	public void printQueue() {
		System.out.println("\n------------------------\nCurrent Turn Order:\n");
		for(Character c : turnOrder) {
			System.out.println(""+c.getName()+",Speed:"+c.getStat("speed"));
		}
		System.out.println("\nTotal: "+turnOrder.size()+" characters\n-----------------------\n");
	}
	
	public void printVector(Vector<Character> vect) {
		System.out.print("\n");
		Character temp;
		for(int i=0;i<vect.size();i++) {
			temp=vect.elementAt(i);
			System.out.println(temp.getName()+": Level "+temp.getLevel());
		}
		System.out.print("\n-------------------------------\n");
	}
	
	
	
	/**
	 * Basic Attack
	 * @param attacker
	 * @param target The character being attacked, if their HP goes to 0, then
	 */
	//public void attack(Character attacker,Character target) {
		
	//}
	
	
	
	
	
}
