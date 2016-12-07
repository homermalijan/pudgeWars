import java.awt.Canvas;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.*;
import java.util.HashMap;

public class Game extends Canvas implements Runnable{
	private boolean running = false;
	private Thread thread;
	private Thread move;
	private int N;
	private int team;
	private Player play;
	public static int WIDTH,HEIGHT;

	private static HashMap<String,Player> frogs = new HashMap<String,Player>();

	Handler handler = new Handler();

	public Game(){
		// this.N = N;
	}

	private void init(){
		WIDTH = getWidth();
		HEIGHT = getHeight();

		handler = new Handler();

		// handler.addObject(new Player(50, 50, ObjectId.Player, "key"));
		System.out.println("i am going to render " + GameClient.playerMap.size() + " frogs");
		for(String key : GameClient.playerMap.keySet()){
		   	String player = GameClient.playerMap.get(key);
		  	String[] temp = player.split(" ");
		  	Player temp1;
		 	if(key.equals("1"+GameClient.uName) || key.equals("2"+GameClient.uName)){
					if(key.startsWith("1")) {
						temp1 = new Player(Integer.parseInt(temp[0]),Integer.parseInt(temp[1]),ObjectId.Player,key,1);
						handler.addObject(temp1);
					}
					else {
						temp1 = new Player(Integer.parseInt(temp[0]),Integer.parseInt(temp[1]),ObjectId.Player,key,2);
		 				handler.addObject(temp1);
		 			}
		 		}
	 		else{
  				if(key.startsWith("1")){ 
  					temp1 = new Player(Integer.parseInt(temp[0]),Integer.parseInt(temp[1]),null,key,1);
  					handler.addObject(temp1);
  					frogs.put(key,temp1);
				}	
				else{
					temp1 = new Player(Integer.parseInt(temp[0]),Integer.parseInt(temp[1]),null,key,2);
					handler.addObject(temp1);
	 				frogs.put(key,temp1);
	 			}
	 		}
		}
		

		this.addKeyListener(new KeyInput(handler));
	 }

	public synchronized void start(int team){
		if(running)return;
		this.team = team;
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

	public void moveOthers(){
					
		handler.removeObject();
		for(String key : GameClient.playerMap.keySet()){
		   	String player = GameClient.playerMap.get(key);
		  	String[] temp = player.split(" ");
		  	Player temp1;
		 	if(!key.equals("1"+GameClient.uName) || !key.equals("2"+GameClient.uName)){
				if(key.startsWith("1")){ 
  					temp1 = new Player(Integer.parseInt(temp[0]),Integer.parseInt(temp[1]),null,key,1);
  					handler.addObject(temp1);
  					frogs.put(key,temp1);
				}	
				else{
					temp1 = new Player(Integer.parseInt(temp[0]),Integer.parseInt(temp[1]),null,key,2);
					handler.addObject(temp1);
	 				frogs.put(key,temp1);
	 			}		
		 	}
	 		
		}
	            
    }

}
