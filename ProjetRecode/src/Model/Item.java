package Model; 

public abstract class Item extends GameObject{
	public Item(int x, int y, String ID) {
		super (x,y,ID);
	}
	
	public boolean isObstacle(Entity entity) {
		return false;
	}
}