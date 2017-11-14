package Testers;

import Map.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class MapTest {

	@Test
	public void MapObjTests() {
		MapObj Uno=new MapObj();
		MapObj Tres=new MapObj("Rock","Smell my Cooking");
		MapObj Dos=new MapObj(Tres);
		assertEquals(Uno.getDesc(),"This is a default map object");
		assertEquals(Dos.getType(),Tres.getType());
		assertEquals("Rock",Tres.getType());
	}
	
	@Test
	public void MapTileTests() {
		MapObj sample=new MapObj("Rock","The people's champ");
		MapTile tile= new MapTile("Ring",sample);
		//Check the getObj method 
		assertEquals(tile.getObj().getType(),sample.getType());
		tile.setType("Cave");
		MapObj second=new MapObj("Meme","100 layers of Irony");
		assertEquals(tile.getType(),"Cave");
		tile.setObj(second);
		assertEquals(tile.getObj().getType(),"Meme");
	}
	
	@Test
	public void GameMapTests() {
		GameMap Mappy=new GameMap();
		assertEquals(16,Mappy.getWidth());
		assertEquals(null,Mappy.getConnection("N"));
		Mappy.addConnection("New Map", "S");
		assertEquals("New Map",Mappy.getConnection("S"));
		GameMap MappyAgain=new GameMap("World",100,100);
		assertEquals(MappyAgain.getName(),"World");
		assertEquals(100,MappyAgain.getLength());
		MappyAgain.addConnection("WestWorld", "W");
		assertEquals("WestWorld",MappyAgain.getConnection("W"));
		MappyAgain.addConnection("North", "N");
		assertEquals("North",MappyAgain.getConnection("N"));
		assertEquals(null,Mappy.getConnection("E"));
		MappyAgain.addConnection("2hu", "E");
		assertEquals("2hu",MappyAgain.getConnection("E"));
	}
	
	@Test
	public void GameWorldTest() {
		GameWorld world=new GameWorld();
		assertNull(world.getCurrentMap());
		assertNull(world.findMap("Blah"));
		world.addMapToWorld(new GameMap("TestWorld",10,10));
		//Duplicate adding test
		world.addMapToWorld(new GameMap("TestWorld",15,15));
		assertEquals(world.findMap("TestWorld").getLength(),10);
		//Adding a second world
		world.addMapToWorld(new GameMap("World2",20,20));
		assertEquals(world.findMap("TestWorld").getName(),"TestWorld");
		assertEquals(world.getCurrentMap().getName(),"TestWorld");
		assertNotNull(world.findMap("World2"));
		
	}
	
	

}
