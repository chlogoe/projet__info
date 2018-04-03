package Model;

public abstract class GameObject {
    protected int posX;
    protected int posY;
    protected String ID;

    public GameObject(int x, int y, String ID) {
        this.posX = x;
        this.posY = y;
        this.ID = ID;
    }

    public int getPosX() {
        return this.posX;
    }

    public int getPosY() {
        return this.posY;
    }
    
    public String getID() {
    	return this.ID;
    }
    
    public boolean isAtPosition(int x, int y) {
        return this.posX == x && this.posY == y;
    }

    public abstract boolean isObstacle();
}
