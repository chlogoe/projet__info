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
			//TODO Revenir sur la facon de regarder si il y a encore des mobs, ici les flèches comptent
		}
		else {
			return false;
		}
	}
}
