package Model;


import java.util.Random;

import View.Window;
import Model.Game;

public class MonstreCaC extends Entity implements Runnable{
	
	private Window window;
	private Game game;

	
	public MonstreCaC(int x, int y, Window window, Game game) {
		super(x, y, "Test", 5, 3);
		new Thread(this).start();
		this.window = window;
		this.game = game;
		super.setMaxHealth(5);
	}
	
	@Override
	public void move(int x, int y) {
		this.setDirection(getDirection(x,y));
		if(!game.checkObstacle(getPosX()+x, getPosY()+y, this)) {
    		this.setPosX(this.getPosX()+x);
    		this.setPosY(this.getPosY()+y);
    	}
	}

	@Override
	public void attack() throws Exception {
		Direction playerDirection = checkPlayerDirection(game.getPlayer());
		if(playerDirection != null) {
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
				int x = rand.nextInt(3)-1;
				int y = 0;
				Player player = game.getPlayer();
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
				attack();
				
				move(x, y);
				Thread.sleep(sleepTime);
				window.update();
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
	public void dealDamage(int damage) {
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
