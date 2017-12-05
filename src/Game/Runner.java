package Game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Vector;

import Characters.Enemy;
import Characters.pChara;
import Player.Player;
import Map.*;

public class Runner {

	public static void main(String[] args) throws InterruptedException {
		Player play=new Player();
		Vector<Enemy> meme=new Vector<Enemy>();
		
		//Add 2 Enemies and 4 Party Members
		meme.add(new Enemy());
		meme.add(new Enemy("Bob",1,2,3,4,5,6));
		play.addPlayer(new pChara("Djeeta",3,100,5,5,5,27));
		
		play.addPlayer(new pChara("Lyria",1,100,5,5,5,8));
		play.addPlayer(new pChara("Katalina",10,100,5,5,5,17));
		play.addPlayer(new pChara("Rackam",10,100,5,5,5,100));
		play.party.elementAt(3).addSkill("Heal");
		play.party.elementAt(3).addSkill("Burn");
		
		play.addMap(new GameMap());
		play.gameLoop();
		
	}
}
