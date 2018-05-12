package Model;

public class BlockUnbreakable extends GameObject {

    public BlockUnbreakable(int X, int Y) {
        super(X, Y, "A");
    }

    @Override
    public boolean isObstacle(Entity entity) {
        return true;
    }
}
