package Model;

public abstract class GameObject {
    protected int posX;
    protected int posY;
    protected String ID;
    //protected int color;

    public GameObject(int X, int Y, String ID) {
        this.posX = X;
        this.posY = Y;
        //this.color = color;
        this.ID = ID;
    }

    public int getPosX() {
        return this.posX;
    }

    public int getPosY() {
        return this.posY;
    }
    /*
    public int getColor() {
        return this.color;
    }
	*/
    
    public String getID() {
    	return this.ID;
    }
    
    public boolean isAtPosition(int x, int y) {
        return this.posX == x && this.posY == y;
    }

    public abstract boolean isObstacle();
}
