package Model;

public abstract class Entity extends GameObject implements Deletable, Activable{
	private int health;
	private int damage;
	public Entity(int x, int y, String ID, int health) {
		super(x,y,ID);
		this.health = health;
	}
	
	public void move(int x, int y) {
		this.posX += x;
		this.posY += y;	
	}
	
	public abstract boolean isObstacle();
	
	public int getDamage() {
		return damage;
	}
	
	public int getHealth() {
		return health;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}
	
	public abstract void attack();
	
}
