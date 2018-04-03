package Model;

import java.util.ArrayList;

public interface DeletableObserver {
    void delete(Deletable d, ArrayList<GameObject> loot);
}
