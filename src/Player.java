import java.awt.*;
import java.util.*;
import javax.swing.*;

public class Player extends GameObject{

	private String uname;
	private int team;

	public Player(float x,float y,ObjectId id,String uname, int team){
		super(x,y,id,uname);
		this.team = team;
	}

	public void tick(LinkedList<GameObject> object){
		x += velX;
		y += velY;
	}
	public void render(Graphics g){
		ImageIcon frog;

		if(team == 1) frog = new ImageIcon("../img/red.png");
		else frog = new ImageIcon("../img/blue.png");
		Image frogI = frog.getImage();
		g.drawImage(frogI,(int)x,(int)y,null);
	}
	public Rectangle getBounds(){
		return new Rectangle((int)x,(int)y,32,32);
	}



}
