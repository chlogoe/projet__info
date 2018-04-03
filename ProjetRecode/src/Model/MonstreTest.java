package Model;

import java.util.ArrayList;

public class MonstreTest extends Entity{
	
	private ArrayList<DeletableObserver> observers;
	
	public MonstreTest(int x, int y) {
		super(x, y, "Test", 2);
	}

	@Override
	public void attack() {
		// TODO Auto-generated method stub

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
		int lifePoints = this.getLifePoints();
		if(lifePoints > 0) {
			this.setLifePoints(lifePoints-1);
		}
		else {
			kill();
		}
		
	}
	 public void kill() {
		 notifyDeletableObserver();
	 }

}
