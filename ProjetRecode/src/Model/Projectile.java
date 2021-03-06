package Model;

public class Projectile extends Entity implements Runnable{

	private int range;
	private int speed = 2;
	private Game game;
	private Entity launcher;
	private boolean used = false;
	private int level;
	
	public Projectile(Entity entity, int range,float damage, Game game) {
		super(entity.getPosX(), entity.getPosY(), "Proj", game);
		if(range > 0) {
			this.range = range;
		}
		super.setDamage(damage);
		super.setDirection(entity.getDirection());
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
	public void sufferDamage(float damage) {
		//Pas d'effet
	}
	
	@Override
	public boolean isObstacle(Entity entity) {
		return false;
	}

	@Override
	public void attack() throws Exception {
		GameObject nextBlock = game.getBlockType(getDirection(), this);
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
				
				move(getDirection());
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

