package Controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Model.Direction;
import Model.Entity;
import Model.Game;
import Model.GameObject;
import Model.Player;

public class Keyboard implements KeyListener {
    private Game game;
    private Player player;

    private static final int player1 = 0;

    public Keyboard(Game game, GameObject player) {
        this.game = game;
        this.player = (Player) player;
    }

    @Override
    public void keyPressed(KeyEvent event) {
        int key = event.getKeyCode();

        switch (key) {
        case KeyEvent.VK_D:
            game.moveEntity(1, 0, player);
            break;
        case KeyEvent.VK_Q:
            game.moveEntity(-1, 0, player);
            break;
        case KeyEvent.VK_S:
            game.moveEntity(0, 1, player);
            break;
        case KeyEvent.VK_Z:
            game.moveEntity(0, -1, player);
            break;
        case KeyEvent.VK_P:
            game.playerPos(player1);
            break;
        case KeyEvent.VK_LEFT:
        	game.attack(Direction.Left, player);
        	break;
        case KeyEvent.VK_RIGHT:
        	game.attack(Direction.Right, player);
        	break;
        case KeyEvent.VK_UP:
        	game.attack(Direction.Up, player);
        	break;
        case KeyEvent.VK_DOWN:
        	game.attack(Direction.Down, player);
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
