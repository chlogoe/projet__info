package Model;

public interface Deletable {
    void attachDeletable(DeletableObserver po);

    void notifyDeletableObserver();
}
