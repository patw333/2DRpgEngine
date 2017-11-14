package Game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Vector;

import Characters.Enemy;
import Characters.pChara;
import Player.Player;

public class Runner {

	public static void main(String[] args) {
		Player play=new Player();
		Vector<Enemy> meme=new Vector<Enemy>();
		
		//Add 2 Enemies and 4 Party Members
		meme.add(new Enemy());
		meme.add(new Enemy("Bob",1,2,3,4,5,6));
		play.addPlayer(new pChara("Djeeta",3,100,5,5,5,27));
		play.addPlayer(new pChara("Lyria",1,100,5,5,5,8));
		play.addPlayer(new pChara("Katalina",10,100,5,5,5,17));
		play.addPlayer(new pChara("Rackam",10,100,5,5,5,100));
		
		battle batoru=new battle(play.party,meme);
		//We then populate the Queue in the proper action order
		batoru.populateQueue(batoru.all);
		batoru.printQueue();
		
		//retParty and enemyParty Example
		System.out.println("Here is the current party:");
		batoru.printVector(batoru.retParty);
		System.out.println("Here are the enemies:");
		batoru.printVector(batoru.enemyParty);
		System.out.println("\nWe Kill off Rackam here:");
		batoru.removeFromQueue("Rackam",true);
		batoru.removeFromVector("Rackam", batoru.retParty, true);
		
		//Killing off a member
		System.out.println("And now he's gone from the queue and the party list");
		batoru.printQueue();
		batoru.printVector(batoru.retParty);
		
		//Showing they are dead so they can be brought back later
		System.out.println("Now he's in the dead people list");
		batoru.printVector(batoru.dead);
		
	}
}
