import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.imageio.*;

public class MainPanel extends JPanel {

	private JPanel gamePanel;
	private JPanel chatPanel;

	public MainPanel() {
		super(new BorderLayout());
		this.setComponents();
	}

	public void setComponents() {
		this.gamePanel = new JPanel();
		this.chatPanel = new JPanel();

		this.add(gamePanel, BorderLayout.CENTER);
		this.add(chatPanel, BorderLayout.SOUTH);
	}


}