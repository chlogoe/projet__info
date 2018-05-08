package Model;

import java.util.ArrayList;
import java.util.Random;

public class BlockBreakable extends Block implements Deletable, Activable {

    private ArrayList<DeletableObserver> observers = new ArrayList<DeletableObserver>();
    private ArrayList<Item> items = new ArrayList<Item>();
    
    public BlockBreakable(int x, int y) {
        super(x, y, "B");
        Random rand = new Random();
        int tinted = rand.nextInt(200);
        if(tinted == 3) {
        	this.setID("B2");
        }
        items.add(getLoot());
        //items.add(new Key(x,y));
    }
    
    private Item getLoot() {
    	Random rand = new Random();
    	int n = rand.nextInt(100);
		if(0 <=n && n <10) {
			return new Item(this.getPosX(), this.getPosY(), "Heart");
		}
		else if(10 <= n && n < 20) {
			return new Item(this.getPosX(), this.getPosY(),"Arrow");
		}
		else if(20 <= n && n < 25) {
			return new Item(this.getPosX(), this.getPosY(),"DamageUp");
		}
		else if(25 <= n && n < 27) {
			return new Item(this.getPosX(), this.getPosY(),"HealthUp");
		}
		else if(30<=n && n<70) {
			return null;
		}
		else if(70 <= n && n < 72) {
			return new Item(this.getPosX(), this.getPosY(),"Armor");
		}
		else if(75 <= n && n < 80) {
			return new Item(this.getPosX(), this.getPosY(),"RegenPotion");
		}
		else if(80 <= n && n < 85) {
			return new Item(this.getPosX(), this.getPosY(),"DamagePotion");
		}
		else {
			return new Item(this.getPosX(), this.getPosY(),"Bomb");
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
        		o.delete(this, items.get(0));
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
