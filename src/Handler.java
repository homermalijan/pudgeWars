import java.awt.*;
import java.util.*;


public class Handler{

	public LinkedList<GameObject> object = new LinkedList<GameObject>();

	private GameObject tempObject;
	
	public void tick(){
		for(int i = 0;i<object.size();i++){
			tempObject = object.get(i);
			
			tempObject.tick(object);
		}
	}
	
	public void render(Graphics g){
		for(int i = 0;i<object.size();i++){
			tempObject = object.get(i);
			
			tempObject.render(g);
		}
	}
	
	public void addObject(GameObject object){
		this.object.add(object);
	}
	public void removeObject(GameObject object){
		this.object.remove(object);
	}

	public void bound(){
		for(int xx=0;xx<Game.WIDTH+32;xx+= 32){
			addObject(new Test(xx,Game.HEIGHT-200,ObjectId.Test));
		}
		for(int xx=0;xx<Game.WIDTH+32;xx+= 32){
			addObject(new Test(xx,Game.HEIGHT-180,ObjectId.Test));
		}
		for(int xx=0;xx<Game.WIDTH+32;xx+= 32){
			addObject(new Test(xx,Game.HEIGHT-220,ObjectId.Test));
		}
		for(int xx=0;xx<Game.WIDTH+32;xx+= 32){
			addObject(new Test(xx,Game.HEIGHT-160,ObjectId.Test));
		}
		for(int xx=0;xx<Game.WIDTH+32;xx+= 32){
			addObject(new Test(xx,Game.HEIGHT-240,ObjectId.Test));
		}
	}
}
