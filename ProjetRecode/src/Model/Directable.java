package Model;

public interface Directable {
	public Direction getDirection();
	public void setDirection(Direction direction);
	public Direction getDirection(int x, int y);
}
