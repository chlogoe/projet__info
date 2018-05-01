package Model;

public class Projectile extends Entity implements Runnable{

	private int range = 5;
	private int damage = 2;
	private int speed = 0;
	private Direction direction;
	private Game game;
	private Entity launcher;
	
	public Projectile(Entity entity, int speed, int range, int damage, Game game) {
		super(entity.getPosX(), entity.getPosY(), "Proj", 0, damage);
		if(range > 0) {
			this.range = range;
		}

		if(damage > 0) {
			this.damage = damage;
		}
		
		if(speed < 10 && speed > -10) {
			this.speed = speed;
		}
		
		direction = entity.getDirection();
		this.game = game;
		this.launcher = entity;
		new Thread(this).start();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void notifyDeletableObserver() {
		for (DeletableObserver o : observers) {
            o.delete(this, null);
        }
	}

	@Override
	public void move(Direction direction) {
		if(!game.checkObstacle(direction, this)) {
			this.setPosX(this.getPosX()+direction.getX());
			this.setPosY(this.getPosY()+direction.getY());
    	}
		else {
			notifyDeletableObserver();
		}
	}

	@Override
	public void sufferDamage(int damage) {
		//Pas d'effet
	}
	
	@Override
	public boolean isObstacle(Entity entity) {
		return false;
	}

	@Override
	public void attack() throws Exception {
		//TODO Revoir le système d'attaque de tout
	}

	@Override
	public void run() {
		for(int i  = 0;i< range;i++) {
			try {
				while(!game.isRunning()) {
					Thread.sleep(15);
				}
				GameObject nextBlock = game.getBlockType(direction, this);
				if(nextBlock instanceof Entity && nextBlock != launcher) {
					((Entity) nextBlock).sufferDamage(damage);
					i = range;
				}
				
				move(direction);
				Thread.sleep(200-20*(speed-1));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		notifyDeletableObserver();
	}
}

