package Model;

import java.util.ArrayList;
import java.util.Hashtable;

public class Player extends Entity {

    private int lifes = 3;
    
    private int maxBombs = 5;
    private int maxKeys = 5;
    
    //private ArrayList<Item> inventory = new ArrayList<Item>();
    
    private Hashtable<String, Integer> inventory = new Hashtable<String, Integer>();
    
    
    

    public Player(int x, int y, int maxBomb, int health) {
        super(x, y, "Player", health, 4);
        super.setMaxHealth(10);
        initializeInventory();
    }

   // //////////////////////////////////////////////////////////////////////////////////////
    
    private void initializeInventory() {
    	inventory.put("Bomb", 4);
    	inventory.put("Key", 0);
    	inventory.put("DamageUp", 0);
    }
    
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
    
    private void heal(int healing) {
    	int nextHealth = this.getHealth()+healing;
    	
    	if(nextHealth < this.getMaxHealth()) {
    		this.setHealth(nextHealth);
    	}
    	else {
    		this.setHealth(this.getMaxHealth());
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
    	if(item instanceof Heart) {
    		heal(3);
    	}
    	else {
    		String ID = item.getID();
    		inventory.put(ID, inventory.get(ID)+1);
    	}
    }
    
    public int getBombAmount() {
    	return inventory.get("Bomb");
    }
    
    public void useBomb() {
    	inventory.put("Bomb", inventory.get("Bomb")-1);
    }
    
    public int getKeyAmount() {
    	return inventory.get("Key");
    }
    
    public Hashtable<String, Integer> getInventory(){
    	return inventory;
    }
    
    public void useKey() {
    	inventory.put("Key", inventory.get("Key")-1);
    }
}
