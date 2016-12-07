import java.awt.*;
import java.util.*;
import javax.swing.*;

public class Tongue extends GameObject{

	private String uname;
	private int team,tongue;
	private Handler handler;

	public Tongue(float x,float y,ObjectId id,String uname,int velX,Handler handler){
		super(x,y,id,uname);
		this.velX = velX;
		this.handler = handler;
	}

	public void tick(LinkedList<GameObject> object){
		if(x+velX>=Game.WIDTH) object.remove(this);
		x += velX;
		
		collision(object);
	}
	public void render(Graphics g){
		g.setColor(Color.PINK);
		g.fillRect((int)x,(int)y,32,32);
	}
	public Rectangle getBounds(){
		return new Rectangle((int)x,(int)y,32,32);
	}
	
	
	public void collision(LinkedList<GameObject> object){
		for(int i = 0; i < handler.object.size(); i++){
			GameObject tempObject = handler.object.get(i);

			if(tempObject.getId() == null){
				if(getBounds().intersects(tempObject.getBounds())){
					object.remove(this);
					object.remove(tempObject);
					
				}
			}
		}
	}



}
