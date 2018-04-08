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
        super(x, y, "Player", health);
        super.setMaxHealth(10);
    }

   // //////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void notifyDeletableObserver() {
    	for (DeletableObserver o : observers) {
            o.delete(this, null);
        }
    }

    @Override
    public void activate() {
    	int health = this.getHealth();
    	this.setHealth(health - 1);
    	if(health == 0 && lifes == 0) {
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
