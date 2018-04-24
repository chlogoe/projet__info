package View;

public class ViewUpdater implements Runnable {
	
	private Window window;
	
	public ViewUpdater(Window window) {
		this.window = window;
		new Thread(this).start();
	}
	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(15);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			window.update();
		}

	}

}
