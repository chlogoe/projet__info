package Model;

public class TempEntity extends Entity {

	public TempEntity(int x, int y, Game game) {
		super(x, y, " ", game);
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
	public void sufferDamage(float damage) {
		
	}

	@Override
	public void attack() throws Exception {
	}

}
