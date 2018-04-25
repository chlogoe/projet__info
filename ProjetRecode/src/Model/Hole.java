package Model;

public class Hole extends Block implements Activable{
	private boolean plancked  = false;
	
	public Hole(int X, int Y) {
		super(X,Y,"H");
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
}