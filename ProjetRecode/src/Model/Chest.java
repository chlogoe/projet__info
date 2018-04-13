package Model;


public class Chest extends Block {
	
	public Chest (int x, int y) {
		super (x,y,"C");
	}
	
	@Override
    public boolean isObstacle(Entity entity) {
        return true;
    }
}
