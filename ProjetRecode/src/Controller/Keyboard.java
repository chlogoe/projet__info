package Controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Model.Direction;
import Model.Game;
import Model.GameObject;
import Model.Player;

public class Keyboard implements KeyListener {
    private Game game;
    private Player player;

    public Keyboard(Game game, GameObject player) {
        this.game = game;
        this.player = (Player) player;
    }

    @Override
    public void keyPressed(KeyEvent event) {
        int key = event.getKeyCode();

        switch (key) {
        case KeyEvent.VK_D:
        	player.move(1, 0);
            break;
        case KeyEvent.VK_Q:
        	player.move(-1, 0);
            break;
        case KeyEvent.VK_S:
        	player.move(0, 1);
            break;
        case KeyEvent.VK_Z:
        	player.move(0, -1);
            break;
        case KeyEvent.VK_LEFT:
        	game.interact(Direction.Left, player);
        	break;
        case KeyEvent.VK_RIGHT:
        	game.interact(Direction.Right, player);
        	break;
        case KeyEvent.VK_UP:
        	game.interact(Direction.Up, player);
        	break;
        case KeyEvent.VK_DOWN:
        	game.interact(Direction.Down, player);
        	break;
        case KeyEvent.VK_E:
        	if(player.getBombAmount()>0) {
        		player.useBomb();
        		game.dropBomb(player.getPosX(), player.getPosY());
        	}
        	break;
        case KeyEvent.VK_H:
        	game.addMonster(10);
        	break;
        case KeyEvent.VK_I:
        	System.out.println(player.getInventory());
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
