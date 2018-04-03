package Model;

public class BlockWater extends Block {
	
	public BlockWater(int x, int y) {
		//super(x, y, "water");
		super(x,y,"C");
	}

	@Override
	public boolean isObstacle() {
		return false;
	}

}
