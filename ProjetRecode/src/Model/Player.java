package Model;

public class Player extends Entity {

    int lifes = 0;

    public Player(int x, int y, int maxBomb, int lifes) {
        super(x, y, "Player", lifes);
        this.lifes = lifes;
    }

    public void move(int X, int Y) {
        this.posX = this.posX + X;
        this.posY = this.posY + Y;
    }

   // //////////////////////////////////////////////////////////////////////////////////////


    @Override
    public boolean isObstacle() {
        return true;
    }

@Override
public void attachDeletable(DeletableObserver po) {
	// TODO Auto-generated method stub
	
}

@Override
public void notifyDeletableObserver() {
	// TODO Auto-generated method stub
	
}

@Override
public void activate() {
	// TODO Auto-generated method stub
	
}

@Override
public void attack() {
	// TODO Auto-generated method stub
	
}
}
