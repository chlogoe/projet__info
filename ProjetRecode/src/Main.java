import java.io.IOException;

import Controller.Keyboard;
import Model.Game;
import View.Window;

public class Main {
    public static void main(String[] args) throws IOException {
//        Window window = new Window();
//
//        Game game = new Game(window);
		try {
			Window window = new Window();

	        Game game = new Game(window);
			Keyboard keyboard = new Keyboard(game, game.getPlayer());
			window.setKeyListener(keyboard);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
