import java.awt.Canvas;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.*;

public class Game extends Canvas implements Runnable{
	private boolean running = false;
	private Thread thread;

	public static int WIDTH,HEIGHT;
	public static Handler handler;

	private void init(){
		WIDTH = getWidth();
		HEIGHT = getHeight();

		handler = new Handler();

		handler.addObject(new Player(50,50,ObjectId.Player));
		this.addKeyListener(new KeyInput(handler));
	 }

	public synchronized void start(){
		if(running)return;

		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public void run(){
		init();
		this.requestFocus();

		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0 ;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;

		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1){
				tick();
				updates++;
				delta--;
			}
			render();
			frames++;

			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				// System.out.println("FPS: " + frames + " TICKS: " + updates);
				frames = 0;
				updates = 0;
			}
		}
	}

	public void tick(){
		handler.tick();
	}

	public void render(){
		Toolkit.getDefaultToolkit().sync();
		 BufferStrategy bs  = this.getBufferStrategy();

		if(bs == null){
			this.createBufferStrategy(2);
			return;
		}

		Graphics g = bs.getDrawGraphics();

		ImageIcon bg = new ImageIcon("../img/bg.jpg");
		Image bgI = bg.getImage();
		//bgI= bgI.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH);
		//g.fillRect(0,0 ,getWidth(),getHeight());
		g.drawImage(bgI,0,0,null);

		handler.render(g);

		g.dispose();
		bs.show();

	}

	// public static void addPlayer(){
	// 	handler.addObject(new Player(50,50,ObjectId.Player));
	// }

	//add player function
	//redraw player function (remove previous then draw new players) using hashmap

}
