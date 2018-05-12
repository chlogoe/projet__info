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
    private String gameName = "The binding of Zelda: breath of the Isaac";

    public Window() { // Ajout d'un param�tre permetant de d�finir la taille de la carte
    	
    	JFrame window = new JFrame(gameName);
		this.windowPanel = new JPanel();
		this.windowPanel.setLayout(new BoxLayout(this.windowPanel, BoxLayout.X_AXIS));
		this.map.setPreferredSize(new Dimension(32, 32));
		this.windowPanel.add(this.map);
		this.windowPanel.add(this.inventory);
		
        //Cr�e la fen�tre � proprement parl�
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Quand on clique sur la croix, �a quitte
        window.setBounds(0, 0, 1900, 988); //Adapte la taille de la fen�tre � la taille de la carte
        window.getContentPane().setBackground(Color.GRAY);//La couleur de fond de la fen�tre est grise
        window.getContentPane().add(this.windowPanel);//On met la carte dans la fen�tre
        window.setLocationRelativeTo(null);//On place la fen�tre au centre de l'image
        window.setResizable(false);//On emp�che de redimensionner la fen�tre
        window.setVisible(true);//On rend la fen�tre visible
        
        new ViewUpdater(this);
    }

    public void setGameObjects(ArrayList<GameObject> objects) { //Fonction qui ajoute tout le terrain � la carte
        this.map.setTerrain(objects);
    }
    
    public void setEntities(ArrayList<Entity> objects) { //Fonction qui ajoute toutes les entit�s � la carte
        this.map.setEntities(objects);
    }
    
    public void setItems(ArrayList<Item> items) {
    	this.map.setItems(items);
    }
    
    public void setPlayer(Player player) {
    	this.inventory.setPlayer(player);
    }

    public void update() {	//Met la carte et l'inventaire � jour
        this.windowPanel.repaint();
    }
    

    public void setKeyListener(KeyListener keyboard) { //Ajoute le lecteur de clavier � la fen�tre
        this.map.addKeyListener(keyboard);
    }
}
