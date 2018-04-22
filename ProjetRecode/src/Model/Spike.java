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
		try {
			while(this.level == game.getLevel() && game.getPlayer() != null) {
				while(!player.isAtPosition(x, y)) {
					if(this.level == game.getLevel()) {
						Thread.sleep(10);
					}
					else {
						break;
					}
				}
				if(this.level == game.getLevel()) {
				player.dealDamage(2);;
				}
				Thread.sleep(2000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
//TODO Revoir l'ensemble des object appelé et rendre le tout concordant (player et game.getPlayer() nottament)
