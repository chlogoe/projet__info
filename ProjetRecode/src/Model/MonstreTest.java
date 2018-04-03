package Model;

import java.util.ArrayList;

public class MonstreTest extends Entity implements Activable, Deletable{
	
	private ArrayList<DeletableObserver> observers = new ArrayList<DeletableObserver>();
	
	public MonstreTest(int x, int y) {
		super(x, y, "Test", 2);
	}

	@Override
	public void attack() {
		// TODO Auto-generated method stub

	}
	
	public void kill() {
		 notifyDeletableObserver();
	 }
	
	@Override
    public void notifyDeletableObserver() {
        for (DeletableObserver o : observers) {
            o.delete(this, null);
            System.out.println("Entity deleted");
        }
    }

	@Override
    public void attachDeletable(DeletableObserver po) {
        observers.add(po);
    }
	


	@Override
	public void activate() {
		int lifePoints = this.getLifePoints();
		if(lifePoints > 1) {
			this.setLifePoints(lifePoints-1);
		}
		else {
			kill();
		}
		
	}
	 

}
