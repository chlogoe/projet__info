package Model;

public class Potion extends Item implements Runnable {

	private Player player;
	
	public Potion(int x, int y, String ID) {
		super(x, y, ID);
	}
	
	public void use(Player player) {
		this.player = player;
		new Thread(this).start();
	}
	
	public void run() {
		if(getID() == "RegenPotion") {
			for(int i = 0;i<20;i++) {
				player.heal(0.1f);
				System.out.println(player.getHealth());
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		else if(getID() == "DamagePotion") {
			System.out.println(player.getDamage());
			player.setDamage(player.getDamage()+2);
			System.out.println(player.getDamage());
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			player.setDamage(player.getDamage()-2);
			System.out.println(player.getDamage());
		}
	}
	

}
