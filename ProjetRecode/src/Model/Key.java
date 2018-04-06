package Model;

public class Key extends Item {

	public Key(int x, int y) {
		super(x, y, "Key");
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isObstacle(Entity entity) {
		// TODO Auto-generated method stub
		return false;
	}

}
