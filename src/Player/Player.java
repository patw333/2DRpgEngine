package Player;
import java.util.Scanner;
import java.util.Vector;
import Game.*;

import Characters.*;
import Map.*;

public class Player {
	//The player's current party of characters
	public Vector<pChara> party;
	//The gameworld the player is in. CurrentMap holds the map the player is currently in
	public GameWorld World;
	//The coords of the player in the current GameWorld and GameMap;
	private int xCoord,yCoord;
	private Battle batoru;
	
	public Player() {
		party=new Vector<pChara>();
		World=new GameWorld();
		xCoord=0;
		yCoord=0;
		batoru=null;
	}
	
	public boolean addPlayer(pChara member) {
		if (party.size()<4){
			party.add(member);
			return true;
		}
		return false;
	}
	
	/**
	 * Add map to the world
	 * @param map
	 */
	public void addMap(GameMap map) {
		World.addMapToWorld(map);
	}
	
	public int getX() {
		return xCoord;
	}
	
	public int getY() {
		return yCoord;
	}
	
	/**
	 * @param x The value to move in the x direction
	 * @param y The value to move in the y direction
	 */
	public boolean move(int x,int y) {
		if(((xCoord+x)>=0)&& ((xCoord+x)<World.getCurrentMap().getWidth())) {
			if(((yCoord+y)>=0)&& ((yCoord+y)<World.getCurrentMap().getLength())) {
				xCoord+=x;
				yCoord+=y;
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * @throws InterruptedException 
	 * 
	 */
	public void triggerBattle() throws InterruptedException {
		//Add some crazy checks for map tile type and stuff later
		int gen= (int)(Math.random()*255);
		if(gen>200) {
			batoru=new Battle(party,World.generateEnemy());
			batoru.battleLoop(new Scanner(System.in));
		}
		
	}
	
	public void printMap() {
		for(int i=0;i<World.getCurrentMap().getLength();i++) {
			for(int j=0;j<World.getCurrentMap().getWidth();j++) {
				if((i==xCoord) && (j==yCoord)) {
					System.out.print("[o]");
				}
				else {
					System.out.print("[ ]");
				}
			}
			System.out.println("");
		}
		
	}
	
	
	
	/**
	 * Concept game loop
	 * @throws InterruptedException 
	 */
	public void gameLoop() throws InterruptedException {
		@SuppressWarnings("resource")
		Scanner scanner=new Scanner(System.in);
		int ok;
		while(true) {
		
		
		System.out.println("Current Location: "+World.getCurrentMap().getName()+": "+xCoord+","+yCoord);
		printMap();
		System.out.println("\nInput a command:");
		System.out.println("1) Move Left, 2) Move Right, 3) Move Down, 4) Move Up, 5) Quit");
		ok=scanner.nextInt();
		
		if(ok==1){
			loopMove("L");
		}
		else if(ok==2) {
			loopMove("R");
		}
		else if(ok==3) {
			loopMove("D");
		}
		else if(ok==4) {
			loopMove("U");
		}
		else if(ok==5) {
			System.out.println("Bye bye!");
			return;
		}
		else {
			System.out.println("what the fuck man\n");
		}
		}
	}
	
	
	
	public void loopMove(String dir) throws InterruptedException {
		int x=0;
		int y=0;
		
		if(dir=="L") {
			y=-1;
		}
		if(dir=="R") {
			y=1;
		}
		if(dir=="U") {
			x=-1;
		}
		if(dir=="D") {
			x=1;
		}
		
		if(move(x,y)) {
			System.out.println("Moved character to "+xCoord+","+yCoord+"\n");
			triggerBattle();
		}
		else {
			System.out.println("Unable to move to that location\n");
		}
	}
	

	
}
