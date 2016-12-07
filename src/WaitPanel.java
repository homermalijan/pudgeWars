import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.*;
import java.awt.image.*;

public class WaitPanel extends Canvas {

	BufferedImage image;

	public WaitPanel() {
		try{
			image = ImageIO.read(new File("../img/wait.jpg"));
		}
		catch(IOException e) {}
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(image, 0, 0, this);
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Testing");
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.add(new WaitPanel());
        frame.setPreferredSize(new Dimension(600,500));
        frame.pack();
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
	}
}