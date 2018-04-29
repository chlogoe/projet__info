package Model;

import java.util.ArrayList;

public abstract class Entity extends GameObject implements Deletable, Damageable, Directable{
	
	protected ArrayList<DeletableObserver> observers = new ArrayList<DeletableObserver>();
	
	private int health;
	private int damage;
	private int maxHealth;
	private Direction direction = Direction.Up;
	
	public Entity(int x, int y, String ID, int health, int damage) {
		super(x,y,ID);
		this.health = health;
		this.damage = damage;
	}
	
	public abstract void move(Direction direction);
	
	public boolean isObstacle(Entity entity) {
		return true;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public abstract void sufferDamage(int damage);
	
	public int getHealth() {
		return health;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}
	
	public abstract void attack() throws Exception;
	
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
	
	@Override
    public void attachDeletable(DeletableObserver po) {
        observers.add(po);
    }
	
	@Override
	public Direction getDirection(int x, int y) {
		if(x == 1) {
			return Direction.Right;
		}
		else if(x == -1) {
			return Direction.Left;
		}
		else if(y == 1) {
			return Direction.Down;
		}
		else {
			return Direction.Up;
		}
	}
}
