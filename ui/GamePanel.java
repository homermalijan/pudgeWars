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
		super();
		this.setComponents();
	}

	public void setComponents() {
		this.userPanel = new JPanel();
		this.opponentPanel = new JPanel();
	}
}