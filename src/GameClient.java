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
<<<<<<< HEAD
    JPanel gamePanel = new JPanel();
    JPanel chatPanel = new JPanel();

=======
    GamePanel gamePanel = new GamePanel();
    JPanel chatPanel = new JPanel();
    // Frog frog = new Frog();
>>>>>>> d3c84ce701ad2a4fc273121acb33662623228890
    final JTextArea chatDump = new JTextArea(3,400);
    final JScrollPane chatScroll = new JScrollPane(chatDump);
    final JTextField chatInput = new JTextField();

    chatDump.setEditable(false);
    chatInput.setPreferredSize(new Dimension(780,20));
    gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    gameFrame.setPreferredSize(new Dimension(800,500));

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
            message = message.trim();
            if(!isConnected && message.startsWith("Connected")){
              isConnected = true;
              System.out.println("You are now connected");
            }else if(!isConnected && message.startsWith("No")){
              System.out.println("Cannot Accomodate more players :(");
              break;
            }else if(!isConnected){
              System.out.println("Connecting..");
              send("Connect " + username);
            }else if(isConnected){
              if(message!=null && !message.equals("") ){
                System.out.println(message);
                String[] temp = message.split(" ");
                playerMap.put(temp[0], temp[1] + " " + temp[2]);
              }
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


<<<<<<< HEAD
  	Game game = new Game();
  	chatPanel.setLayout(new BorderLayout());
  	chatPanel.setPreferredSize(new Dimension(780,100));
  	chatPanel.add(chatScroll, BorderLayout.CENTER);
  	chatPanel.add(chatInput, BorderLayout.SOUTH);
  	gameContainer.add(chatPanel, BorderLayout.SOUTH);
  	gameContainer.add(game, BorderLayout.CENTER);
  	gameFrame.pack();
  	gameFrame.setVisible(true);
  	game.start();
=======
    chatPanel.setLayout(new BorderLayout());
    chatPanel.setPreferredSize(new Dimension(780,100));
    chatPanel.add(chatScroll, BorderLayout.CENTER);
    chatPanel.add(chatInput, BorderLayout.SOUTH);
    
    gameContainer.add(chatPanel, BorderLayout.SOUTH);
    gameContainer.add(gamePanel, BorderLayout.CENTER);
    gameFrame.pack();
    gameFrame.setVisible(true);
>>>>>>> d3c84ce701ad2a4fc273121acb33662623228890
  }//close main

  public static void send(String msg){
    try{
      byte[] buffer = msg.getBytes();
      // InetAddress address = InetAddress.getByName(msg.split(" ")[4]);
      InetAddress address = InetAddress.getByName(ipCopy);
      DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 8081);
      socket.send(packet);
    }catch(Exception e){}
  }
}//close main
