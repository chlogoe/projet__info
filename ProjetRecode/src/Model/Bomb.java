package Model;

public class Bomb extends Item implements Activable {

	public Bomb(int x, int y) {
		super(x, y, "Bomb");
	}

	@Override
	public void activate() {
		this.notifyDeletableObserver();
	}
}
