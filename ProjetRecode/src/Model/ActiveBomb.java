package Model;

import java.util.ArrayList;

public class ActiveBomb extends Item implements Runnable, Deletable, Activable {

	private ArrayList<DeletableObserver> observers = new ArrayList<DeletableObserver>();
	private Game game;
	
	public ActiveBomb(int x, int y, Game game) {
		super(x, y, "ActiveBomb");
		this.game = game;
		new Thread(this).start();
	}

	public void explode() {
		game.explode(this);
		notifyDeletableObserver();
	}
	
	@Override
	public boolean isObstacle() {
		return true;
	}

	@Override
	public void notifyDeletableObserver() {
        for (DeletableObserver o : observers) {
            o.delete(this, null);
        }
    }

	@Override
    public void attachDeletable(DeletableObserver po) {
        observers.add(po);
    }

	@Override
	public void run() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		explode();
		
	}

	@Override
	public void activate() {
		// TODO On pourrais faire en sorte de désactiver une bombe en la tapant.
		
	}

}
