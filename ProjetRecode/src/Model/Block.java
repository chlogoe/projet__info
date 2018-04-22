package Model;

public abstract class Block extends GameObject {
    public Block(int x, int y, String ID) {
        super(x, y, ID);
    }
    //TODO Il n'y a actuellement pas d'utilité à cette interface. En trouver une ou la supprimer
}
