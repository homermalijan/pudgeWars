import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.imageio.*;

public class GamePanel extends JPanel implements KeyListener {

	private JPanel userPanel;
	private JPanel opponentPanel;

	public GamePanel() {
		super(new GridLayout(1,2));
		this.setComponents();
	}

	public void setComponents() {
		this.userPanel = new JPanel();
		this.opponentPanel = new JPanel();

		this.userPanel.add(new JLabel("user panel here"));
		this.opponentPanel.add(new JLabel("opponent panel here"));


		this.add(userPanel);
		this.add(opponentPanel);
	}


	public void keyTyped(KeyEvent e) {}
	public void keyPressed(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
}