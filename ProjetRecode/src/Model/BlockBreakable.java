package Model;

import java.util.ArrayList;
import java.util.Random;

public class BlockBreakable extends GameObject implements Deletable, Activable {

    private ArrayList<DeletableObserver> observers = new ArrayList<DeletableObserver>();
    
    public BlockBreakable(int x, int y) {
        super(x, y, "B");
        Random rand = new Random();
        int tinted = rand.nextInt(200);
        if(tinted == 3) {
        	this.setID("B2");
        }
    }
    
    private Item getLoot() {
    	Random rand = new Random();
    	int n = rand.nextInt(100);
    	if(n < 15) {
			return new Item(this.getPosX(), this.getPosY(), "Bomb");
		}
		else if(n < 30) {
			return new Item(this.getPosX(), this.getPosY(),"Heart");
		}
		else if(n < 45) {
			return new Item(this.getPosX(), this.getPosY(),"Arrow");
		}
		else if(n < 50) {
			return new Item(this.getPosX(), this.getPosY(),"Plank");
		}
		else if(n < 55) {
			return new Item(this.getPosX(), this.getPosY(),"DamageUp");
		}
		else if(n < 60) {
			return new Potion(this.getPosX(), this.getPosY(),"HealthUp");
		}
		else if(n < 65) {
			return new Potion(this.getPosX(), this.getPosY(),"Armor");
		}
		else if(n < 75) {
			return new Potion(this.getPosX(), this.getPosY(),"RegenPotion");
		}
		else if(n < 85) {
			return new Potion(this.getPosX(), this.getPosY(),"DamagePotion");
		}
		else if(n < 100) {
			return new Potion(this.getPosX(), this.getPosY(),"Key");
		}
		else {
			return null;
		}
    }
    
    public void activate(){
            crush();
    }


    public void crush(){
        notifyDeletableObserver();
    }
    // //////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void attachDeletable(DeletableObserver po) {
        observers.add(po);
    }

    @Override
    public void notifyDeletableObserver() {
        for (DeletableObserver o : observers) {
        	if(this.getID() == "B2") {
        		o.delete(this, getLoot());
        		//TODO créer une véritable pool d'items
        	}
        	else {
        		o.delete(this, null);
        	}
        }
    }

    @Override
    public boolean isObstacle(Entity entity) {
        return true;
    }

}
