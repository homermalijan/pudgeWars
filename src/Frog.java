import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.imageio.*;
import java.util.Random;
import javax.swing.Timer;



public class Frog extends JPanel implements ActionListener, KeyListener {

	// private Image image;

	public Timer t = new Timer(5, this);

	private int xPos = 0;
	private int yPos = 0;
	private int xAdd = 0;
	private int yAdd = 0;

	public Frog() {
		this.t.start();

		this.addKeyListener(this);
		this.setFocusable(true);
		this.setFocusTraversalKeysEnabled(false);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D)g;
		g2d.fill(new Ellipse2D.Double(xPos, yPos, 40, 40)) ;

		
	}

	public void actionPerformed(ActionEvent e) {
		repaint();
		xPos+= xAdd;
		yPos+= yAdd;
	}

	public void moveLeft() {
		yAdd = 2;
		xAdd = 0;

	}

	public void moveRight() {
		yAdd = -2;
		xAdd = 0;
	}

	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();

		if(code == KeyEvent.VK_LEFT) {
			moveLeft();
			System.out.println("frog moved left");
		}

		if(code == KeyEvent.VK_RIGHT) {
			moveRight();
			System.out.println("frog moved right");
		}
		
	}

	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
}