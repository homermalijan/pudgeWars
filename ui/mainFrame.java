import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.imageio.*;

public class MainFrame extends JFrame implements WindowListener {


	private Container container;
	private JPanel mainPanel;

	public MainFrame(String title){
		super(title);
		this.setPreferredSize(new Dimension(800,600));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.container = this.getContentPane();
		this.mainPanel = new JPanel();
		this.addWindowListener(this);

		this.pack();
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
	}	

	public static void main(String[] args) {
		MainFrame main = new MainFrame("Frog Wars");
	}

	public void windowClosing(WindowEvent e) {}
	public void windowActivated(WindowEvent e) {}
	public void	windowClosed(WindowEvent e) {}
	public void	windowDeactivated(WindowEvent e) {}
	public void	windowDeiconified(WindowEvent e) {}
	public void	windowIconified(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
}