package View;

import Model.GameObject;
import Model.Item;

import javax.swing.*;
import Model.Player;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

@SuppressWarnings("serial")
public class Inventory extends JPanel{
	
	private Player player;
	
	private BufferedImage bomb;
	private BufferedImage key;
	
	public Inventory(){
		this.setFocusable(true);
		this.requestFocusInWindow();
	}
	
	
	public void paint(Graphics g) {
		
		for (int i=0; i<player.getMaxHealth(); i++) {
			if (i<player.getHealth()) {
				g.setColor(Color.GREEN);
				g.fillOval(100+i*32, 100, 30, 30);
			}
			else {
				g.setColor(Color.RED);
				g.fillOval(100+i*32,100, 30, 30);
			}
		}
		
		
		

    	
	}
	public void setPlayer(Player player) {
    	this.player = player;
    }	
}
