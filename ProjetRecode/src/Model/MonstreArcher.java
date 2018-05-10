package Model;


import java.util.Random;

import Model.Game;

public class MonstreArcher extends Entity implements Runnable{
	
	private Game game;

	
	public MonstreArcher(int x, int y, Game game) {
		super(x, y, "Archer", game);
		new Thread(this).start();
		this.game = game;
		setMaxHealth(5+game.getLevel());
		setHealth(getMaxHealth());
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
		game.throwProjectile(this,10);
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
		int n = rand.nextInt(100);
		if(n <10) {
			return new Item(this.getPosX(), this.getPosY(), "Bomb");
		}
		else if(n < 20) {
			return new Item(this.getPosX(), this.getPosY(),"Heart");
		}
		else if(n < 50) {
			return new Item(this.getPosX(), this.getPosY(),"Arrow");
		}
		else if(n < 51) {
			return new Item(this.getPosX(), this.getPosY(),"Plank");
		}
		else if(n < 52) {
			return new Item(this.getPosX(), this.getPosY(),"DamageUp");
		}
		else if(n < 53) {
			return new Potion(this.getPosX(), this.getPosY(),"HealthUp");
		}
		else if(n < 54) {
			return new Potion(this.getPosX(), this.getPosY(),"Armour");
		}
		else if(n < 59) {
			return new Potion(this.getPosX(), this.getPosY(),"RegenPotion");
		}
		else if(n < 64) {
			return new Potion(this.getPosX(), this.getPosY(),"DamagePotion");
		}
		else if(n < 74) {
			return new Potion(this.getPosX(), this.getPosY(),"Key");
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
	public void sufferDamage(float damage) {
		float health = this.getHealth();
		if(health - damage > 0) {
			this.setHealth(health-damage);
		}
		else {
			this.setHealth(0);
			kill();
		}
	}
}
