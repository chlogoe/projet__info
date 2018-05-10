package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import Model.Player;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Font;
import java.io.IOException;


@SuppressWarnings("serial")
public class Inventory extends JPanel{
	
	private Player player;
	
	private BufferedImage bomb;
	private BufferedImage key;
	private BufferedImage sword;
	private BufferedImage shield;
	
	public Inventory(){
		this.setFocusable(true);
		this.requestFocusInWindow();
		try { //Le try catch ici permet d'éviter les erreurs
        	this.bomb = ImageIO.read(getClass().getResourceAsStream("/images/bomb.png"));
        	this.key = ImageIO.read(getClass().getResourceAsStream("/images/key.png"));
        	this.sword = ImageIO.read(getClass().getResourceAsStream("/images/sword.png"));
        	this.shield = ImageIO.read(getClass().getResourceAsStream("/images/shield.png"));
        } catch (IOException e) {
    		e.printStackTrace();
        }
	}
	
	
	public void paint(Graphics g) {
		g.setFont(new Font("Calibri", Font.PLAIN, 18));
		paintHealth(g);
		paintOneUp(g);
		paintDamage(g);
		paintArmor(g);
		paintArrow(g);
		paintKey(g);
		paintBomb(g);
		paintPlanch(g);
    	
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
			g.drawImage(sword,100+i*32,150,null);
		}
	}
	
	public void paintArmor(Graphics g) {
		for (int i=0; i< player.getArmor();i++) {
			g.drawImage(shield,100+i*32,200,null);
		}
	}
	
	public void paintOneUp(Graphics g) {
		g.setColor(Color.BLACK);
    	g.drawString("x "+ Integer.toString(player.getOneUpAmount()), 32* player.getMaxHealth()+120, 120);
	}
	
	public void paintArrow(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(100, 250, 20, 10);
		//g.drawImage(key,100,300,null);
    	g.drawString(Integer.toString(player.getArrowAmount()), 130, 270);
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
	
	public void paintPlanch(Graphics g) {
		g.setColor(Color.ORANGE);
		g.fillRect(100, 400, 20, 20);
		g.setColor(Color.BLACK);
		g.drawString(Integer.toString(player.getPlanchAmount()), 130, 420);
	}
}
