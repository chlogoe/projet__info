package Model;

import java.util.ArrayList;
import java.util.Random;

public class Chest extends Block implements Activable, Deletable {
	
	private ArrayList<DeletableObserver> observers = new ArrayList<DeletableObserver>();
	
	public Chest (int x, int y) {
		super (x,y,"C");
	}
	
	public Item getLoot() {
		Random rand = new Random();
		int n = rand.nextInt(100);
		if(n<25) {
			return new Item(this.getPosX(), this.getPosY(),"DamageUp");
		}
		else if(n<50) {
			return new Item(this.getPosX(), this.getPosY(),"HealthUp");
		}
		else if(n<75) {
			return new Item(this.getPosX(), this.getPosY(),"Armor");
		}
		else if(n<100){
			return new Item(this.getPosX(), this.getPosY(),"Planch");
		}
		else {
			return null;
		}
	}
	
	@Override
    public boolean isObstacle(Entity entity) {
        return true;
    }

	@Override
	public void attachDeletable(DeletableObserver po) {
		observers.add(po);
	}

	@Override
	public void notifyDeletableObserver() {
		for (DeletableObserver o : observers) {
        	o.delete(this, getLoot());
        }
	}

	@Override
	public void activate() {
		System.out.println("I try to activate");
		notifyDeletableObserver();
	}
}
