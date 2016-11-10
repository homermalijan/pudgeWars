import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameClient{
  public static void main(String [] args){
    JFrame gameFrame = new JFrame("Frog Wars");
    Container gameContainer = gameFrame.getContentPane();
    gameContainer.setLayout(new BorderLayout());
    GamePanel gamePanel = new GamePanel();
    JPanel chatPanel = new JPanel();
    // Frog frog = new Frog();
    final JTextArea chatDump = new JTextArea(3,400);
    chatDump.setEditable(false);
    final JScrollPane chatScroll = new JScrollPane(chatDump);
    final JTextField chatInput = new JTextField();
    chatInput.setPreferredSize(new Dimension(780,20));
    final Scanner sc = new Scanner(System.in);

    gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setPreferredSize(new Dimension(800,500));

    try{
        final String username = args[2];
        String serverName = args[0]; //get IP address of server from first param
        int port = Integer.parseInt(args[1]); //get port from second param

        /* Open a ClientSocket and connect to ServerSocket */
        System.out.println("Connecting to " + serverName + " on port " + port);
        final Socket client = new Socket(serverName, port);

        //=============================================================
        //chat send
        chatInput.addActionListener(
          new ActionListener(){
            public void actionPerformed(ActionEvent event){
              try{
                String message = event.getActionCommand();
                OutputStream outToServer = client.getOutputStream();
                DataOutputStream out = new DataOutputStream(outToServer);
                out.writeUTF("\n" + username + ": " + message);
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
          }
        );//close chat input action listener
        //=============================================================

        //Thread for receiving lines sent by the server
        new Thread(){
          public void run(){
            try{
              while(true){
                InputStream inFromServer = client.getInputStream();
                DataInputStream in = new DataInputStream(inFromServer);
                String message = in.readUTF();
                chatDump.append(message);
                System.out.println(message);
              }
            }catch(Exception e){
              try{
                client.close();
              }catch(Exception e2){
                e2.printStackTrace();
              }
            }
          }
        }.start();
    }catch(IOException e){
        System.out.println("Cannot find (or disconnected from) Server");
        // System.exit(1);
    }catch(ArrayIndexOutOfBoundsException e){
        System.out.println("Usage: java GameClient <server ip> <port no.> <username>");
        System.exit(1);
    }


    chatPanel.setLayout(new BorderLayout());
    chatPanel.setPreferredSize(new Dimension(780,100));
    chatPanel.add(chatScroll, BorderLayout.CENTER);
    chatPanel.add(chatInput, BorderLayout.SOUTH);
    
    gameContainer.add(chatPanel, BorderLayout.SOUTH);
    gameContainer.add(gamePanel, BorderLayout.CENTER);
    gameFrame.pack();
    gameFrame.setVisible(true);
  }//close main
}//close main
