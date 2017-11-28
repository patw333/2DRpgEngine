package Testers;

import Game.*;
import Player.*;
import Map.*;
import Characters.*;
import static org.junit.Assert.*;
import java.util.*;
import org.junit.Test;
import Map.*;

public class PlayerTester {

	@Test
	public void BasicMovement() {
		Player play=new Player();
		assertEquals(0,play.getX());
		assertEquals(0,play.getY());
		play.addMap(new GameMap("Whatever",4,4));
		assertTrue(!play.move(5, 0));
		assertTrue(play.move(2, 2));
	}
	
	@Test
	public void addPartyMembers() {
		Player play=new Player();
		assertTrue(play.addPlayer(new pChara("Gran",1,100,5,5,5,5)));
		assertTrue(play.addPlayer(new pChara("Lyria",1,100,5,5,5,5)));
		assertTrue(play.addPlayer(new pChara("Katalina",1,100,5,5,5,5)));
		assertTrue(play.addPlayer(new pChara("Rackam",1,100,5,5,5,5)));
		assertTrue(!(play.addPlayer(new pChara("Eugen",1,100,5,5,5,5))));
	}
	
	@Test
	public void battleTester() {
		Player play=new Player();
		Vector<Enemy> meme=new Vector<Enemy>();
		meme.add(new Enemy());
		meme.add(new Enemy("Bob",1,2,3,4,5,6));
		assertTrue(play.addPlayer(new pChara("Gran",1,100,5,5,5,7)));
		assertTrue(play.addPlayer(new pChara("Lyria",1,100,5,5,5,8)));
		assertTrue(play.addPlayer(new pChara("Katalina",1,100,5,5,5,9)));
		assertTrue(play.addPlayer(new pChara("Rackam",1,100,5,5,5,10)));
		Battle batoru=new Battle(play.party,meme);
		
		
		
		
	}

}
