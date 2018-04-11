package Model;

public class Spike extends Block implements Runnable {
	private Player player;
	private int x;
	private int y;
	private int level;
	private Game game;
	
	public Spike(int x, int y, Player player, Game game) {
		super(x, y, "P");
		this.player = player;
		this.x = x;
		this.y = y;
		this.level = game.getLevel();
		this.game = game;
		new Thread(this).start();
	}

	@Override
	public boolean isObstacle(Entity entity) {
		if(entity instanceof Player) {
			return false;
		}
		else {
			return true;
		}
	}

	@Override
	public void run() {
		while(this.level == game.getLevel()) {
			while(!player.isAtPosition(x, y)) {
				if(this.level == game.getLevel()) {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				else {
					break;
				}
				
			}
			player.activate();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
