package Model;


import java.util.Random;

import Model.Game;

public class MonstreArcher extends Entity implements Runnable{
	
	private Game game;

	
	public MonstreArcher(int x, int y, Game game) {
		super(x, y, "Archer", 5, 3);
		new Thread(this).start();
		this.game = game;
		super.setMaxHealth(5);
	}
	
	@Override
	public void move(Direction direction) {
		this.setDirection(getDirection(direction.getX(),direction.getY()));
		if(!game.checkObstacle(direction, this)) {
    		this.setPosX(this.getPosX()+direction.getX());
    		this.setPosY(this.getPosY()+direction.getY());
    	}
	}

	@Override
	public void attack() throws Exception {
		game.throwProjectile(this);
		Thread.sleep(100);
	}
	
	public void kill() {
		 notifyDeletableObserver();
	 }
	
	@Override
    public void notifyDeletableObserver() {
        for (DeletableObserver o : observers) {
            o.delete(this, getLoot());
        }
    }
	
	private Item getLoot() {
		Random rand = new Random();
		int n = rand.nextInt(25);
		if(n == 3) {
			return new Heart(this.getPosX(), this.getPosY());
		}
		else if(n == 7) {
			return new Bomb(this.getPosX(), this.getPosY());
		}
		else {
			return null;
		}
	}

	@Override
	public synchronized void run() {
		Random rand = new Random();
		int shoot = 0;
		try{
			while (this.getHealth()>0 && game.getPlayer() != null){
				
				while(!game.isRunning()) {
					Thread.sleep(15);
				}
				
				int x = rand.nextInt(4);
				switch(x) {
				case 0:
					move(Direction.Up);
					break;
				case 1:
					move(Direction.Right);
					break;
				case 2:
					move(Direction.Down);
					break;
				case 3:
					move(Direction.Left);
					break;
				}
				Thread.sleep(300);
				if(shoot%3==0) {
					attack();
				}
				shoot++;
			}
				
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void sufferDamage(int damage) {
		int health = this.getHealth();
		if(health - damage > 0) {
			this.setHealth(health-damage);
		}
		else {
			this.setHealth(0);
			kill();
		}
	}
}
