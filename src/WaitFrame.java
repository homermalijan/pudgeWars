import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.*;
import java.awt.image.*;

public class WaitFrame extends JFrame {

	WaitPanel waitPanel;
	JPanel main, cards, card1, card2, card3;
	JButton prev, next;

	final static String STR1 = "card1";
	final static String STR2 = "card2";
	final static String STR3 = "card3";

	public WaitFrame() {

		main = new JPanel(new BorderLayout());
		cards = new JPanel(new CardLayout());
		card1 = new JPanel();
		card2 = new JPanel();
		card3 = new JPanel();

		prev = new JButton("<");
		next = new JButton(">");

		main.add(prev, BorderLayout.WEST);
		main.add(next, BorderLayout.EAST);

		this.add(main);
		this.setPreferredSize(new Dimension(600,500));
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
	}


}