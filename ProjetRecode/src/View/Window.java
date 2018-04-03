package View;

import Model.GameObject;

import java.awt.Color;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Window {
    private Map2 map = new Map2();
    private int size;
    private String gameName = "Splendid Game";

    public Window(int size) { // Ajout d'un param�tre permetant de d�finir la taille de la carte
    	this.size = size;
    	map.setSize(size); //D�finit la taille de la carte
    	
        JFrame window = new JFrame(gameName);//Cr�e la fen�tre � proprement parl�
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Quand on clique sur la croix, �a quitte
        window.setBounds(0, 0, size*32+500, size*32+28); //Adapte la taille de la fen�tre � la taille de la carte
        window.getContentPane().setBackground(Color.black);//La couleur de fond de la fen�tre est grise
        window.getContentPane().add(this.map);//On met la carte dans la fen�tre
        window.setLocationRelativeTo(null);//On place la fen�tre au centre de l'image
        window.setResizable(false);//On emp�che de redimensionner la fen�tre
        window.setVisible(true);//On rend la fen�tre visible
    }

    public void setGameObjects(ArrayList<GameObject> objects) { //Fonction qui ajoute tout le terrain � la carte
        this.map.setTerrain(objects);
        this.map.redraw();
    }
    
    public void setOther(ArrayList<GameObject> objects) { //Fonction qui ajoute toutes les entit�s � la carte
        this.map.setOther(objects);
        this.map.redraw();
    }

    public void update() {	//Met la carte � jour
        this.map.redraw();
    }

    public void setKeyListener(KeyListener keyboard) { //Ajoute le lecteur de clavier � la fen�tre
        this.map.addKeyListener(keyboard);
    }
    
    public int getSize() { //permet aux autre classe d'obtenir la taille de la carte
    	return size; 
    }
}
