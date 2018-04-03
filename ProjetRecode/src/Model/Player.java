package Model;

public class Player extends GameObject {

    int lifes = 0;

    public Player(int x, int y, int maxBomb, int lifes) {
        super(x, y, 'P');
        this.lifes = lifes;
    }

    public void move(int X, int Y) {
        this.posX = this.posX + X;
        this.posY = this.posY + Y;
    }

   // //////////////////////////////////////////////////////////////////////////////////////


    @Override
    public boolean isObstacle() {
        return false;
    }
}
