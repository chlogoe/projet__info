package Model;

import java.util.Hashtable;

public class Player extends Entity {
    
    private Hashtable<String, Integer> inventory = new Hashtable<String, Integer>();
    private Hashtable<String, Integer> maxItem = new Hashtable<String, Integer>();
    private Hashtable<Integer, Potion> usable = new Hashtable<Integer, Potion>();
    
    private Game game;
    private int numberOfWeapon = 2;
    private int slot = 1;
    private float damage = 3;

    public Player(Game game) {
        super(-1, -1, "Player",game);
        setMaxHealth(10);
        setHealth(getMaxHealth());
        this.game = game;
        initializeInventory();
    }
    

   // //////////////////////////////////////////////////////////////////////////////////////
    
    private void initializeInventory() {
    	inventory.put("Bomb", 1);
    	inventory.put("Key", 1);
    	inventory.put("DamageUp", 0);
    	inventory.put("HealthUp", 0);
    	inventory.put("Armor", 1);
    	inventory.put("OneUp", 2);
    	inventory.put("Plank", 1);
    	inventory.put("Weapon", 1);
    	inventory.put("Arrow", 10);
    	maxItem.put("Bomb", 10);
    	maxItem.put("Key", 10);
    	maxItem.put("Arrow", 100);
    	//usable.put(1,new Potion(0,0,"DamagePotion"));
    	//usable.put(2,new Potion(0,0,"DamagePotion"));
    }
    
    @Override
    public void notifyDeletableObserver() {
    	for (DeletableObserver o : observers) {
            o.delete(this, null);
            System.out.println("Fin de la partie");
    		game.endGame();
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
     * Fonction qui g�re la prise de d�gat du joueur
     * V�rifie si la vie apr�s les d�gat est possible, sinon met la vie � z�ro
     * 
     * V�rifie ensuite, si la vie du joueur est nulle, si il a une vie suppl�mentaire
     */
    
    @Override
    public void sufferDamage(float damage) {
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
    	}
    	else if(health == 0) {
    		this.setHealth(this.getMaxHealth());
    		inventory.put("OneUp", inventory.get("OneUp")-1);
    	}
    }
    
    public void swapSlot(int slot) {
    	this.slot = slot;
    }
    
    public void useItem() {
    	if(usable.get(slot)!=null) {
    		usable.get(slot).use(this);
        	usable.remove(slot);
    	}
    }
    
    public void interract(Direction side) {
    	this.setDirection(side);
    	GameObject target  = game.getBlockType(side, this);
    	if(target instanceof Hole && inventory.get("Weapon") != 2) {
    		if(((Hole) target).isObstacle(this) && inventory.get("Plank") > 0){
    			inventory.put("Plank", inventory.get("Plank")-1);
    			((Hole) target).activate();
    		}
    	}
    	else if(target instanceof Chest) {
    		if(getKeyAmount()>0) {
    			((Chest) target).activate();
    			inventory.put("Key", inventory.get("Key")-1);
    		}
    	}
    	else if(target instanceof Entity && inventory.get("Weapon") == 1) {
    		((Entity) target).sufferDamage(this.getDamage());
    	}
    	else if(inventory.get("Weapon") == 2 && inventory.get("Arrow")>0){
    		game.throwProjectile(this,7, getDamage());
    		inventory.put("Arrow", inventory.get("Arrow")-1);
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
    
    
    public void heal(float healing) {
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
    	// TODO G�rer l'attaque du joueur par ici et non pas via la classe Game
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
    		inventory.put("HealthUp", inventory.get("HealthUp")+1);
    		heal(2);
    		break;
    	case "Arrow":
    		if(maxItem.get("Arrow")>inventory.get(ID)) {
    			if(inventory.get("Arrow")+10 < maxItem.get("Arrow")) {
    				inventory.put("Arrow", inventory.get("Arrow")+10);
    			}
    			else {
    				inventory.put("Arrow", maxItem.get("Arrow"));
    			}
    		}
    		break;
    	case "RegenPotion":
    		if(usable.size()<5) {
    			usable.put(usable.size()+1, (Potion) item);
    		}
    		break;
    	case "DamagePotion":
    		if(usable.size()<5) {
    			usable.put(usable.size()+1, (Potion) item);
    		}
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
    
    public int getBombAmount() {
    	return inventory.get("Bomb");
    }
    
    public int getArrowAmount() {
    	return inventory.get("Arrow");
    }
    
    public int getOneUpAmount() {
    	return inventory.get("OneUp");
    }
    
    public int getPlankAmount() {
    	return inventory.get("Plank");
    }
    
    public int getArmor() {
    	return inventory.get("Armor");
    }
    
    public int getWeapon() {
    	return inventory.get("Weapon");
    }
    
    public Hashtable<Integer, Potion> getUsable(){
    	return usable;
    }
    
    public void useKey() {
    	inventory.put("Key", inventory.get("Key")-1);
    }
    
    @Override
    public float getDamage() {
    	return damage + 2*inventory.get("DamageUp");
    }
    
    @Override
    public void setDamage(float f) {
    	this.damage = f-2*inventory.get("DamageUp");
    }
    
    public int getSlot() {
    	return slot; 
    }
    
    
}
