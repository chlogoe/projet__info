package Model;

public abstract class Entity extends GameObject {
	private int lifePoints;
	private int damage;
	public Entity(int x, int y, String ID, int lifePoint) {
		super(x,y,ID);
		this.lifePoints = lifePoint;
	}
	
	public void move(int x, int y) {
		this.posX += x;
		this.posY += y;	
	}
	
	public boolean isObstacle() {
		return false;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public int getLifePoints() {
		return lifePoints;
	}
	
	
	public abstract void attack();
	
}
