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
	private BufferedImage arrow;
	private BufferedImage emptyHeart;
	private BufferedImage fullHeart;
	private BufferedImage plank;
	private BufferedImage bow;
	
	public Inventory(){
		this.setFocusable(true);
		this.requestFocusInWindow();
		try { //Le try catch ici permet d'éviter les erreurs
        	this.bomb = ImageIO.read(getClass().getResourceAsStream("/images/bomb.png"));
        	this.key = ImageIO.read(getClass().getResourceAsStream("/images/key2.png"));
        	this.sword = ImageIO.read(getClass().getResourceAsStream("/images/sword.png"));
        	this.shield = ImageIO.read(getClass().getResourceAsStream("/images/shield.png"));
        	this.arrow = ImageIO.read(getClass().getResourceAsStream("/images/Arrow.png"));
        	this.emptyHeart = ImageIO.read(getClass().getResourceAsStream("/images/emptyHeart.png"));
        	this.fullHeart = ImageIO.read(getClass().getResourceAsStream("/images/fullHeart.png"));
        	this.plank = ImageIO.read(getClass().getResourceAsStream("/images/plank.png"));
        	this.bow = ImageIO.read(getClass().getResourceAsStream("/images/bow.png"));
        } catch (IOException e) {
    		e.printStackTrace();
        }
	}
	
	
	public void paint(Graphics g) {
		g.setFont(new Font("Calibri", Font.PLAIN, 18));
		g.setColor(Color.BLACK);
		paintHealth(g);
		paintOneUp(g);
		paintDamage(g);
		paintArmor(g);
		paintArrow(g);
		paintKey(g);
		paintBomb(g);
		paintPlank(g);
    	
	}
	public void setPlayer(Player player) {
    	this.player = player;
    }	
	
	public void paintHealth( Graphics g) {
		for (int i=0; i<player.getMaxHealth(); i++) {
			if (i<player.getHealth()) {
				g.drawImage(fullHeart,100+i*32,100,null);
			}
			else {
				g.drawImage(emptyHeart,100+i*32,100,null);
			}
		}
	}
	
	
	
	public void paintDamage(Graphics g) {
		for (int i=0; i< player.getDamage();i++) {
			if (player.getWeapon()==1) {
				g.drawImage(sword,100+i*32,150,null);
			}
			else {
				g.drawImage(bow,100+i*32,150,null);
			}
		}
	}
	
	public void paintArmor(Graphics g) {
		for (int i=0; i< player.getArmor();i++) {
			g.drawImage(shield,100+i*32,200,null);
		}
	}
	
	public void paintOneUp(Graphics g) {
    	g.drawString("x "+ Integer.toString(player.getOneUpAmount()), 32* player.getMaxHealth()+110, 120);
	}
	
	public void paintArrow(Graphics g) {
		g.drawImage(arrow,100,250,null);
    	g.drawString(Integer.toString(player.getArrowAmount()), 140, 270);
	}
	
	public void paintKey(Graphics g) {
		g.drawImage(key,100,300,null);
    	g.drawString(Integer.toString(player.getKeyAmount()), 140, 320);
	}
	
	public void paintBomb(Graphics g) {
		g.drawImage(bomb,100,350,null);
    	g.drawString(Integer.toString(player.getBombAmount()), 140, 370);
	}
	
	public void paintPlank(Graphics g) {
		g.drawImage(plank,100,400,null);
		g.drawString(Integer.toString(player.getPlankAmount()), 140, 420);
	}
}
