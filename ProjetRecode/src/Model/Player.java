package Model;

import java.util.ArrayList;

public class Player extends Entity {

    private int lifes = 0;
    private int health;
    private int maxBombs = 5;
    private int bombAmount = 4;
    private int maxKeys = 5;
    private int keyAmount = 0;
    private ArrayList<Item> inventory = new ArrayList<Item>();

    public Player(int x, int y, int maxBomb, int health) {
        super(x, y, "Player", health);
        this.health = health;
    }

    public void move(int X, int Y) {
        this.posX = this.posX + X;
        this.posY = this.posY + Y;
    }

   // //////////////////////////////////////////////////////////////////////////////////////


    @Override
    public boolean isObstacle(Entity entity) {
        return true;
    }

    @Override
    public void attachDeletable(DeletableObserver po) {
    	// TODO Auto-generated method stub
    }

    @Override
    public void notifyDeletableObserver() {
    	// TODO Auto-generated method stub
    }

    @Override
    public void activate() {
    	System.out.println("OK");
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
