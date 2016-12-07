import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.text.DefaultCaret;

public class GameClient{
  static DatagramSocket socket;
  private static String ipCopy;
  public static String uName;
  public static boolean isConnected = false;
  public static HashMap<String, String> playerMap = new HashMap<String, String>();

  public static void main(String [] args){
    JFrame gameFrame = new JFrame("Frog Wars");
    final Container gameContainer = gameFrame.getContentPane();
    gameContainer.setLayout(new BorderLayout());
    final JPanel gamePanel = new JPanel();
    JPanel chatPanel = new JPanel();

    final JTextArea chatDump = new JTextArea(3,400);
    final JScrollPane chatScroll = new JScrollPane(chatDump, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    final JTextField chatInput = new JTextField();
    DefaultCaret caret = (DefaultCaret)chatDump.getCaret();
    caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

    chatDump.setEditable(false);
    chatInput.setPreferredSize(new Dimension(580,20));
    gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    gameFrame.setPreferredSize(new Dimension(600,500));

	final Game game = new Game();
 	gameContainer.add(game, BorderLayout.CENTER);
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
  //=================================================
      new Thread(){
        public void run(){
          try{
            socket = new DatagramSocket();
            socket.setSoTimeout(100);
          }catch(Exception e){}
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
            int team = 0;
            message = message.trim();
            if(!isConnected && (message.startsWith("1Connected") || message.startsWith("2Connected"))){
              if(message.startsWith("1")){
                System.out.println("Team 1");
                team = 1;
                uName = "1" + uName;
              }
              else{
                System.out.println("Team 2");
                team = 2;
                uName = "2" + uName;
              }
              isConnected = true;
              System.out.println("You are now connected");
		  	    }else if(!isConnected && message.startsWith("No")){
              System.out.println("Cannot Accomodate more players :(");
              break;
            }else if(!isConnected){
              System.out.println("Connecting..");
              send("Connect " + username);
            }else if(isConnected){
              if(isConnected && message.startsWith("Start")){
                System.out.println("Game Start!");
              }else if(isConnected && message.startsWith("playerName")){
                System.out.println("player received");
                playerMap.put(message.split(" ")[1], "50 " + ((50*(playerMap.size()+1))));
              }else if(isConnected && message.startsWith("sent")){
                for(String tempKey : playerMap.keySet()){
                  if(tempKey.startsWith("2")){
                    String[] tempHolder = playerMap.get(tempKey).split(" ");
                    playerMap.put(tempKey, (Integer.parseInt(tempHolder[0])+420) + " " + tempHolder[1]);
                  }
                }
                game.start(team);
              }else if(isConnected && message.startsWith("dead")){
                String deadMeat = message.split(" ")[1];
                playerMap.put(deadMeat,"99999 99999");
              }else if(message!=null && !message.equals("") ){
                String[] temp = message.split(" ");
                playerMap.put(temp[0], temp[3] + " " + temp[4]);
                for(String tempKey : playerMap.keySet()){
                  System.out.println(tempKey + " is at " + playerMap.get(tempKey));
                }
                game.moveOthers();
              }//close if
              }
          }
        }//close run
      }.start();
  //=================================================

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
          // System.out.println(e.getActionCommand());
          chatInput.setText("");
        }
      }//close actionlistener
    );//close chat input action listener

    //=============================================================
    //chat recieve ; append to text area
    //=============================================================
    //Thread for receiving lines sent by the server
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

  	chatPanel.setLayout(new BorderLayout());
  	chatPanel.setPreferredSize(new Dimension(580,100));
  	chatPanel.add(chatScroll, BorderLayout.CENTER);
  	chatPanel.add(chatInput, BorderLayout.SOUTH);
  	gameContainer.add(chatPanel, BorderLayout.SOUTH);
  	gameFrame.pack();
  	gameFrame.setVisible(true);
  }//close main

  public static void send(String msg){
    try{
      byte[] buffer = msg.getBytes();
      // 	InetAddress address = InetAddress.getByName(msg.split(" ")[4]);
      InetAddress address = InetAddress.getByName(ipCopy);
      DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 8081);
      socket.send(packet);
    }catch(Exception e){}
  }
}//close main
