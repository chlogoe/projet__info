import java.io.IOException;

import Controller.Keyboard;
import Model.Game2;
import View.Window;

public class Main {
    public static void main(String[] args) throws IOException {
        Window window = new Window(30);

        Game2 game = new Game2(window);
        Keyboard keyboard = new Keyboard(game);
        window.setKeyListener(keyboard);
    }
}
