import java.io.*;
import java.net.*;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Iterator;

public class GameServer extends Thread{
  //class attributes
  private LinkedList<Socket> clients = new LinkedList<Socket>();
  private static ServerSocket serverSocket;
  private static DatagramSocket server;
  private static HashMap<String, DatagramPacket> clientMap = new HashMap<String, DatagramPacket>();

  public GameServer(int port) throws IOException{
    serverSocket = new ServerSocket(port);
    serverSocket.setSoTimeout(120000);
  }//close constructor

  public void run(){
    System.out.println("listening at port " + serverSocket.getLocalPort() + "...");
    new Thread(){
      public void run(){

        while(true){
          //wait for connections
          try{
            final Socket client = serverSocket.accept();
            DataInputStream in = new DataInputStream(client.getInputStream());
            System.out.println(client.getRemoteSocketAddress() + " connected as " + in.readUTF());
            clients.add(client);

            //another thread for every connection to receive messages
            new Thread(){
              public void run(){
                try{
                  while(true){
                    DataInputStream in = new DataInputStream(client.getInputStream());
                    String message = in.readUTF();
                    System.out.println(message);
                    for(Socket s : clients){
                      DataOutputStream out = new DataOutputStream(s.getOutputStream());
                      out.writeUTF(message);
                      out.flush();
                    }//close for
                    System.out.println();
                  }//close while
                }catch(Exception e){
                  System.exit(1);
                  e.printStackTrace();
                }
              }//close run
            }.start();
          }catch(Exception e){
            System.out.println("Socket Timed Out");
          }
        }//close while
     }//close run
   }.start();
 }//close run

  public static void main(String args[]) {
    //Open Server Socket
    try {
      final int port = Integer.parseInt(args[0]);
      final int playerCount = Integer.parseInt(args[1]);
      Thread t = new GameServer(port);
      t.start();

      new Thread() {
        public void run(){
          try{
            server = new DatagramSocket(port+1);
            server.setSoTimeout(100);
          }catch(Exception e){}
          int teamCounter = 1;
          int teamOne = 0;
          int teamTwo = 0;
          //start tcp infinite loop
          while(true){
            byte[] buffer = new byte[256];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            try{
              server.receive(packet);
              String message = new String(buffer).trim();
              System.out.println(message);
              if(message.startsWith("Connect")){
                String name = message.split(" ")[1];
                if(clientMap.size() == playerCount){
                  System.out.println(name + " is trying to connect. cannot accomodate");
                  send(packet, "No");
                  continue;
                }
                clientMap.put(name, packet);
                if(teamCounter%2 == 1){
                  teamOne++;
                  send(packet, "1Connected " + InetAddress.getLocalHost());
                }else{
                  teamTwo++;
                  send(packet, "2Connected " + InetAddress.getLocalHost());
                }
                teamCounter++;
                System.out.println("connecting..");
                if(clientMap.size() == playerCount){
<<<<<<< HEAD
                  for (String key : clientMap.keySet()) {
                      send(clientMap.get(key), "Game Start!");
                      for (String key2 : clientMap.keySet()) {
                          send(clientMap.get(key), "playerName " + key2);

                      }
=======
                	byte[] buffer2 = new byte[256];
                	DatagramPacket packet2 = new DatagramPacket(buffer2, buffer2.length);
                	System.out.println("Players Complete");
                	broadcast("Start");
                  for(String tempKey : clientMap.keySet()){
                    for(String tempKey2 : clientMap.keySet()){
                      send(clientMap.get(tempKey),"playerName " + tempKey2);
                    }
>>>>>>> b106afd7d69213fe0f0d7672125410c651237a35
                  }
                }
              }else{
                //broadcast position only if there is enough player count
                if(clientMap.size() == playerCount){
                  broadcast(message + " " + InetAddress.getLocalHost());
                  System.out.println("Team 1: " + teamOne);
                  System.out.println("Team 2: " + teamTwo);
                }
              }

            }catch(Exception e){}
          }
        }
      }.start();


    } catch (IOException e) {
      System.out.println("Invalid port");
    } catch(ArrayIndexOutOfBoundsException e){
      System.out.println("Insufficient Arguments");
    }
  }//close main

  public static void broadcast(String msg){
    for(String key: clientMap.keySet()){
      DatagramPacket temp = clientMap.get(key);
      send(temp,msg);
    }
  }//close broadcasr

  public static void send(DatagramPacket temp, String msg){
    byte buffer[] = msg.getBytes();
    try{
      DatagramPacket packet = new DatagramPacket(buffer, buffer.length, temp.getAddress(), temp.getPort());
      server.send(packet);
    }catch(Exception e){}
  }
}//close class Server
