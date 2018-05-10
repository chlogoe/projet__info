package View;

import Model.GameObject;
import Model.Item;

import javax.imageio.ImageIO;
import javax.swing.*;
import Model.Player;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

@SuppressWarnings("serial")
public class Inventory extends JPanel{
	
	private Player player;
	
	private BufferedImage bomb;
	private BufferedImage key;
	
	public Inventory(){
		this.setFocusable(true);
		this.requestFocusInWindow();
		try { //Le try catch ici permet d'éviter les erreurs
        	this.bomb = ImageIO.read(getClass().getResourceAsStream("/images/bomb.png"));
        	this.key = ImageIO.read(getClass().getResourceAsStream("/images/key.png"));
        } catch (IOException e) {
    		e.printStackTrace();
        }
	}
	
	
	public void paint(Graphics g) {
		paintHealth(g);
		paintDamage(g);
		paintArmor(g);
		paintKey(g);
		paintBomb(g);
		
		g.setColor(Color.BLACK);
    	
		g.drawString(player.getInventory().toString(), 100, 500);
		g.drawString(player.getUsable().toString(), 100, 550);
    	
	}
	public void setPlayer(Player player) {
    	this.player = player;
    }	
	
	public void paintHealth( Graphics g) {
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
	
	public void paintDamage(Graphics g) {
		for (int i=0; i< player.getDamage();i++) {
			g.setColor(Color.CYAN);
			g.fillOval(100+i*32, 150, 30, 30);
		}
	}
	
	public void paintArmor(Graphics g) {
		for (int i=0; i< player.getArmor();i++) {
			g.setColor(Color.MAGENTA);
			g.fillOval(100+i*32, 200, 30, 30);
		}
	}
	
	public void paintKey(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawImage(key,100,300,null);
    	g.drawString(Integer.toString(player.getKeyAmount()), 130, 320);
	}
	
	public void paintBomb(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawImage(bomb,100,350,null);
    	g.drawString(Integer.toString(player.getBombAmount()), 130, 370);
	}
}
