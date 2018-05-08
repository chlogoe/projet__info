package Controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Model.Direction;
import Model.Game;
import Model.GameObject;
import Model.Player;
import Model.Projectile;

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
            case KeyEvent.VK_H:
            	game.addMonster(10);
            	break;
            case KeyEvent.VK_I:
            	System.out.println(player.getInventory());
            	break;
            case KeyEvent.VK_A:
            	player.switchWeapon();
            	System.out.println("Switched weapon");
            	break;
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
