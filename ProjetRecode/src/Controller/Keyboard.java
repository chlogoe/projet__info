package Controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Model.Direction;
import Model.Game;
import Model.Player;

public class Keyboard implements KeyListener {
    private Game game;
    private Player player;

    public Keyboard(Game game) {
        this.game = game;
        this.player = game.getPlayer();
    }

    @Override
    public void keyPressed(KeyEvent event) {
        int key = event.getKeyCode();
        if(key == KeyEvent.VK_P) {
        	game.changeRunning();
        }
        if(game.isRunning()) {
        	switch (key) {
            case KeyEvent.VK_D:
            	player.move(Direction.Right);
                break;
            case KeyEvent.VK_Q:
            	player.move(Direction.Left);
                break;
            case KeyEvent.VK_S:
            	player.move(Direction.Down);
                break;
            case KeyEvent.VK_Z:
            	player.move(Direction.Up);
                break;
            case KeyEvent.VK_LEFT:
            	player.interract(Direction.Left);
            	break;
            case KeyEvent.VK_RIGHT:
            	player.interract(Direction.Right);
            	break;
            case KeyEvent.VK_UP:
            	player.interract(Direction.Up);
            	break;
            case KeyEvent.VK_DOWN:
            	player.interract(Direction.Down);
            	break;
            case KeyEvent.VK_E:
            	player.useBomb();
            	break;
            case KeyEvent.VK_A:
            	player.switchWeapon();
            	System.out.println("Switched weapon");
            	break;
            case KeyEvent.VK_ENTER:
            	player.useItem();
            	break;
            case KeyEvent.VK_1: case KeyEvent.VK_2: case KeyEvent.VK_3: case KeyEvent.VK_4: case KeyEvent.VK_5: case KeyEvent.VK_6: case KeyEvent.VK_7: case KeyEvent.VK_8: case KeyEvent.VK_9:
            	player.swapSlot(key-48);
            }
        }  
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
