package Model;

public class Heart extends Item {
	
	public Heart(int x, int y) {
		super(x, y, "Heart");
	}

	@Override
	public void activate() {
		this.notifyDeletableObserver();
	}

}
