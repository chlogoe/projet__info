package Model;

import java.util.ArrayList;

public abstract class Item extends GameObject implements Deletable{
	
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
}