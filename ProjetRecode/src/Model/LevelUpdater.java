package Model;


public class LevelUpdater implements Runnable {
	
	private Game game;
	
	public LevelUpdater(Game game) {
		this.game = game;
		new Thread(this).start();
	}
	@Override
	public void run() {
		while(true) {
			try {
				if(game.isRunning()) {
					game.startLevel();
				}
				Thread.sleep(15);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}