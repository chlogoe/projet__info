package Model;

public class Door extends GameObject {

	private Game game;
	
	public Door(int x, int y, Game game) {
		super(x, y, "D");
		this.game = game;
	}

	@Override
	public boolean isObstacle(Entity entity) {
		if(game.getAmountEntities()>0) {
			return true;
		}
		else {
			return false;
		}
	}
}
