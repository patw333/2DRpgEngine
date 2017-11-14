package Testers;

import Characters.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class CharacterTester {

	@Test
	public void playerTest() {
		//Test the default constructor
		pChara player=new pChara();
		assertEquals("Person",player.getName());
		assertEquals(10,player.getStat("atk"));
	}
	
	@Test
	public void levelUpTest() {
		//Test the 
		pChara player2=new pChara("Djeeta",99,999,97,82,99,89);
		assertEquals(89,player2.getStat("speed"));
		player2.levelUp();
		assertTrue(player2.getLevel()==100);
		//Level ups can give 0 to stats so this will always be true
		assertTrue(player2.getStat("hpMax")>=999);
		assertTrue(player2.getStat("hpRem")>=999);
		assertTrue(player2.getStat("atk")>=97);
		assertTrue(player2.getStat("def")>=82);
		assertTrue(player2.getStat("luck")>=99);
		assertTrue(player2.getStat("speed")>=89);
		assertTrue(player2.getStat("FakeStat")==-1);
		assertTrue(player2.getExp()==(99*100));
	}
	
	@Test
	public void enemyController() {
		Enemy You=new Enemy();
		Enemy Kuribabylon= new Enemy("Kuribabylon", 1, 2, 3, 4, 5, 6);
		assertTrue(Kuribabylon.getLevel()==1);
		assertTrue(Kuribabylon.getStat("hpMax")==2);
		assertTrue(Kuribabylon.getStat("hpRem")==2);
		assertTrue(Kuribabylon.getStat("atk")==3);
		assertTrue(Kuribabylon.getStat("def")==4);
		assertTrue(Kuribabylon.getStat("speed")==5);
		assertTrue(Kuribabylon.getExp()==6);
		assertTrue(Kuribabylon.getStat("FakeStat")==-1);
	}

}
