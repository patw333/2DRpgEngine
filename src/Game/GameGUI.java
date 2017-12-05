package Game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;


import Characters.pChara;
import Map.GameMap;
import Player.Player;
 
 
public class GameGUI{
 
	private Player play;
	private Battle batoru;
	private boolean inBattle;
	private String battleText;
	private String battlePhase;
	private JPanel panel;
	
    public GameGUI(){
    	play=new Player();
    	play.addPlayer(new pChara("Djeeta",3,100,5,5,5,27));
		play.addPlayer(new pChara("Lyria",1,100,5,5,5,8));
		play.addPlayer(new pChara("Katalina",10,100,5,5,5,17));
		play.addPlayer(new pChara("Rackam",10,100,5,5,5,100));
    	batoru=null;
    	inBattle=false;
    	battleText="";
    	battlePhase="";
   
    	play.addMap(new GameMap());
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e) {
            //silently ignore
        }
        JFrame window = new JFrame("Basic RPG GUI");
        window.setSize(800, 600);

        
        panel=new JPanel() { 
      
        	
        	public void paintGrid(Graphics g) {
        		GameMap curr=play.World.getCurrentMap();
        		curr.getTiles()[3][4].setType("Grey");
        		curr.getTiles()[3][5].setType("Grey");
        		curr.getTiles()[6][7].setType("Nice");
        		for(int i=0;i<curr.getLength();i++) {
        			for(int j=0;j<curr.getWidth();j++) {
        				g.drawRect(200+(i*50),j*50,50,50);
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
        				g.fillOval(50+(i*250), 175, 200, 200);
        				g.setColor(Color.BLACK);
        				g.drawString(batoru.enemyParty.elementAt(i).getName(), 60+(i*250),255);
        				g.setColor(Color.WHITE);
        			}
        			
        		}
        	}
        	
        	public void drawBattleText(Graphics g) {
        		//Draw the party and their hp
    			for(int i=0;i<play.party.size();i++) {
    				pChara temp=play.party.elementAt(i);
    				g.drawString(temp.getName(), 15, 445+(i*40));
    				g.drawString(temp.getStat("hpRem")+"/"+temp.getStat("hpMax"), 250, 445+(i*40));
    			}
    		
    			g.drawString(battleText, 15, 45);
        	}
        	
        	public void setBattleText(Graphics g,String s) {
        		battleText=s;
        		repaint();
        	}
        	
   
        	
        	
    		public void paint (Graphics g){  
    				g.setColor(Color.GRAY);
    				g.fillRect(0, 0, 800, 600);
    				paintGrid(g);
        			paintChara(g);
        			paintBattle(g);
    			
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
	            	if(e.getKeyCode()==40) {
	            		res=play.move(0, 1);
	            	}
	            	if(e.getKeyCode()==90) {
	            		if(battlePhase=="Start") {
	            			battlePhase="Main";
	            			
	            		}
	            	}
            	}
            	if(e.getKeyCode()==82) {
            		inBattle=!inBattle;
            		play.party.elementAt(0).hpRem-=2;
            	}
        		System.out.println(e.getKeyCode());
        		panel.repaint();
        		if(res) {
        			if((255*(Math.random()))>190) {
        			System.out.println("Battle Start");
        			inBattle=true;
        			batoru=new Battle(play.party,play.World.getCurrentMap().generateEnemies());
        			if(batoru.enemyParty.size()>1) {
        				battleText="Encountered "+batoru.enemyParty.elementAt(0).getName()+" and its team!";
        			}
        			else{
        				battleText="Encountered "+batoru.enemyParty.elementAt(0).getName()+"!";
        			}
        			
        			battlePhase="Start";
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