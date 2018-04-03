package Model;

import java.util.ArrayList;

import View.Window;

public class MonstreTest extends Entity implements Activable, Deletable, Runnable{
	
	private ArrayList<DeletableObserver> observers = new ArrayList<DeletableObserver>();
	private Window window;
	
	public MonstreTest(int x, int y, Window window) {
		super(x, y, "Test", 2);
		new Thread(this).start();
		this.window = window;
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
			this.setLifePoints(0);
		}
		
	}

	@Override
	public synchronized void run() {
		try{
			while (this.getLifePoints()>0){
				this.move(0, 1);
				Thread.sleep(1000);
				window.update();
				System.out.println("Entity moved.");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	 

}
