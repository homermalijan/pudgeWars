import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.HashMap;
import java.util.Iterator;

public class GameClient{
  static DatagramSocket socket;
  private static String ipCopy;
  public static String uName;
  public static boolean isConnected = false;
  public static HashMap<String, String> playerMap = new HashMap<String, String>();

  public static void main(String [] args){
    JFrame gameFrame = new JFrame("Frog Wars");
    Container gameContainer = gameFrame.getContentPane();
    gameContainer.setLayout(new BorderLayout());
    JPanel gamePanel = new JPanel();
    JPanel chatPanel = new JPanel();

    final JTextArea chatDump = new JTextArea(3,400);
    final JScrollPane chatScroll = new JScrollPane(chatDump);
    final JTextField chatInput = new JTextField();

    chatDump.setEditable(false);
    chatInput.setPreferredSize(new Dimension(580,20));
    gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    gameFrame.setPreferredSize(new Dimension(600,500));

    try{
        final String username = args[2];
        final String serverName = args[0]; //get IP address of server from first param
        ipCopy = serverName;
        uName = username;
        final int port = Integer.parseInt(args[1]); //get port from second param

        /* Open a ClientSocket and connect to ServerSocket */
        System.out.println("Connecting to " + serverName + " on port " + port);
        final Socket client = new Socket(serverName, port);
        System.out.println("Connected as " + username);
//=============================================================
//Game thread
//=============================================================
        new Thread(){
          public void run(){
            try{
              socket = new DatagramSocket();
              socket.setSoTimeout(100);
            }catch(Exception e){}
//=============================================================
//game proper
//=============================================================
            while(true){
              byte[] buffer = null;
              DatagramPacket packet = null;
              try{
                Thread.sleep(1);
                buffer = new byte[256];
                packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
              }catch(Exception e){}

              String message = new String(buffer);
              message = message.trim();

              if(!isConnected && message.startsWith("Connected")){            //player succesfully connects
                isConnected = true;
                playerMap.put(username,"50 50");
                System.out.println("You are now connected");
                System.out.println(playerMap.size());
                for(String key : playerMap.keySet()){
                  String player = playerMap.get(key);
      		 	  	  System.out.println(key);
      		  	  }
  		  	    }else if(!isConnected && message.startsWith("No")){            //game is full
                System.out.println("Cannot Accomodate more players :(");
                break;
              }else if(!isConnected){                                       //send message to server; request to connect
                System.out.println("Connecting..");
                send("Connect " + username);
              }else if(isConnected){                                        //ingame interaction
                if(message.equals("Game Start!")){                          //game full; start game
                  System.out.println("Game Start!\nPlayers:\n");
                }else if(message.startsWith("playerName")){                 //list players
                  System.out.println(message.split(" ")[1]);
                  playerMap.put(message.split(" ")[1], "50 50");
                  //DRAW PLAYER HERE
                  // if(!message.split(" ")[1].equals(username)) Game.addPlayer();
                }else if(message!=null && !message.equals("") ){            //receive other player's movement
                  String[] temp = message.split(" ");
                  playerMap.put(temp[0], temp[3] + " " + temp[4]);
                  for (String key : playerMap.keySet()) {
                      System.out.println(key + " is at " + playerMap.get(key));
                  }
                  //EVERYTIME A PLAYER MOVES. remove previous then re-draw
                }
              }
            }//close while
          }//close run
        }.start();
//=============================================================
//chat connect
//=============================================================
    try{
      OutputStream outToServer = client.getOutputStream();
      DataOutputStream out = new DataOutputStream(outToServer);
      out.writeUTF(username);
    }catch(Exception e){
      e.printStackTrace();
    }
//=============================================================
//chat send
//=============================================================
    chatInput.addActionListener(
      new ActionListener(){
        public void actionPerformed(ActionEvent event){
          try{
            String message = event.getActionCommand();
            OutputStream outToServer = client.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);
            out.writeUTF(username + ": " + message);
            out.flush();
          }catch(Exception e){
            try{
              client.close();
            }catch(Exception e2){
              e2.printStackTrace();
            }
          }
          chatInput.setText("");
        }
      }//close actionlistener
    );//close chat input action listener

//=============================================================
//chat recieve ; append to text area
//=============================================================
      new Thread(){
        public void run(){
          try{
            while(true){
              InputStream inFromServer = client.getInputStream();
              DataInputStream in = new DataInputStream(inFromServer);
              String message = in.readUTF();
              chatDump.append(message+"\n");
              System.out.println(message);
            }
          }catch(Exception e){
            try{
              client.close();
            }catch(Exception e2){
              e2.printStackTrace();
            }
          }
        }//close run
      }.start();//close thread for receiving messages
    }catch(IOException e){
        System.out.println("Cannot find (or disconnected from) Server");
        System.exit(1);
    }catch(ArrayIndexOutOfBoundsException e){
        System.out.println("Usage: java GameClient <server ip> <port no.> <username>");
        System.exit(1);
    }

  	Game game = new Game(playerMap.size());
  	chatPanel.setLayout(new BorderLayout());
  	chatPanel.setPreferredSize(new Dimension(580,100));
  	chatPanel.add(chatScroll, BorderLayout.CENTER);
  	chatPanel.add(chatInput, BorderLayout.SOUTH);
  	gameContainer.add(chatPanel, BorderLayout.SOUTH);
  	gameContainer.add(game, BorderLayout.CENTER);
  	gameFrame.pack();
  	gameFrame.setVisible(true);
  	game.start();
  }//close main

//=============================================================
//Send message to server
//=============================================================
  public static void send(String msg){
    try{
      byte[] buffer = msg.getBytes();
      InetAddress address = InetAddress.getByName(ipCopy);
      DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 8081);
      socket.send(packet);
    }catch(Exception e){}
  }
}//close main
