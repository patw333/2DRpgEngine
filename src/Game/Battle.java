package Game;
import Player.*;
import Map.*;
import Characters.*;
import Characters.Character;

import java.util.*;
import java.util.concurrent.TimeUnit;
/**
 * Battles take 2 vectors: The party and a vector of enemies.
 * Battles take the following steps:
 * @author pat
 *
 */
public class Battle {
	public Queue<Character> turnOrder;
	public Vector<Character> all;
	
	//The eventual party that is returned to update the player's party
	//Also used to display party stats easily
	public Vector<Character> retParty;
	public Vector<Character> enemyParty;
	public Vector<Character>dead;
	//Variables to help indicate when a battle ends
	
	/**
	 * Constructor for Battle, takes a vector of party members and a vector of enemies.
	 * @param party
	 * @param cohorts
	 */
	public Battle(Vector<pChara> party,Vector<Enemy> cohorts) {
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
		
		populateQueue(all);
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
	
	/**
	 * Removes a character from an existing vector and if it is a party member, we put them in the dead vector
	 * @param targetName
	 * @param vect
	 * @param isParty
	 */
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
			System.out.print(""+c.getName()+" -> ");
		}
		System.out.println(turnOrder.peek().getName()+"\n-----------------------\n");
	}
	
	/**
	 * Prints the vector for example purposes
	 * @param vect
	 */
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
	 * Returns whether or not the battle is over.
	 * @return True if battle is "over" false otherwise
	 */
	public boolean battleOver() {
		if((retParty.isEmpty())||(enemyParty.isEmpty())) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if the current turn guy is an enemy.
	 * @param name - name of enemy
	 * @return true if the character is an enemy
	 */
	public boolean isEnemy(String name) {
		for(int i=0;i<enemyParty.size();i++) {
			if(enemyParty.elementAt(i).getName()==name) {
				return true;
			}
		}
		
		return false;
	}
	
	
	/**
	 * Returns one of three end states for battle.
	 * @return
	 */
	public int victoryType() {
		//If the battle isnt over then return a 0;
		if (!battleOver()) {
			return 0;
		}
		//If the player party is dead, return 2
		if(!(retParty.isEmpty())) {
			return 2;
		}
		
		//If the enemy party is dead, return a 1
		if(!(enemyParty.isEmpty())) {
			return 1;
		}
		
		return 1;
	}
	
	/**
	 * Concept Battle loop
	 * Basically characters can do whatever they want at the moment, while enemies just die right away.
	 * @param scanner
	 * @throws InterruptedException 
	 */
	public void battleLoop(Scanner scanner) throws InterruptedException {
		//What the fuck lol
		System.out.println("__________         __    __  .__             _________ __                 __   \n" + 
				"\\______   \\_____ _/  |__/  |_|  |   ____    /   _____//  |______ ________/  |_ \n" + 
				" |    |  _/\\__  \\\\   __\\   __\\  | _/ __ \\   \\_____  \\\\   __\\__  \\\\_  __ \\   __\\\n" + 
				" |    |   \\ / __ \\|  |  |  | |  |_\\  ___/   /        \\|  |  / __ \\|  | \\/|  |  \n" + 
				" |______  /(____  /__|  |__| |____/\\___  > /_______  /|__| (____  /__|   |__|  \n" + 
				"        \\/      \\/                     \\/          \\/           \\/             ");
		//loop forever
		
		String curr;
		Character temp;
		int exp=0;
		while(!battleOver()) {
			printQueue();
			//Remove them from the queue
			temp=turnOrder.remove();
			
			System.out.println("It's "+temp.getName()+"'s turn!");
			
			if(!isEnemy(temp.getName())) {
				System.out.println("1) Arbitrary Action, 2) Nothing"); 
				int com=scanner.nextInt();
				if(com==1||com==2) {
					System.out.println(temp.getName()+" did nothing.");
				}
				
				//Add them back to the queue
				turnOrder.add(temp);
			}
			else {
				TimeUnit.SECONDS.sleep(1);
				System.out.println(temp.getName()+" just kinda died!");
				TimeUnit.SECONDS.sleep(1);
				removeFromVector(temp.getName(),enemyParty,false);
				exp+=temp.getExp();
			}
			
			if(battleOver()) {
				System.out.println("Your team got "+exp+" experience for doing nothing!\n");
			}
			
		}
		
	}
	
	
	
	/**
	 * Basic Attack
	 * @param attacker
	 * @param target The character being attacked, if their HP goes to 0, then
	 */
	//public void attack(Character attacker,Character target) {
		
	//}
	
	
	
	
	
}
