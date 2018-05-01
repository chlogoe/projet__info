package Model;

public class TempEntity extends Entity {

	public TempEntity(int x, int y) {
		super(x, y, " ",0,0);
	}

	@Override
	public boolean isObstacle(Entity entity) {
		return false;
	}

	@Override
	public void notifyDeletableObserver() {
		for (DeletableObserver o : observers) {
            o.delete(this, null);
        }
		
	}

	@Override
	public void move(Direction direction) {
		
	}

	@Override
	public void sufferDamage(int damage) {
		
	}

	@Override
	public void attack() throws Exception {
	}

}
