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

	public void collision(){
		for (int i=0; i<object.size(); i++){
			tempObject = object.get(i);
			if(tempObject.getId() == ObjectId.Tongue){
				Tongue temp = (Tongue) tempObject;
				temp.collision(object);
			}
		}
	}

	public void removeObject(){
		for(GameObject temp : object){
			if(temp.getUname().compareTo(GameClient.uName) != 0){
				this.object.remove(temp);
			}
		}//close for
	}//close removeObjects

	public void removeOne(String name){
		for(GameObject temp : object){
			if(temp.getUname().compareTo(name) == 0){
				this.object.remove(temp);
			}
		}//close for
	}//close removeObjects


}
