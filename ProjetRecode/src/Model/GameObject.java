package Model;

public abstract class GameObject {
    private int posX;
    private int posY;
    private String ID;

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
    
    public void setPosX(int x){
    	this.posX = x;
    }
    
    public void setPosY(int y) {
    	this.posY = y;
    }
    
    public String getID() {
    	return this.ID;
    }
    
    public void setID(String ID) {
    	this.ID = ID;
    }
    
    public boolean isAtPosition(int x, int y) {
        return this.posX == x && this.posY == y;
    }

    public abstract boolean isObstacle(Entity entity);
}
