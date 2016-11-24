import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.imageio.*;

public class GamePanel extends JPanel {

	public JPanel userPanel;
	public JPanel opponentPanel;
	private Frog frog;

	public GamePanel() {
		super(new GridLayout(1,2));
		this.setComponents();
	}

	public void setComponents() {
		this.userPanel = new JPanel();
		this.opponentPanel = new JPanel();
		this.frog = new Frog();

		// this.userPanel.add(new JLabel("user panel here"));
		userPanel.add(frog);
		System.out.println("Frog added.");
		this.opponentPanel.add(new JLabel("opponent panel here"));


		this.add(userPanel);
		this.add(opponentPanel);
	}
}