package Model;


import java.util.Random;

import View.Window;
import Model.Game;

public class MonstreCaC extends Entity implements Runnable{
	
	private Window window;
	private Game game;

	
	public MonstreCaC(int x, int y, Window window, Game game) {
		super(x, y, "Test", 5);
		new Thread(this).start();
		this.window = window;
		this.game = game;
		super.setMaxHealth(5);
	}

	@Override
	public void attack() {
		// TODO Auto-generated method stub

	}
	
	public void kill() {
		 notifyDeletableObserver();
	 }
	
	@Override
    public void notifyDeletableObserver() {
        for (DeletableObserver o : observers) {
            o.delete(this, null);
        }
    }

	@Override
	public void activate() {
		int health = this.getHealth();
		if(health > 1) {
			this.setHealth(health-1);
		}
		else {
			this.setHealth(0);
			kill();
		}
	}

	@Override
	public synchronized void run() {
		Random rand = new Random();
		try{
			while (this.getHealth()>0){
				int sleepTime = 250;
				int x = rand.nextInt(3)-1;
				int y = 0;
				Player player = (Player) game.getPlayer();
				if(x==0) {
					y  = rand.nextInt(3)-1;
				}
				
				if(game.playerInZone(this.getPosX(), this.getPosY(),5)) {
					int dx = player.getPosX()-this.getPosX();
					int dy = player.getPosY()-this.getPosY();
					if(Math.abs(dx) < Math.abs(dy)) {
						y = dy/Math.abs(dy);
						x = 0;
					}
					else {
						x = dx/Math.abs(dx);
						y = 0;
					}
				}
				game.moveEntity(x, y, this);
				Thread.sleep(sleepTime);
				window.update();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
