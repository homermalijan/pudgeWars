import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class KeyInput extends KeyAdapter{
	Handler handler;

	public KeyInput(Handler handler){
		this.handler = handler;
	}

	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();

		for(int i=0;i<handler.object.size();i++){
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getId() == ObjectId.Player){
				System.out.println(tempObject.getX() + " " + tempObject.getY());
				if(key == KeyEvent.VK_D){
					if(tempObject.getUname().startsWith("1") && (tempObject.getX()+6 > 95)) break;
					if(tempObject.getUname().startsWith("2") && (tempObject.getX()+6 > 500)) break;
					tempObject.setX(tempObject.getX()+6);
				}
				if(key == KeyEvent.VK_A){
					if(tempObject.getUname().startsWith("1") && (tempObject.getX()-6 < 0)) break;
					if(tempObject.getUname().startsWith("2") && (tempObject.getX()-6 < 420)) break;
					tempObject.setX (tempObject.getX()-6);
				}
				if(key == KeyEvent.VK_S){
					if(tempObject.getY()+6 > 290) break;
					tempObject.setY(tempObject.getY()+6);
				}
				if(key == KeyEvent.VK_W){
					if(tempObject.getY()-6 < 0) break;
					tempObject.setY(tempObject.getY()-6);
				}
				if(key == KeyEvent.VK_SPACE){
					Tongue tempT;
					if(tempObject.getUname().startsWith("1")){
						tempT = new Tongue(tempObject.getX()+50,tempObject.getY(),ObjectId.Tongue,"Tongue",5,this.handler);
						handler.addObject(tempT);	
					}
					else{
						tempT = new Tongue(tempObject.getX()-50,tempObject.getY(),ObjectId.Tongue,"Tongue",-5,this.handler);
						handler.addObject(tempT);
					}	
				}

				//=======================================================================
				try{
					// DatagramPacket packet = null;
					if(GameClient.isConnected){
						System.out.println(tempObject.getX() + " " + tempObject.getY());
						String message = GameClient.uName + " is at " + tempObject.getX() + " " + tempObject.getY();
						System.out.println(GameClient.playerMap.size());
						GameClient.send(message);
					}
				} catch(Exception ee){
					ee.printStackTrace();
				}
				//=======================================================================
			}
		}

		if(key == KeyEvent.VK_ESCAPE){
			System.exit(1);
		}
	}
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();

		for(int i=0;i<handler.object.size();i++){
			GameObject tempObject = handler.object.get(i);

			if(tempObject.getId() == ObjectId.Player){
				if(key == KeyEvent.VK_D)tempObject.setVelX(0);
				if(key == KeyEvent.VK_A)tempObject.setVelX (0);
				if(key == KeyEvent.VK_W)tempObject.setVelY(0);
				if(key == KeyEvent.VK_S)tempObject.setVelY(0);
			}
		}
	}

}
