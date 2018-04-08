package Model;

public class Spike extends Block implements Runnable {
	private Player player;
	private int x;
	private int y;
	
	public Spike(int x, int y, Player player) {
		super(x, y, "P");
		this.player = player;
		this.x = x;
		this.y = y;
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
		while(true) {
			while(!player.isAtPosition(x, y)) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
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
