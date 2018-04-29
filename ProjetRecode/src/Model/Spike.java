package Model;

public class Spike extends Block implements Runnable {
	private Player player;
	private int level;
	private Game game;
	private int damage = 2;
	
	public Spike(int x, int y, Game game) {
		super(x, y, "P");
		try {
			this.player = game.getPlayer();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
				while(!player.isAtPosition(getPosX(), getPosY())) {
					if(this.level == game.getLevel()) {
						Thread.sleep(10);
					}
					else {
						break;
					}
				}
				if(this.level == game.getLevel()) {
				player.sufferDamage(damage);;
				}
				Thread.sleep(2000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setDamage(int damage) {
		this.damage = damage;
	}
}