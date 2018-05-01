package Model;

public class ActiveBomb extends Item implements Runnable {

	private Game game;
	
	public ActiveBomb(int x, int y, Game game) {
		super(x, y, "ActiveBomb1");
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
		int sleepTime = 15;
		while(counter < 20) {
			try {
				while(!game.isRunning()) {
					Thread.sleep(15);
				}
				
				Thread.sleep(sleepTime*(25-counter));
				counter++;
				int modulo = counter%5;
				switch(modulo) {
				case 0:
					this.setID("ActiveBomb1");
					break;
				case 1:
					this.setID("ActiveBomb2");
					break;
				case 2:
					this.setID("ActiveBomb3");
					break;
				case 3:
					this.setID("ActiveBomb4");
					break;
				case 4:
					this.setID("ActiveBomb5");
					break;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			game.getWindow().update();
		}
		explode();
		
	}

	@Override
	public void activate() {
		//Ne fait rien
	}
}
