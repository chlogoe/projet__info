package Model;


import java.util.Random;

import Model.Game;

public class MonstreCaC extends Entity implements Runnable{
	
	private Game game;

	
	public MonstreCaC(int x, int y, Game game) {
		super(x, y, "Test", 5, 3);
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
		Direction playerDirection = checkPlayerDirection(game.getPlayer());
		if(playerDirection != null&&this.getHealth()>0) {
			Thread.sleep(200);
			switch(playerDirection) {
			case Left:
				game.interact(Direction.Left,this);
				break;
			case Right:
				game.interact(Direction.Right,this);
				break;
			case Up:
				game.interact(Direction.Up,this);
				break;
			case Down:
				game.interact(Direction.Down,this);
				break;
			}
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
		try{
			while (this.getHealth()>0 && game.getPlayer() != null){
				int sleepTime = 280;
				Player player = game.getPlayer();
				
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
				Thread.sleep(sleepTime);
				attack();
			}
		}catch(Exception e){
			e.printStackTrace();
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
