package View;

import Model.GameObject;
import Model.Item;

import javax.swing.JPanel;
import Model.Player;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Inventory extends JPanel{
	
	private Player player;
	
	public Inventory(){
		this.setFocusable(true);
		this.requestFocusInWindow();
	}
	
	
	public void paint(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, 10000, 10000);
		g.setColor(Color.GREEN);
		g.fillRect(100, 100, 300*player.getHealth()/player.getMaxHealth(), 20);
		g.setColor(Color.RED);
		g.fillRect(100+300*player.getHealth()/player.getMaxHealth(), 100, 300-300*player.getHealth()/player.getMaxHealth(), 20);
    				
	}
	public void setPlayer(Player player) {
    	this.player = player;
    }	
	
	public void redraw() { 
        this.repaint();
    }
}
