package Model;

public class ActiveBomb extends Item implements Runnable {

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
	public boolean isObstacle(Entity entity) {
		return true;
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
}
