package Model;

import java.util.ArrayList;

public abstract class Entity extends GameObject implements Deletable, Damageable, Directable{
	
	protected ArrayList<DeletableObserver> observers = new ArrayList<DeletableObserver>();
	
	private float health = 5;
	private float damage = 2;
	private int maxHealth;
	private Game game;
	private Direction direction = Direction.Up;
	
	public Entity(int x, int y, String ID, Game game) {
		super(x,y,ID);
		this.game = game;
	}
	
	public abstract void move(Direction direction);
	
	public boolean isObstacle(Entity entity) {
		return true;
	}
	
	public float getDamage() {
		return (float) (damage+(0.1*game.getLevel()));
	}
	
	public void setDamage(float damage) {
		this.damage = damage;
	}
	
	public float getHealth() {
		return health;
	}
	
	public void setHealth(float health) {
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
