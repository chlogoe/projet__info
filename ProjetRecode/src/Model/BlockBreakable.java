package Model;

import java.util.ArrayList;
import java.util.Random;

public class BlockBreakable extends Block implements Deletable, Activable {

    private ArrayList<DeletableObserver> observers = new ArrayList<DeletableObserver>();
    private ArrayList<Item> items = new ArrayList<Item>();
    
    public BlockBreakable(int x, int y) {
        super(x, y, "B");
        Random rand = new Random();
        int tinted = rand.nextInt(200);
        if(tinted == 3) {
        	ID = "B2";
        }
        items.add(new Bomb(x,y));
        //items.add(new Key(x,y));
    }
    
    public void activate(){
            crush();
    }


    public void crush(){
        notifyDeletableObserver();
    }
    // //////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void attachDeletable(DeletableObserver po) {
        observers.add(po);
    }

    @Override
    public void notifyDeletableObserver() {
        for (DeletableObserver o : observers) {
        	if(ID == "B2") {
        		o.delete(this, items.get(0));
        	}
        	else {
        		o.delete(this, null);
        	}
        }
    }

    @Override
    public boolean isObstacle(Entity entity) {
        return true;
    }

}
