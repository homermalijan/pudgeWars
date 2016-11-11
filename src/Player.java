import java.awt.*;
import java.util.*;


public class Player extends GameObject{

	public Player(float x,float y,ObjectId id){
		super(x,y,id);
	}
	
	public void tick(LinkedList<GameObject> object){
		x += velX;
		y += velY; 
	}
	public void render(Graphics g){
		g.setColor(Color.green);
		g.fillOval((int)x,(int)y,32,32);
	}
	public Rectangle getBounds(){
		return new Rectangle((int)x,(int)y,32,32); 
	}
	
	
	  
} 
