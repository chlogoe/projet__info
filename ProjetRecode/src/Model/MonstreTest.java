package Model;

import java.util.ArrayList;
import java.util.Random;

import View.Window;
import Model.Game;

public class MonstreTest extends Entity implements Activable, Deletable, Runnable{
	
	private ArrayList<DeletableObserver> observers = new ArrayList<DeletableObserver>();
	private Window window;
	private Game game;
	
	public MonstreTest(int x, int y, Window window, Game game) {
		super(x, y, "Test", 2);
		new Thread(this).start();
		this.window = window;
		this.game = game;
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
		Random rand = new Random();
		try{
			while (this.getLifePoints()>0){
				int x = rand.nextInt(3)-1;
				int y = 0;
				if(x==0) {
					y  = rand.nextInt(3)-1;
				}
				game.moveEntity(x, y, this);
				Thread.sleep(250);
				window.update();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	@Override
	public boolean isObstacle() {
		return true;
	}
	 

}
