package Model;

import java.util.Hashtable;

public class Player extends Entity {
    
    private Hashtable<String, Integer> inventory = new Hashtable<String, Integer>();
    private Hashtable<String, Integer> maxItem = new Hashtable<String, Integer>();
    
    private Game game;
    

    public Player(Game game) {
        super(-1, -1, "Player", 10, 4);
        super.setMaxHealth(10);
        this.game = game;
        initializeInventory();
    }

   // //////////////////////////////////////////////////////////////////////////////////////
    
    private void initializeInventory() {
    	inventory.put("Bomb", 1);
    	inventory.put("Key", 0);
    	inventory.put("DamageUp", 0);
    	inventory.put("OneUp", 2);
    	inventory.put("Planch", 1);
    	maxItem.put("Bomb", 10);
    	maxItem.put("Key", 10);
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
    public void sufferDamage(int damage) {
    	int health = this.getHealth();
    	
    	if(health - damage > 0) {
    		this.setHealth(health - damage);
    	}
    	else {
    		this.setHealth(0);
    	}
    	
    	health = this.getHealth();
    	
    	
    	if(inventory.get("OneUp") == 0 && health == 0) {
    		notifyDeletableObserver();
    		System.out.println("Fin de la partie");
    	}
    	else if(health == 0) {
    		this.setHealth(this.getMaxHealth());
    		inventory.put("OneUp", inventory.get("OneUp")-1);
    	}
    }
    
    public void interract(Direction side) {
    	this.setDirection(side);
    	GameObject target  = game.getBlockType(side, this);
    	if(target instanceof Hole) {
    		if(((Hole) target).isObstacle(this) && inventory.get("Planch") > 0){
    			inventory.put("Planch", inventory.get("Planch")-1);
    			((Hole) target).activate();
    		}
    		else if(!((Hole) target).isObstacle(this)) {
    			inventory.put("Planch", inventory.get("Planch")+1);
    			((Hole) target).activate();
    		}
    	}
    	else if(target instanceof Entity) {
    		((Entity) target).sufferDamage(this.getDamage());
    	}
    	
    }
    
    @Override
    public void move(Direction direction) {
    	if(!game.checkObstacle(direction, this)) {
    		this.setPosX(this.getPosX()+direction.getX());
    		this.setPosY(this.getPosY()+direction.getY());
    		game.pickUpItem();
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
    	// TODO Gérer l'attaque du joueur par ici et non pas via la classe Game
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
    		if(maxItem.get(ID)==null || maxItem.get(ID)>inventory.get(ID)) {
    			inventory.put(ID, inventory.get(ID)+1);
    		}
    		
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
