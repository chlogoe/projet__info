package Model;

public class Hole extends Block{
	
	public Hole(int X, int Y) {
		super(X,Y,"H");
	}
	
	@Override
	public boolean isObstacle(Entity entity) {
		return true;
	}
}