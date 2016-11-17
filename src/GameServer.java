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
   //
  //  //threads for receiving connection (saving addresses)
  //  new Thread(){
  //    public void run(){
  //       while(true){
  //         try{
  //           DatagramSocket udpSocket = new DatagramSocket(8081);
  //           byte buffer[] = new byte[256];
  //           DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
  //           udpSocket.receive(packet);
   //
  //           String message = "\nWelcome to Frog Wars";
  //           buffer = message.getBytes();
  //           InetAddress address = packet.getAddress();
   //
  //           clientAddresses.add(address);
  //           clientSockets.add(udpSocket);
   //
  //           System.out.println("hallu");
  //           int port = packet.getPort();
  //           packet = new DatagramPacket(buffer, buffer.length, address, port);
  //           udpSocket.send(packet);
  //         }catch(Exception err){
  //           err.printStackTrace();
  //         }
  //       }//close while
  //    }//close run
  //  }.start();
   //
  //  //thread for receiving and sending client moves over other clients
  //  new Thread(){
  //    public void run(){
  //       while(true){
  //         try{
  //           byte buffer[] = new byte[256];
  //           DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
  //           moveSocket.receive(packet);
   //
  //           String message = new String(packet.getData());
  //           System.out.println(packet.getAddress() +  " said: " + message);
  //           buffer = message.getBytes();
  //           int port = packet.getPort();
   //
  //           for(DatagramSocket s : clientSockets){
  //             // if(ia != packet.getAddress())
  //             System.out.println("send " + message);
  //             byte buffer2[] = new byte[256];
  //             buffer = message.getBytes();
  //             DatagramPacket packet2 = new DatagramPacket(buffer2, buffer2.length, packet.getAddress(), port+1);
  //             s.send(packet2);
  //           }
   //
  //           System.out.println("Movement Sent to everyone");
  //         }catch(Exception err){
  //           err.printStackTrace();
  //         }
  //       }//close while
  //    }//close run
  //  }.start();



 }//close run

  public static void main(String args[]) {
    //Open Server Socket
    try {
      int port = Integer.parseInt(args[0]);
      Thread t = new GameServer(port);
      t.start();

      new Thread() {
        public void run(){
          try{
            server = new DatagramSocket(8081);
            server.setSoTimeout(100);
          }catch(Exception e){}

          while(true){
            byte[] buffer = new byte[256];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            try{
              server.receive(packet);
              String message = new String(buffer).trim();
              System.out.println(message);
              if(message.startsWith("Connect")){
                String name = message.split(" ")[1];
                clientMap.put(name, packet);
                send(packet, "Connected");
                System.out.println("connecting..");
              }else{
                broadcast(message);
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
  }

  public static void send(DatagramPacket temp, String msg){
    byte buffer[] = msg.getBytes();
    try{
      DatagramPacket packet = new DatagramPacket(buffer, buffer.length, temp.getAddress(), temp.getPort());
      server.send(packet);
    }catch(Exception e){}
  }
}//close class Server
