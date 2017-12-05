package Game;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class BattlePanel extends JPanel{
	
	public BattlePanel() {
		super();
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawRect(230, 80, 10, 10);
		g.setColor(Color.RED);
		g.fillRect(230, 80, 10, 10);
	}
	
}
