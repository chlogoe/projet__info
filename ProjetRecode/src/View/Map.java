package View;

import Model.Direction;
import Model.Entity;
import Model.GameObject;
import Model.Item;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Map extends JPanel {
	private int size;	//Initialisation des variables
    private ArrayList<GameObject> terrains = null;
    private ArrayList<Entity> entities = null;
    private ArrayList<Item> items = null;
    
    
    private BufferedImage font;
    private BufferedImage breakableBlock;
    private BufferedImage tintedRock;
    private BufferedImage spike;

    public Map() {
        this.setFocusable(true);//Autorise la map à être au premier plan
        this.requestFocusInWindow(); //Demande à la map d'être au premier plan
        try { //Le try catch ici permet d'éviter les erreurs
        	this.font = ImageIO.read(getClass().getResourceAsStream("/images/backGround.png")); //Charge l'image dans la mémoire du jeu
        	this.breakableBlock = ImageIO.read(getClass().getResourceAsStream("/images/stonebrick.png"));
        	this.tintedRock = ImageIO.read(getClass().getResourceAsStream("/images/stonebrick_cracked.png"));
        	this.spike = ImageIO.read(getClass().getResourceAsStream("/images/spike.png"));
        } catch (IOException e) {
    		e.printStackTrace();
    	}
    }

    public void paint(Graphics g) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int x = i;
                int y = j;
                g.drawImage(font, x*32, y*32,null); //on dessine une image sur toutes les cases de sol, on ajoute plus bas les autres textures
            }
        }

        for (GameObject terrain : this.terrains) { //Dessine tout les composant du terrain
            int x = terrain.getPosX();
            int y = terrain.getPosY();
            String ID = terrain.getID();
            switch(ID) {
            case "A":
            	g.setColor(Color.BLACK);
                g.drawRect(x * 32, y * 32, 31, 31);
            	g.setColor(Color.DARK_GRAY);
            	g.fillRect(x * 32, y * 32, 31, 31);
            	break;
            case "B":
            	g.drawImage(breakableBlock, x*32, y*32,null);
            	break;
            case "W":
            	g.setColor(Color.BLUE);
            	g.fillRect(x * 32, y * 32, 32, 32);
            	break;
            case "H":
            	g.setColor(Color.BLACK);
            	g.drawRect(x*32, y*32, 31, 31);
            	g.fillRect(x*32, y*32, 31, 31);
            	break;
            case "D":
            	g.setColor(Color.WHITE);
            	if(entities.size() == 1) {
            		g.fillRect(x*32+28, y*32, 4, 32);
            	}
            	else {
            		g.fillRect(x*32, y*32+28, 32, 4);
            	}
            	break;
            case "P":
            	g.drawImage(spike, x*32, y*32, null);
            	break;
            case "B2":
            	g.drawImage(tintedRock, x*32, y*32,null);
            	break;
            case "C":
            	g.setColor(Color.ORANGE);
            	g.fillRect(x*32, y*32, 32, 32);
            }
            
            
        	
        }
        
        for(Entity entity : this.entities) { //Dessine toutes les entités
        	int x = entity.getPosX();
        	int y = entity.getPosY();
        	String ID = entity.getID();
        	Direction direction = entity.getDirection();
        	int a1[]={32*x+16,32*x+4,32*x+28};
        	int b1[]={32*y+4,32*y+28,32*y+28};
        	int a2[]={32*x+4,32*x+4,32*x+28};
        	int b2[]={32*y+4,32*y+28,32*y+16};
        	int a3[]={32*x+16,32*x+4,32*x+28};
        	int b3[]={32*y+28,32*y+4,32*y+4};
        	int a4[]={32*x+28,32*x+28,32*x+4};
        	int b4[]={32*y+4,32*y+28,32*y+16};
        	
        	switch(ID) {
        	case "Player":
        		g.setColor(Color.RED);
        		switch(direction) {
        		case Up:
        			g.fillPolygon(a1,b1,3);
                	break;
        		case Right:
        			g.fillPolygon(a2,b2,3);
                	break;
        		case Down:
        			g.fillPolygon(a3,b3,3);
                	break;
        		case Left:
        			g.fillPolygon(a4,b4,3);
                	break;
        		}
        		g.setColor(Color.GREEN);
        		g.fillRect(x*32+1, y*32+2, 30*entity.getHealth()/entity.getMaxHealth(), 2);
        		g.setColor(Color.RED);
        		g.fillRect(x*32+32*entity.getHealth()/entity.getMaxHealth(), y*32+2, 30-30*entity.getHealth()/entity.getMaxHealth(), 2);
            	
        		break;
        	case "Test":
        		g.setColor(Color.GREEN);
        		g.fillRect(x * 32+8, y * 32+8, 16, 16);
        		g.fillRect(x*32, y*32+2, 30*entity.getHealth()/entity.getMaxHealth(), 2);
        		g.setColor(Color.RED);
        		g.fillRect(x*32-1+32*entity.getHealth()/entity.getMaxHealth(), y*32+2, 30-30*entity.getHealth()/entity.getMaxHealth(), 2);
        		break;
        	}
        }
        
        for(Item item : items) {
        	int x = item.getPosX();
        	int y = item.getPosY();
        	String ID = item.getID();
        	switch(ID) {
        	case "ActiveBomb":
        		g.setColor(Color.MAGENTA);
        		g.fillOval(x*32+8, y*32+8, 16, 16);
        		break;
        	case "ActiveBomb2":
        		g.setColor(Color.BLUE);
        		g.fillOval(x*32+8, y*32+8, 16, 16);
        		break;
        	case "Bomb":
        		g.setColor(Color.BLACK);
        		g.fillOval(x*32+8, y*32+8, 16, 16);
        		break;
        	case "Heart":
        		g.setColor(Color.PINK);
        		g.fillOval(x*32+8, y*32+8, 16, 16);
        	}
        		
        }
    }

    public void setTerrain(ArrayList<GameObject> terrain) { //Permet de modifier le terrain de la map depuis l'extérieur de la classe
        this.terrains = terrain;
    }
    
    public void setEntities(ArrayList<Entity> entities) { //Permet d'ajouter des entités depuis l'ectérieur de la classe
        this.entities = entities;
    }
    
    public void setItems(ArrayList<Item> items) {
    	this.items = items;
    }

    public void redraw() { //Redessine la map
        this.repaint();
    }
    
    public void setSize(int size) { // Définit la taille de la map
    	this.size = size;
    }
}
