package Controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Model.Game;

public class Keyboard implements KeyListener {
    private Game game;

    private static final int player1 = 0;

    public Keyboard(Game game2) {
        this.game = game2;
    }

    @Override
    public void keyPressed(KeyEvent event) {
        int key = event.getKeyCode();

        switch (key) {
        case KeyEvent.VK_D:
            game.movePlayer(1, 0, player1);
            break;
        case KeyEvent.VK_Q:
            game.movePlayer(-1, 0, player1);
            break;
        case KeyEvent.VK_S:
            game.movePlayer(0, 1, player1);
            break;
        case KeyEvent.VK_Z:
            game.movePlayer(0, -1, player1);
            break;
        case KeyEvent.VK_P:
            game.playerPos(player1);
            break;
        case KeyEvent.VK_LEFT:
        	game.attack("LEFT");
        	break;
        case KeyEvent.VK_RIGHT:
        	game.attack("RIGHT");
        	break;
        case KeyEvent.VK_UP:
        	game.attack("UP");
        	break;
        case KeyEvent.VK_DOWN:
        	game.attack("DOWN");
        	break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
