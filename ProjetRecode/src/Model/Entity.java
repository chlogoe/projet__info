package Model;

public abstract class Entity extends GameObject implements Deletable, Activable, Directable{
	private int health;
	private int damage;
	private int maxHealth;
	private Direction direction = Direction.Up;
	
	public Entity(int x, int y, String ID, int health) {
		super(x,y,ID);
		this.health = health;
	}
	
	public void move(int x, int y) {
		this.posX += x;
		this.posY += y;
	}
	
	public abstract boolean isObstacle(Entity entity);
	
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
	
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	public Direction getDirection() {
		return direction;
	}
	
	public int getMaxHealth() {
		return maxHealth;
	}
	
	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}
}
