package Model;

public class BlockUnbreakable extends Block {

    public BlockUnbreakable(int X, int Y) {
        super(X, Y, "A");
    }

    @Override
    public boolean isObstacle() {
        return true;
    }
}
