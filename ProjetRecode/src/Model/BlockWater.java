package Model;

public class BlockWater extends Block {
	
	public BlockWater(int x, int y) {
		//super(x, y, "water");
		super(x,y,"W");
	}

	@Override
	public boolean isObstacle(Entity entity) {
		return false;
	}

}
