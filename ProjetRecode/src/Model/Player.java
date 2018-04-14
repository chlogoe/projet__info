package Model;

import java.util.ArrayList;

public class Player extends Entity {

    private int lifes = 3;
    private int maxBombs = 5;
    private int bombAmount = 4;
    private int maxKeys = 5;
    private int keyAmount = 0;
    private ArrayList<Item> inventory = new ArrayList<Item>();

    public Player(int x, int y, int maxBomb, int health) {
        super(x, y, "Player", health, 4);
        super.setMaxHealth(10);
        
    }

   // //////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void notifyDeletableObserver() {
    	for (DeletableObserver o : observers) {
            o.delete(this, null);
        }
    }

    /*
     * Fonction qui gère la prise de dégat du joueur
     * Vérifie si la vie après les dégat est possible, sinon met la vie à zéro
     * 
     * Vérifie ensuite, si la vie du joueur est nulle, si il a une vie supplémentaire
     */
    
    @Override
    public void dealDamage(int damage) {
    	int health = this.getHealth();
    	
    	if(health - damage > 0) {
    		this.setHealth(health - damage);
    	}
    	else {
    		this.setHealth(0);
    	}
    	
    	health = this.getHealth();
    	
    	
    	if(lifes == 0 && health == 0) {
    		notifyDeletableObserver();
    		System.out.println("Fin de la partie");
    	}
    	else if(health == 0) {
    		this.setHealth(this.getMaxHealth());
    		lifes--;
    	}
    	
    }

    @Override
    public void attack() {
    	// TODO Auto-generated method stub
	}
    
    /*
     * 
     * 
     * Partie concernant l'inventaire du joueur, les fonctions permentant de connaitre 
     * et modifier le nombre de chaque object.
     * 
     * 
     */
    
    public void addItem(Item item) {
    	if(item instanceof Bomb) {
    		if (bombAmount < maxBombs) {
    			bombAmount++;
    			this.inventory.add(item);
    		}
    	}
    	else if (item instanceof Key){
    		if (keyAmount < maxKeys) {
        		keyAmount++;
        		this.inventory.add(item);
    		}
    	}
    	else {
    		this.inventory.add(item);
    	}
    }
    
    public int getBombAmount() {
    	return bombAmount;
    }
    
    public void useBomb() {
    	this.bombAmount--;
    }
    
    public int getKeyAmount() {
    	return keyAmount;
    }
}
