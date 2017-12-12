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
	private int expGained;
	//Variables to help indicate when a battle ends
	
	/**
	 * Constructor for Battle, takes a vector of party members and a vector of enemies.
	 * @param party
	 * @param cohorts
	 */
	public Battle(Vector<pChara> party,Vector<Enemy> cohorts) {
		all=new Vector<Character>();
		expGained=0;
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
	 * Activates a battle action
	 * @param user
	 * @param party whether or not the target is a party member
	 * @param type
	 * @param target
	 */
	public String battleAction(Character user, String type,boolean party,String target) {
		if(type=="Attack"||type=="Burn") {
			if(party) {
				for(int i=0;i<retParty.size();i++) {
					if(retParty.elementAt(i).getName()==target) {
						int damage=((user.atk*10)-retParty.elementAt(i).def);
						retParty.elementAt(i).hpRem-=damage;
						//System.out.println();
						if(retParty.elementAt(i).hpRem<=0) {
							//dead
							String res=retParty.elementAt(i).getName()+" was defeated!";
							removeFromQueue(target,true);
							removeFromVector(target,retParty,true);
							return res;
						}
						else {
							return retParty.elementAt(i).getName()+" took "+damage+" damage!";
						}
					}
				}
			}
			else {
				for(int i=0;i<enemyParty.size();i++) {
					if(enemyParty.elementAt(i).getName()==target) {
						int damage=((user.atk*5)-enemyParty.elementAt(i).def);
						enemyParty.elementAt(i).hpRem-=damage;
						
						if(enemyParty.elementAt(i).hpRem<=0) {
							//dead
							String res=enemyParty.elementAt(i).getName()+" was defeated!";
							expGained+=enemyParty.elementAt(i).getExp();
							removeFromQueue(target,false);
							removeFromVector(target,enemyParty,false);
							if(enemyParty.size()==0) {
									for(int j=0;j<retParty.size();j++) {
										if(retParty.elementAt(j).hpRem>0) {
											retParty.elementAt(j).exp+=expGained;
											if(retParty.elementAt(j).exp>=100){
												((pChara)retParty.elementAt(j)).levelUp();
												retParty.elementAt(j).exp-=100;
											}
										}
									}
							}
							return res;
							
							
						}
						else {
							return enemyParty.elementAt(i).getName()+" took "+damage+" damage!";
						}
					}
				}
			}
		}
		//Temporary placeholder just because
		if(type=="Heal") {
			return "But it failed!";
		}
		return "But it failed!";
		
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
		//What the f
		System.out.println("---------------------------------------------------\nBattle Starting\n---------------------------------------------------");
		//loop forever until win
		
		String curr;
		Character temp;
		int exp=0;
		while(!battleOver()) {
			printQueue();
			//Remove them from the queue
			temp=turnOrder.remove();
			
			System.out.println("It's "+temp.getName()+"'s turn!");
			
			if(!isEnemy(temp.getName())) {
				for(int i=0;i<temp.getSkills().size();i++) {
					System.out.print((i+1)+") "+temp.getSkills().elementAt(i)+" ");
				}
				 
				int com=scanner.nextInt();
				if(com-1>temp.getSkills().size()) {
					System.out.println(temp.getName()+" did nothing.");
				}
				else{
					System.out.println("Target?:\n 1) Party 2) Enemies");
					int partyChoice=scanner.nextInt();
					String target="";
					Vector<Character> tempV=new Vector<Character>();
					if(partyChoice==1){
					 tempV=retParty;
					}
					else if(partyChoice==2) {
					tempV=enemyParty;
					}
					
					for(int i=0;i<tempV.size();i++) {
							System.out.print((i+1)+")"+tempV.elementAt(i).getName()+" ");
						}
						int partyChoice2=scanner.nextInt();
						if((partyChoice2-1)<tempV.size()) {
						target=tempV.elementAt(partyChoice2-1).getName();
						
						}
					
			
					System.out.println(temp.getName()+" tried to "+temp.getSkills().elementAt(com-1)+" "+target+"!" );
					if(partyChoice==1) {
						battleAction(temp,temp.getSkills().elementAt(com-1),true,target);
						
					}
					else {
						battleAction(temp,temp.getSkills().elementAt(com-1),false,target);
						
					}
					
					
					TimeUnit.SECONDS.sleep(1);
				}
				
				//Add them back to the queue
				turnOrder.add(temp);
			}
			else {
				//enemy action
				TimeUnit.SECONDS.sleep(1);
				System.out.println(temp.getName()+" just kinda died!");
				TimeUnit.SECONDS.sleep(1);
				removeFromVector(temp.getName(),enemyParty,false);
				exp+=temp.getExp();
			}
			
			if(battleOver()) {
				//win
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
