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
				Thread.sleep(15);
				game.startLevel();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}