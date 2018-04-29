package Model;

public class Hole extends Block implements Activable{
	private boolean plancked  = false;
	private Game game;
	private String subID = "0000";
	
	public Hole(int X, int Y, Game game) {
		super(X,Y,"H");
		this.game = game;
	}

	/*
	 *Est-ce qu'on fait en sorte que la planche reste sur le block de vide tant que le joueur ne l'a pas récupéré ?
	 *
	 */
	@Override
	public boolean isObstacle(Entity entity) {
		if(plancked) {
			return false;
		}
		else {
		return true;
		}
	}

	@Override
	public void activate() {
		if(plancked) {
			plancked = false;
		}
		else {
			plancked = true;
		}
		
	}
	
	public void setSubID() {
		String subID = "";
		if(game.getBlockType(Direction.Up, this) instanceof Hole) {
			subID += "1";
		}
		else {
			subID += "0";
		}
		if(game.getBlockType(Direction.Right, this) instanceof Hole) {
			subID += "1";
		}
		else {
			subID += "0";
		}
		if(game.getBlockType(Direction.Down, this) instanceof Hole) {
			subID += "1";
		}
		else {
			subID += "0";
		}
		if(game.getBlockType(Direction.Left, this) instanceof Hole) {
			subID += "1";
		}
		else {
			subID += "0";
		}
		this.subID = subID;
	}
	
	public String getSubID() {
		return subID;
	}
}