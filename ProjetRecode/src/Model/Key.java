package Model;

public class Key extends Item implements Activable {

	public Key(int x, int y) {
		super(x, y, "Key");
	}

	@Override
	public void activate() {
		this.notifyDeletableObserver();
		
	}

}
