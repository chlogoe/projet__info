package View;

import Model.GameObject;

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
    private ArrayList<GameObject> entities = null;
    private BufferedImage font;

    public Map2() {
        this.setFocusable(true);//Autorise la map à être au premier plan
        this.requestFocusInWindow(); //Demande à la map d'être au premier plan
        try { //Le try catch ici permet d'éviter les erreurs
        	this.font = ImageIO.read(getClass().getResourceAsStream("/images/font.png")); //Charge l'image dans la mémoire du jeu
        } catch (IOException e) {
    		e.printStackTrace();
    	}
    }

    public void paint(Graphics g) {
    	
    	for (int i=0; i<size+400 ; i++) {
    		for (int j=0; j<size ; j++) {
    			int x = i;
    			int y = j;
    			g.setColor(Color.BLACK);
    			g.fillRect(x, y, 31, 31);
    		}
    	}
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
            char ID = terrain.getID();

            switch(ID) {
            case 'A':
            	g.setColor(Color.BLACK);
                g.drawRect(x * 32, y * 32, 31, 31);
            	g.setColor(Color.DARK_GRAY);
            	g.fillRect(x * 32, y * 32, 31, 31);
            	break;
            case 'B':
            	g.setColor(Color.BLACK);
                g.drawRect(x * 32, y * 32, 31, 31);
                g.setColor(Color.GRAY);
                g.fillRect(x * 32, y * 32, 31, 31);
            	break;
            case 'C':
            	g.setColor(Color.BLUE);
            	g.fillRect(x * 32, y * 32, 32, 32);
            	break;
            }
            
        	
        }
        
        for(GameObject other : this.entities) { //Dessine toutes les entités
        	int x = other.getPosX();
        	int y = other.getPosY();
        	char ID = other.getID();
        	
        	switch(ID) {
        	case 'P':
        		g.setColor(Color.RED);
            	g.fillRect(x * 32+8, y * 32+8, 16, 16);
        		break;
        	}
        }
    }

    public void setTerrain(ArrayList<GameObject> terrain) { //Permet de modifier le terrain de la map depuis l'extérieur de la classe
        this.terrains = terrain;
    }
    
    public void setOther(ArrayList<GameObject> other) { //Permet d'ajouter des entités depuis l'ectérieur de la classe
        this.entities = other;
    }

    public void redraw() { //Redessine la map
        this.repaint();
    }
    
    public void setSize(int size) { // Définit la taille de la map
    	this.size = size;
    }
}
