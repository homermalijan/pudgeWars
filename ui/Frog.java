import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.imageio.*;



public class Frog extends JPanel implements KeyListener {

	private Image image;

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		try {
			image = ImageIO.read(new File(""));
		} 
		catch(Exception e) {
			System.out.println(e.getMessage());
		}

		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(Color.GREEN);
		g.drawImage(image, 0, 0, null);
		g2d.drawOval(150, 150, 50, 50);

	}

	public void keyTyped(KeyEvent e) {}
	public void keyPressed(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
}