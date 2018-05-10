package Model;


import java.util.Random;

import Model.Game;

public class MonstreCaC extends Entity implements Runnable{
	
	private Game game;

	
	public MonstreCaC(int x, int y, Game game) {
		super(x, y, "CaC", game);
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
		Direction playerDirection = checkPlayerDirection(game.getPlayer());
		if(playerDirection != null&&this.getHealth()>0) {
			Thread.sleep(250);
			game.getPlayer().sufferDamage(getDamage());
		}
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
		if(n <15) {
			return new Item(this.getPosX(), this.getPosY(), "Bomb");
		}
		else if(n < 30) {
			return new Item(this.getPosX(), this.getPosY(),"Heart");
		}
		else if(n < 40) {
			return new Item(this.getPosX(), this.getPosY(),"Arrow");
		}
		else if(n < 41) {
			return new Item(this.getPosX(), this.getPosY(),"Plank");
		}
		else if(n < 42) {
			return new Item(this.getPosX(), this.getPosY(),"DamageUp");
		}
		else if(n < 43) {
			return new Potion(this.getPosX(), this.getPosY(),"HealthUp");
		}
		else if(n < 44) {
			return new Potion(this.getPosX(), this.getPosY(),"Armour");
		}
		else if(n < 54) {
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
		Player player = null;
		while(game.isRunning() && player == null) {
			try {
				player = game.getPlayer();
			} catch (Exception e) {
				System.out.println("MonsterCaC ligne 71");
				e.printStackTrace();
			}
		}
		while (this.getHealth()>0){
			
			while(!game.isRunning()) {
				try {
					Thread.sleep(15);
				} catch (InterruptedException e) {
					System.out.println("MonsterCaC ligne 80");
					e.printStackTrace();
				}
			}
				
			int sleepTime = 280;
			if(game.playerInZone(this.getPosX(), this.getPosY(),5)) {
				int dx = player.getPosX()-this.getPosX();
				int dy = player.getPosY()-this.getPosY();
				if(Math.abs(dx) < Math.abs(dy)) {
					if(dy/Math.abs(dy) == -1) {
						move(Direction.Up);
					}
					else {
						move(Direction.Down);
					}
				}
				else {
					if(dx/Math.abs(dx) == -1) {
						move(Direction.Left);
					}
					else {
						move(Direction.Right);
					}
				}
			}
			else {
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
				
			}
			try {
				Thread.sleep(sleepTime);
				attack();
			} catch (Exception e) {
				System.out.println("MonsterCaC ligne 130");
				e.printStackTrace();
			}
		}
	}

	private Direction checkPlayerDirection(Player player) {
		if(player.isAtPosition(this.getPosX(), this.getPosY()-1)) {
			return Direction.Up;
		}
		else if (player.isAtPosition(this.getPosX(), this.getPosY()+1)) {
			return Direction.Down;
		}
		else if (player.isAtPosition(this.getPosX()-1, this.getPosY())) {
			return Direction.Left;
		}
		else if (player.isAtPosition(this.getPosX()+1, this.getPosY())) {
			return Direction.Right;
		}
		return null;
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
