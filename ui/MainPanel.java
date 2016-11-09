import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.imageio.*;

public class MainPanel extends JPanel {

	private GamePanel gamePanel;
	private ChatPanel chatPanel;

	public MainPanel() {
		super(new BorderLayout());
		this.setComponents();
	}

	public void setComponents() {
		this.gamePanel = new GamePanel();
		this.chatPanel = new ChatPanel();

		this.add(gamePanel, BorderLayout.CENTER);
		this.add(chatPanel, BorderLayout.SOUTH);
	}

}