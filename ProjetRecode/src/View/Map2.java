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
public class Map2 extends JPanel {
	private int size;	//Initialisation des variables
    private ArrayList<GameObject> terrains = null;
    private ArrayList<Entity> entities = null;
    private ArrayList<Item> items = null;
    private BufferedImage font;

    public Map2() {
        this.setFocusable(true);//Autorise la map � �tre au premier plan
        this.requestFocusInWindow(); //Demande � la map d'�tre au premier plan
        try { //Le try catch ici permet d'�viter les erreurs
        	this.font = ImageIO.read(getClass().getResourceAsStream("/images/backGround.png")); //Charge l'image dans la m�moire du jeu
        } catch (IOException e) {
    		e.printStackTrace();
    	}
    }

    public void paint(Graphics g) {
    	
    	for (int i=0; i<size+20 ; i++) {
    		for (int j=0; j<size ; j++) {
    			int x = i;
    			int y = j;
    			g.setColor(Color.BLACK);
    			g.fillRect(x*32, y*32, 32, 32);
    		}
    	}
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int x = i;
                int y = j;
                g.drawImage(font, x*32, y*32,null); //on dessine une image sur toutes les cases de sol, on ajoute plus bas les autres textures
                g.fillRect(x*32, y*32, 32, 32);
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
            	g.setColor(Color.BLACK);
                g.drawRect(x * 32, y * 32, 31, 31);
                g.setColor(Color.GRAY);
                g.fillRect(x * 32, y * 32, 31, 31);
            	break;
            case "C":
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
            	g.setColor(Color.LIGHT_GRAY);
            	g.drawRect(x*32+4, y*32+4, 24, 24);
            	break;
            case "B2":
            	g.setColor(Color.PINK);
            	g.fillRect(x*32, y*32, 32, 32);
            }
            
            
        	
        }
        
        for(Entity entity : this.entities) { //Dessine toutes les entit�s
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
        		g.fillRect(x * 32+8, y * 32+8, 16, 16);
        		break;
        	case "Bomb":
        		g.setColor(Color.BLACK);
        		g.fillRect(x * 32+8, y * 32+8, 16, 16);
        		break;
        	}
        		
        }
    }

    public void setTerrain(ArrayList<GameObject> terrain) { //Permet de modifier le terrain de la map depuis l'ext�rieur de la classe
        this.terrains = terrain;
    }
    
    public void setEntities(ArrayList<Entity> entities) { //Permet d'ajouter des entit�s depuis l'ect�rieur de la classe
        this.entities = entities;
    }
    
    public void setItems(ArrayList<Item> items) {
    	this.items = items;
    }

    public void redraw() { //Redessine la map
        this.repaint();
    }
    
    public void setSize(int size) { // D�finit la taille de la map
    	this.size = size;
    }
}
