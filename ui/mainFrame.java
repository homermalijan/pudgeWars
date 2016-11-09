import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.imageio.*;

public class mainFrame extends JFrame {


	private Container container;
	private  JPanel mainPanel;

	public mainFrame(String title){
		super(title);
		this.setPreferredSize(new Dimension(800,600));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.container = this.getContentPane();
		this.mainPanel = new JPanel();

		this.pack();
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
	}	

	public static void main(String[] args) {
		mainFrame main = new mainFrame("Frog Wars");
	}
}