package View;

import Model.GameObject;
import javax.swing.JPanel;
import Model.Player;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Inventory extends JPanel{
	
	public Inventory(){
		this.setFocusable(true);
		this.requestFocusInWindow();
	}
	
	public void paint(Graphics g) {
		for (int i=51; i<400;i++) {
			for (int j = 10; j<30; j++) {
				int x=i;
				int y=j;
				g.setColor(Color.cyan);
				g.fillRect(x, y, 32, 32);
				
			}
		}
	}
}