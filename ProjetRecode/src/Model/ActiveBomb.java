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
		int counter = 0;
		int sleepTime = 20;
		while(counter < 25) {
			try {
				Thread.sleep(sleepTime*(25-counter));
				counter++;
				if(counter%2 == 0) {
					this.setID("ActiveBomb");
				}
				else {
					this.setID("ActiveBomb2");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			game.notifyView();
		}
		explode();
		
	}

	@Override
	public void activate() {
		//Ne fait rien
	}
}
