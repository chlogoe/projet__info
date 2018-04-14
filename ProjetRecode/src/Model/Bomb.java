package Model;

public class Bomb extends Item implements Activable {

	public Bomb(int x, int y) {
		super(x, y, "Bomb");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void activate() {
		this.notifyDeletableObserver();
	}
}
