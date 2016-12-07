import java.awt.Canvas;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.*;

public class Game extends Canvas implements Runnable{
	private boolean running = false;
	private Thread thread;
	private int N;
	public static int WIDTH,HEIGHT;

<<<<<<< HEAD
	Handler handler;

	public Game(int N){
		this.N = N;
=======
	Handler handler = new Handler();

	public Game(){
		// this.N = N;
>>>>>>> b106afd7d69213fe0f0d7672125410c651237a35
	}

	private void init(){
		WIDTH = getWidth();
		HEIGHT = getHeight();

<<<<<<< HEAD
=======
		handler = new Handler();

<<<<<<< HEAD




=======
		handler.addObject(new Player(50, 50, ObjectId.Player, "key"));
>>>>>>> b106afd7d69213fe0f0d7672125410c651237a35
		/*
>>>>>>> 4216a40f5d58487e0d8eb22e58d4b7d32c628d29
		for(String key : GameClient.playerMap.keySet()){
<<<<<<< HEAD
		  	String player = GameClient.playerMap.get(key);
		 	 String[] temp = player.split(" ");

		// 	 if(key.compareTo(GameClient.uName) != 0){

		 //x		 }

		}
		*/
=======
		   	String player = GameClient.playerMap.get(key);
		  	String[] temp = player.split(" ");
		 // 	if(key.equals(GameClient.uName)){
		   		handler.addObject(new Player(Integer.parseInt(temp[0]),Integer.parseInt(temp[1]),ObjectId.Player,key));
		 //		}
	 	//	else{
  		//		handler.addObject(new Player(Integer.parseInt(temp[0]),Integer.parseInt(temp[1]),null,key));
	 	//	}
		}
		
>>>>>>> b106afd7d69213fe0f0d7672125410c651237a35
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
			int a = 100;
<<<<<<< HEAD

			/*
			//
			for(int i=0;i<GameClient.playerMap.size();i++){
				handler.addObject(new Other(a,50,ObjectId.Other));
				a+=100;
			}
			*/
			handler.removeObject();
			for(String key : GameClient.playerMap.keySet()){
			  	String player = GameClient.playerMap.get(key);
			 	String[] temp = player.split(" ");
			  	if(key.compareTo(GameClient.uName) == 0){
			  		handler.addObject(new Player(Integer.parseInt(temp[0]),Integer.parseInt(temp[1]),ObjectId.Player,key));
		 		}
		 		else{
		 			handler.addObject(new Other(Integer.parseInt(temp[0]),Integer.parseInt(temp[1]),ObjectId.Other,key));
		 		}
		 	}




=======
>>>>>>> b106afd7d69213fe0f0d7672125410c651237a35
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
