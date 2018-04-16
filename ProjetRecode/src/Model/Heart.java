package Model;

public class Heart extends Item {

	private Player player;
	
	public Heart(int x, int y) {
		super(x, y, "Heart");
		this.player = player;
	}

	@Override
	public void activate() {
		this.notifyDeletableObserver();
	}

}
