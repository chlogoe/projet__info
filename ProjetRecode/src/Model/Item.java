package Model;

import java.util.ArrayList;

public class Item extends GameObject implements Deletable, Activable, Scorable{
	
	private ArrayList<DeletableObserver> observers = new ArrayList<DeletableObserver>();
	
	public Item(int x, int y, String ID) {
		super (x,y,ID);
	}
	
	public boolean isObstacle(Entity entity) {
		return false;
	}

	@Override
	public void attachDeletable(DeletableObserver po) {
		observers.add(po);
	}

	@Override
	public void notifyDeletableObserver() {
		for (DeletableObserver o : observers) {
        	o.delete(this, null);
        }
	}

	@Override
	public void activate() {
		this.notifyDeletableObserver();
	}
	
	@Override
	public int getScore() {
		switch(getID()) {
		case "Arrow" : case "Bomb" : case "Heart" : return 20;
		case "RegenPotion" : case "DamagePotion" : return 50;
		case "DamageUp" : case "HealthUp" : case "Plank" : case "Armor" : return 100;
		default : return 1;
		}
	}
}