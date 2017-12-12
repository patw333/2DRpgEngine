package Game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.PaintEvent;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import Characters.Enemy;
import Characters.pChara;
import Map.GameMap;
import Player.Player;
 
 
public class GameGUI{
 
	private Player play;
	private Battle batoru;
	private boolean inBattle,partyTarget,anime;
	private String battleText,battleText2;
	private String battlePhase,command,target;
	private JPanel panel;
	private Characters.Character currChar;
	private int expGained;
	
    public GameGUI(){
    	play=new Player();
    	play.addPlayer(new pChara("Djeeta",3,1000,50,5,5,27));
		play.addPlayer(new pChara("Lyria",1,1000,50,5,5,8));
		play.addPlayer(new pChara("Katalina",10,1000,5,5,5,17));
		play.addPlayer(new pChara("Rackam",10,5000,5,5,5,100));
		play.party.elementAt(3).addSkill("Heal");
		play.party.elementAt(3).addSkill("Burn");
		expGained=0;
    	batoru=null;
    	currChar=null;
    	inBattle=false;
    	partyTarget=false;
    	anime=false;
    	battleText="";
    	battleText2="";
    	battlePhase="";
    	command="";
    	target="";
    	
   
    	GameMap defMap=new GameMap();
    	GameMap secMap=new GameMap("Map2",8,8);
		defMap.getTiles()[3][4].setType("Grey");
		defMap.getTiles()[3][5].setType("Grey");
		defMap.getTiles()[6][7].setType("Nice");
		defMap.getTiles()[5][7].setType("Nice");
    	defMap.addConnection("Map2", "W");
    	secMap.addConnection("SampleMap", "E");
    	play.addMap(defMap);
    	play.addMap(secMap);
    	
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e) {
            //silently ignore
        }
        JFrame window = new JFrame("Basic RPG GUI");
        window.setSize(800, 600);

        
        panel=new JPanel(){ 
        	
        	
        	public void paintGrid(Graphics g) {
        		GameMap curr=play.World.getCurrentMap();

        		/*
        		for(int i=0;i<curr.getLength();i++) {
        			for(int j=0;j<curr.getWidth();j++) {
        				//g.drawRect(200+(i*50),j*50,50,50);
        				if(curr.getTiles()[i][j].getType()=="Field") {
        					//Check for types then fill apropriately or draw image
        					//Depends on type really.
        					g.setColor(Color.GREEN);
        				}
        				if(curr.getTiles()[i][j].getType()=="Grey") {
        					g.setColor(Color.DARK_GRAY);
        				}
        				
        				if(curr.getTiles()[i][j].getType()=="Nice") {
        					g.setColor(Color.MAGENTA);
        				}
        				g.fillRect(201+(i*50),1+j*50,48,48);
    					g.setColor(Color.BLACK);
        			}
        		}
        		*/
        		int x=play.getX();
        		int y=play.getY();
        		g.setColor(Color.WHITE);
        		for(int i=0;i<4;i++) {
        			for(int j=0;j<4;j++) {
        				if(((x-i)>=0)&&(y-j)>=0) {
        					g.drawRect(200+((x-i)*50),(y-j)*50,50,50);
        					fillTile(g,x-i,y-j,curr);
        				}
        				if(((x+i)<curr.getLength())&&(y-j)>=0) {
        					g.drawRect(200+((x+i)*50),(y-j)*50,50,50);
        					fillTile(g,x+i,y-j,curr);
        				}
        				if(((x-i)>=0)&&(y+j)<curr.getWidth()) {
        					g.drawRect(200+((x-i)*50),(y+j)*50,50,50);
        					fillTile(g,x-i,y+j,curr);
        				}
        				if(((x+i)<curr.getLength())&&(y+j)<curr.getWidth()) {
        					g.drawRect(200+((x+i)*50),(y+j)*50,50,50);
        					fillTile(g,x+i,y+j,curr);
        				}
        				
        				
        			}
        		}
        		g.setColor(Color.BLACK);
    			paintChara(g);
        	}
        	
        	public void fillTile(Graphics g,int i,int j,GameMap curr) {
    			if(curr.getTiles()[i][j].getType()=="Field") {
					//Check for types then fill apropriately or draw image
					//Depends on type really.
					g.setColor(Color.GREEN);
				}
				if(curr.getTiles()[i][j].getType()=="Grey") {
					g.setColor(Color.DARK_GRAY);
				}
				
				if(curr.getTiles()[i][j].getType()=="Nice") {
					g.setColor(Color.MAGENTA);
				}
				if(curr.getTiles()[i][j].getType()=="Water") {
					g.setColor(Color.BLUE);
				}
				g.fillRect(201+(i*50),1+j*50,48,48);
				g.setColor(Color.BLACK);
	
        	}
        	
        	public void paintChara(Graphics g) {
        		g.fillOval(210+(play.getX()*50), 10+(play.getY()*50), 30, 30);
        	}
        	
        	/**
        	 * Draws the battle GUI when a battle is triggered.
        	 * @param g
        	 */
        	public void paintBattle(Graphics g) {
        		if(inBattle) {
        			g.setColor(Color.BLACK);
        			g.fillRect(0,0,800,600);
        			g.setColor(Color.WHITE);
        			g.fillRect(0, 0, 800, 150);
        			g.fillRect(0, 400, 800, 200);
        			g.setColor(Color.BLUE);
        			g.fillRect(10, 10, 780, 130);
        			g.fillRect(10, 410, 780, 190);
        			g.setColor(Color.WHITE);
        			g.setFont(new Font("Comic Sans MS",20, 40));
        			drawBattleText(g);
        			
        			for(int i=0;i<batoru.enemyParty.size();i++) {
        				//g.fillOval(50+(i*250), 175, 200, 200);
        				g.setColor(Color.BLACK);
        				 Enemy temp=(Enemy)batoru.enemyParty.elementAt(i);
        				  try {
							 String fn=temp.sprite;
							  ImageIcon img=new ImageIcon(getClass().getResource(fn));
							    img.paintIcon(this, g, 50+(i*250), 200);
							  } catch (Exception ex) {
							    System.out.println(ex);
							  }
        				g.setColor(Color.WHITE);
        				g.drawString(temp.getName(), 50+(i*250), 190);
        			}
        			
        		}
        	}
        	/*God what the fuck*/
        	public void animationTest(Graphics g) {
        		if(anime) {
        		g.setColor(Color.RED);
        		g.fillRect(0, 0, 800, 600);
        		g.setColor(Color.WHITE);
        		try {
					Thread.sleep(15);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        		g.fillRect(0, 0, 800, 600);
        		try {
					Thread.sleep(15);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        		g.fillRect(0, 0, 800, 600);
        		repaint();
        		}
        		anime=false;
        		
        	}
        	
        	public void drawBattleText(Graphics g) {
        		//Draw the party and their hp
    			for(int i=0;i<play.party.size();i++) {
    				pChara temp=play.party.elementAt(i);
    				if(temp.hpRem<=0) {
    					temp.hpRem=0;
    					g.setColor(Color.RED);
    				}
    				g.drawString(temp.getName(), 15, 445+(i*40));
    				g.drawString(temp.getStat("hpRem")+"/"+temp.getStat("hpMax"), 250, 445+(i*40));
    				g.setColor(Color.WHITE);
    			}
    			if(battlePhase=="Main") {
    				
    			}
    		
    			g.drawString(battleText, 15, 45);
    			try {
					TimeUnit.MILLISECONDS.sleep(150);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			g.drawString(battleText2, 15, 105);
        	}
        	
        	public void setBattleText(Graphics g,String s) {
        		battleText=s;
        		repaint();
        	}
        	
   
        	
        	
    		public void paint (Graphics g){  
    				g.setColor(Color.BLACK);
    				g.fillRect(0, 0, 800, 600);
    				paintGrid(g);
        	
        			paintBattle(g);
        			

        			animationTest(g);

    			
    		}
        	
        };


        panel.setFocusable(true);
        panel.requestFocusInWindow();
        
        
        
        panel.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
            	
            	boolean res=false;
            	//Moving around and shit
            	if(inBattle==false) {
	            	if(e.getKeyCode()==37) {
	            		res=play.move(-1, 0);	
	            	}
	            	if(e.getKeyCode()==38) {
	            		res=play.move(0, -1);
	            	}
	            	if(e.getKeyCode()==39) {
	            		res=play.move(1, 0);
	            	}
	            	if(e.getKeyCode()==40||e.getKeyCode()==71) {
	            		res=play.move(0, 1);
	            	}
	         
            	}
            	//In battle codes
            	else {
            		//Z key unfortunately controls the battle loop lul
            	   	if(e.getKeyCode()==90) {
	            		System.out.println(battlePhase);
	            		//To get past the first 
	            		if(battlePhase=="Start") {
	            	
	            			currChar=batoru.turnOrder.remove();
	            			if(batoru.isEnemy(currChar.getName())) {
	            				battlePhase="commandPhase";
	            				int rng=(int)(Math.random()*(batoru.retParty.size()-1));
	            				target=batoru.retParty.elementAt(rng).getName();
	            				partyTarget=true;
	            				battlePhase="commandPhase";
	            			}
	            			else {
	            			battleText="It is "+currChar.getName()+"'s turn!";
	            			panel.repaint();
	            			
	            			battlePhase="Main";
	            			
	            			}
	            			panel.repaint();
	            			batoru.turnOrder.add(currChar);
	            		}
	            		//Main turn phase
	            		if(battlePhase=="Main") {
	            			String temp="";
	            			battleText=currChar.getName()+"\n";
	            			for(int i=0;i<currChar.skillSet.size();i++) {
	            				temp=temp+(i+1)+" )"+currChar.skillSet.elementAt(i)+" ";
	            			}
	            			battleText2=temp;
	            			panel.repaint();
	            		}
	            		
	            	}
            	   	
            	   	//1
            	   	if(e.getKeyCode()==49) {
            	   		if(battlePhase=="Main") {
            	   			command=currChar.skillSet.elementAt(0);
            	   			System.out.println(command+" was selected");
            	   			battlePhase="Target";
            	   			panel.repaint();
            	   		}
            	   		else if(battlePhase=="Target") {
            	   			battlePhase="targetP";
            	   			partyTarget=true;
            	   			panel.repaint();
            	   		}
            	   		else if(battlePhase=="targetP") {
            	   			target=batoru.retParty.elementAt(0).getName();
            	   			battlePhase="commandPhase";
            	   			panel.repaint();
            	   		}
            	  		else if(battlePhase=="targetE") {
            	   			target=batoru.enemyParty.elementAt(0).getName();
            	   			battlePhase="commandPhase";
            	   			panel.repaint();
            	   			
            	   		}
            	   	}
            	   	
            	   	//2
            	   	if(e.getKeyCode()==50) {
            	   		if(battlePhase=="Main") {
            	   			if(2<=currChar.skillSet.size()) {
            	   			command=currChar.skillSet.elementAt(1);
            	   			System.out.println(command+" was selected");
            	   			battlePhase="Target";
            	   			panel.repaint();
            	   			}
            	   		}
            	   		else if(battlePhase=="Target") {
            	   			battlePhase="targetE";
            	   			partyTarget=false;
            	   			panel.repaint();
            	   		}
            	   		else if(battlePhase=="targetP") {
            	   			if(2<=batoru.retParty.size()) {
            	   			target=batoru.retParty.elementAt(1).getName();
            	   			battlePhase="commandPhase";
            	   			panel.repaint();
            	   			}
            	   		}
            	   		else if(battlePhase=="targetE") {
            	   			if(2<=batoru.enemyParty.size()) {
            	   			target=batoru.enemyParty.elementAt(1).getName();
            	   			battlePhase="commandPhase";
            	   			panel.repaint();
            	   			}
            	   		}
            	   	}
            	   	
            	   	//3
            	   	if(e.getKeyCode()==51) {
            	   		if(battlePhase=="Main") {
            	   			if(3<=currChar.skillSet.size()) {
            	   			command=currChar.skillSet.elementAt(2);
            	   			System.out.println(command+" was selected");
            	   			battlePhase="Target";
            	   			panel.repaint();
            	   			}
            	   		}
            	   		
            	   		else if(battlePhase=="targetP") {
            	   			if(3<=batoru.retParty.size()) {
            	   			target=batoru.retParty.elementAt(2).getName();
            	   			battlePhase="commandPhase";
            	   			panel.repaint();
            	   			}
            	   		}
            	  		else if(battlePhase=="targetE") {
            	   			if(3<=batoru.enemyParty.size()) {
            	   			target=batoru.enemyParty.elementAt(2).getName();
            	   			battlePhase="commandPhase";
            	   			panel.repaint();
            	   			}
            	   		}
            	   	}
            	   	
            	   	//4
            	   	if(e.getKeyCode()==52) {
            	   		if(battlePhase=="Main") {
            	   			if(4<=currChar.skillSet.size()) {
            	   			command=currChar.skillSet.elementAt(3);
            	   			System.out.println(command+" was selected");
            	   			battlePhase="Target";
            	   			panel.repaint();
            	   			}
            	   		}
            	   		else if(battlePhase=="targetP") {
            	   			if(4<=batoru.retParty.size()) {
            	   			target=batoru.retParty.elementAt(3).getName();
            	   			battlePhase="commandPhase";
            	   			panel.repaint();
            	   			}
            	   		}
            	   	}
            	   	
            	   	if(battlePhase=="Target") {
            	   		battleText="Who will you target?";
            	   		battleText2="1)Party 2)Enemies";
            	   		panel.repaint();
            	   	}
            	   	
            	   	if(battlePhase=="targetP") {
            	   		battleText="Select Party Member Target";
            	   		battleText2="";
            	   		for(int i=0;i<batoru.retParty.size();i++) {
            	   			battleText2+=(i+1)+")"+batoru.retParty.elementAt(i).getName()+" ";
            	   		}
            	   		
            	   	}
            	   	
            	   	if(battlePhase=="targetE") {
            	   		battleText="Select Enemy Target:";
            	   		battleText2="";
            	   		for(int i=0;i<batoru.enemyParty.size();i++) {
            	   			battleText2+=(i+1)+")"+batoru.enemyParty.elementAt(i).getName()+" ";
            	   		}
            	   	}
            	   	
            	   	if(battlePhase=="commandPhase") {
            	   		battleText=currChar.getName()+" used "+command+" on "+target+"!";
            	   		battleText2=batoru.battleAction(currChar, command, partyTarget, target);
            	   		battlePhase="Start";
            	   		if(batoru.enemyParty.size()==0) {
            	   			inBattle=false;
            	   			battleText2="";
            	   		}
            	   		
            	   		panel.repaint();
            	   	}
            	}
            	
            	
            	if(e.getKeyCode()==82) {
            		inBattle=!inBattle;
            		battlePhase="";
            		battleText2="";
            		play.party.elementAt(0).hpRem-=2;
            	}
            	
            	if(e.getKeyCode()==65) {
            		anime=!anime;
            		panel.repaint();
            	}
        		System.out.println(e.getKeyCode());
        		panel.repaint();
        		if(res) {
        			//Trigger a battle and set text to default.
        			if((255*(Math.random()))>230) {
        			System.out.println("Battle Start");
        			inBattle=true;
        			battlePhase="Start";
        			batoru=new Battle(play.party,play.World.getCurrentMap().generateEnemies());
        			if(batoru.enemyParty.size()>1) {
        				battleText="Encountered "+batoru.enemyParty.elementAt(0).getName()+" and its team!";
        			}
        			else{
        				battleText="Encountered "+batoru.enemyParty.elementAt(0).getName()+"!";
        			}
        			
        			panel.repaint();
        			}
        		}
            }
        });
        
        
        window.setContentPane(panel);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    
    
    
    
    
    
    
    
    
   
 
    public static void main(String[] args) {
        new GameGUI();
    }
}