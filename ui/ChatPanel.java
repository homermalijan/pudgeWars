import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.imageio.*;

public class ChatPanel extends JPanel implements KeyListener {

	private JTextField input;
	private JLabel output;

	public ChatPanel() {
		super(new GridLayout(2,1));
		this.setComponents();
	}

	public void setComponents() {
		this.input = new JTextField("Message here.");
		this.output = new JLabel("Output here.");

		this.add(output);
		this.add(input);
	}

	public void keyTyped(KeyEvent e) {}
	public void keyPressed(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
}