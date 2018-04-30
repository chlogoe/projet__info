package Model;

public class Projectile extends Entity implements Runnable{

	private int range;
	private Direction direction;
	private Game game;
	
	public Projectile(Entity entity, int range, int damage, Game game) {
		super(entity.getPosX(), entity.getPosY(), "Proj", 0, damage);
		this.range = range;
		direction = entity.getDirection();
		this.game = game;
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
		this.setPosX(this.getPosX()+direction.getX());
		this.setPosY(this.getPosY()+direction.getY());
	}

	@Override
	public void sufferDamage(int damage) {
		//Pas d'effet

	}

	@Override
	public void attack() throws Exception {
		
	}

	@Override
	public void run() {
		for(int i  = 0;i< range;i++) {
			GameObject nextBlock = game.getBlockType(direction, this);
			if(nextBlock instanceof Entity) {
				((Entity) nextBlock).sufferDamage(3);
				i = range;
			}
			else {
				if(!game.checkObstacle(direction, this)) {
		    		move(direction);
		    	}
				else {
					i = range;
				}
			}
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		notifyDeletableObserver();
		
	}

}
