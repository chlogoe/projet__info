package Model;

import java.util.Hashtable;

public class Player extends Entity {
    
    private Hashtable<String, Integer> inventory = new Hashtable<String, Integer>();
    private Hashtable<String, Integer> maxItem = new Hashtable<String, Integer>();
    private Hashtable<Integer, String> usable = new Hashtable<Integer, String>();
    
    private Game game;
    private final int numberOfWeapon = 2;
    private int slot = 1;

    public Player(Game game) {
        super(-1, -1, "Player", 10, 5);
        super.setMaxHealth(10);
        this.game = game;
        initializeInventory();
    }

   // //////////////////////////////////////////////////////////////////////////////////////
    
    private void initializeInventory() {
    	inventory.put("Bomb", 1);
    	inventory.put("Key", 0);
    	inventory.put("DamageUp", 0);
    	inventory.put("HealthUp", 0);
    	inventory.put("Armor", 1);
    	inventory.put("OneUp", 2);
    	inventory.put("Planch", 1);
    	inventory.put("Weapon", 1);
    	inventory.put("Arrow", 5);
    	maxItem.put("Bomb", 10);
    	maxItem.put("Key", 10);
    	maxItem.put("Arrow", 50);
    }
    
    @Override
    public void notifyDeletableObserver() {
    	for (DeletableObserver o : observers) {
            o.delete(this, null);
        }
    }

    public void switchWeapon() {
    	if(inventory.get("Weapon") < numberOfWeapon) {
    		inventory.put("Weapon", inventory.get("Weapon")+1);
    	}
    	else {
    		inventory.put("Weapon", 1);
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
    	float health = this.getHealth();
    	
    	if(health - damage > 0) {
    		this.setHealth(health - damage/inventory.get("Armor"));
    	}
    	else {
    		this.setHealth(0);
    	}
    	
    	health = this.getHealth();
    	
    	
    	if(inventory.get("OneUp") == 0 && health == 0) {
    		notifyDeletableObserver();
    		System.out.println("Fin de la partie");
    		game.changeRunning();
    	}
    	else if(health == 0) {
    		this.setHealth(this.getMaxHealth());
    		inventory.put("OneUp", inventory.get("OneUp")-1);
    		setPosX(1);
    		setPosY(1);
    	}
    }
    
    public void swapSlot(int slot) {
    	this.slot = slot;
    }
    
    public void useItem() {
    	if(usable.get(slot) == "DamagePotion") {
    		//TODO augmenter les dégats pour x secondes
    	}
    	else if(usable.get(slot) == "RegenPotion") {
    		//TODO regen de la vie
    	}
    	usable.remove(slot);
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
    	else if(target instanceof Entity && inventory.get("Weapon") == 1) {
    		((Entity) target).sufferDamage(this.getDamage());
    	}
    	else if(inventory.get("Weapon") == 2){
    		game.throwProjectile(this,7);
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
    	float nextHealth = this.getHealth()+healing;
    	
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
    	String ID = item.getID();
    	
    	switch(ID) {
    	case "Heart":
    		heal(3);
    		break;
    	case "HealthUp":
    		this.setMaxHealth(getMaxHealth()+2);
    		heal(2);
    		break;
    	case "Arrow":
    		if(maxItem.get("Arrow")>inventory.get(ID)) {
    			if(inventory.get("Arrow")+5 < maxItem.get("Arrow")) {
    				inventory.put("Arrow", inventory.get("Arrow")+5);
    			}
    			else {
    				inventory.put("Arrow", maxItem.get("Arrow"));
    			}
    			
    		}
    		break;
    	case "RegenPotion":
    		usable.put(usable.size(), ID);
    		break;
    	case "DamagePotion":
    		usable.put(usable.size(), ID);
    		break;
    	default:
    		if(maxItem.get(ID)==null || maxItem.get(ID)>inventory.get(ID)) {
    			inventory.put(ID, inventory.get(ID)+1);
    		}	
    	}
    }
    
    public void useBomb() {
    	if(inventory.get("Bomb")>0) {
    		game.dropBomb(getPosX(), getPosY());
    		inventory.put("Bomb", inventory.get("Bomb")-1);
    	}
    }
    
    public int getKeyAmount() {
    	return inventory.get("Key");
    }
    
    public Hashtable<String, Integer> getInventory(){
    	return inventory;
    }
    
    public Hashtable<Integer, String> getUsable(){
    	return usable;
    }
    
    public void useKey() {
    	inventory.put("Key", inventory.get("Key")-1);
    }
    
    @Override
    public int getDamage() {
    	return 3 + 2*inventory.get("DamageUp");
    }
}
