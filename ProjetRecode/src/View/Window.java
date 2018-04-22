package View;

import Model.Entity;
import Model.GameObject;
import Model.Item;
import Model.Player;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window {
	private JPanel windowPanel;
    private Map map = new Map();
    private Inventory inventory= new Inventory();
    private int size;
    private String gameName = "Splendid Game";

    public Window(int size) { // Ajout d'un paramètre permetant de définir la taille de la carte
    	this.size = size;
    	map.setSize(size); //Définit la taille de la carte
    	
    	JFrame window = new JFrame(gameName);
		this.windowPanel = new JPanel();
		this.windowPanel.setLayout(new BoxLayout(this.windowPanel, BoxLayout.X_AXIS));
		this.map.setPreferredSize(new Dimension(size, size));
		this.windowPanel.add(this.map);
		this.windowPanel.add(this.inventory);
		
        //Crée la fenêtre à proprement parlé
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Quand on clique sur la croix, ça quitte
        window.setBounds(0, 0, size*32+950, size*32+28); //Adapte la taille de la fenêtre à la taille de la carte
        window.getContentPane().setBackground(Color.GRAY);//La couleur de fond de la fenètre est grise
        window.getContentPane().add(this.windowPanel);//On met la carte dans la fenêtre
        window.setLocationRelativeTo(null);//On place la fenêtre au centre de l'image
        window.setResizable(false);//On empêche de redimensionner la fenêtre
        window.setVisible(true);//On rend la fenêtre visible
        
        //TODO fixer la taille de la fenêtre
        //TODO Ne plus dépendre de size
    }

    public void setGameObjects(ArrayList<GameObject> objects) { //Fonction qui ajoute tout le terrain à la carte
        this.map.setTerrain(objects);
        this.map.redraw();
    }
    
    public void setEntities(ArrayList<Entity> objects) { //Fonction qui ajoute toutes les entités à la carte
        this.map.setEntities(objects);
        this.map.redraw();
    }
    
    public void setItems(ArrayList<Item> items) {
    	this.map.setItems(items);
    	this.map.redraw();
    }
    
    public void setPlayer(Player player) {
    	this.inventory.setPlayer(player);
    }

    public void update() {	//Met la carte et l'inventaire à jour
        this.windowPanel.repaint();
        //TODO vérifier si cette fonction est encore nécéssaire
    }
    

    public void setKeyListener(KeyListener keyboard) { //Ajoute le lecteur de clavier à la fenêtre
        this.map.addKeyListener(keyboard);
    }
    
    public int getSize() { //permet aux autre classe d'obtenir la taille de la carte
    	return size; 
    }
}
