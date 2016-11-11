  import java.awt.*;
import java.util.*;


public class Test extends GameObject{

	public Test(float x,float y,ObjectId id){
		super(x,y,id);
	}
	
	public void tick(LinkedList<GameObject> object){
	
	}
	public void render(Graphics g){
		g.setColor(Color.BLUE);
		g.fillRect((int)x,(int)y,32,32);
	}
	public Rectangle getBounds(){
		return new Rectangle((int)x,(int)y,32,32); 
	}
	
	
	  
} 
