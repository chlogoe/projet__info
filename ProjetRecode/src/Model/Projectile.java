package Model;

public class Projectile extends Entity implements Runnable{

	private int range;
	private int speed = 2;
	private Direction direction;
	private Game game;
	private Entity launcher;
	private boolean used = false;
	private int level;
	
	public Projectile(Entity entity, int range, Game game) {
		super(entity.getPosX(), entity.getPosY(), "Proj", 0, 2);
		if(range > 0) {
			this.range = range;
		}
		
		direction = entity.getDirection();
		this.game = game;
		this.launcher = entity;
		this.level = game.getLevel();
		new Thread(this).start();
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
			used = true;
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
		GameObject nextBlock = game.getBlockType(direction, this);
		if(nextBlock instanceof Entity && nextBlock != launcher && !(nextBlock instanceof Projectile)) {
			((Entity) nextBlock).sufferDamage(this.getDamage());
			used = true;
		}
	}

	@Override
	public void run() {
		for(int i  = 0;i< range;i++) {
			try {
				while(!game.isRunning()) {
					Thread.sleep(15);
				}
				attack();
				if(used || this.level != game.getLevel()) {
					break;
				}
				
				move(direction);
				if(used) {
					break;
				}
				Thread.sleep(200-20*(speed-1));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		notifyDeletableObserver();
	}
}

