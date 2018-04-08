package Model;

public class Key extends Item implements Activable {

	public Key(int x, int y) {
		super(x, y, "Key");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void activate() {
		this.notifyDeletableObserver();
		
	}

}
