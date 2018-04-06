package Model;

public class Door extends Block {

	private Game game;
	
	public Door(int x, int y, Game game) {
		super(x, y, "D");
		this.game = game;
	}

	@Override
	public boolean isObstacle(Entity entity) {
		if(game.getAmountEntities()>1) {
			return true;
		}
		else {
			return false;
		}
	}
}
