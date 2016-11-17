import java.io.*;
import java.net.*;
import java.util.LinkedList;

public class GameServer extends Thread{
  //class attributes
  private LinkedList<Socket> clients = new LinkedList<Socket>();
  private LinkedList<InetAddress> clientAddresses = new LinkedList<InetAddress>();
  private ServerSocket serverSocket;
  private DatagramSocket udpSocket;
  private DatagramSocket moveSocket;

  public GameServer(int port) throws IOException{
    serverSocket = new ServerSocket(port);
    udpSocket = new DatagramSocket(port+1);
    moveSocket = new DatagramSocket(port+2);
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

   //threads for receiving connection (saving addresses)
   new Thread(){
     public void run(){
        while(true){
          try{
            byte buffer[] = new byte[256];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            udpSocket.receive(packet);
            System.out.print("Request received...sending time...");
            String message = "\nWelcome to Frog Wars";
            buffer = message.getBytes();
            InetAddress address = packet.getAddress();
            clientAddresses.add(address);
            int port = packet.getPort();
            packet = new DatagramPacket(buffer, buffer.length, address, port);
            udpSocket.send(packet);
            System.out.println("Message sent");
          }catch(Exception err){
            err.printStackTrace();
          }
        }//close while
     }//close run
   }.start();

   //thread for receiving and sending client moves over other clients
   new Thread(){
     public void run(){
        while(true){
          try{
            byte buffer[] = new byte[256];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            moveSocket.receive(packet);
            String message = new String(buffer, 0, packet.getLength());
            buffer = message.getBytes();
            int port = packet.getPort();

            for(InetAddress ia : clientAddresses){
              if(ia != packet.getAddress())
              packet = new DatagramPacket(buffer, buffer.length, ia, port);
              moveSocket.send(packet);
            }

            System.out.println("Movement Sent to everyone");
          }catch(Exception err){
            err.printStackTrace();
          }
        }//close while
     }//close run
   }.start();

 }//close run

  public static void main(String args[]) {
    //Open Server Socket
    try {
      int port = Integer.parseInt(args[0]);
      Thread t = new GameServer(port);
      t.start();
    } catch (IOException e) {
      System.out.println("Invalid port");
    } catch(ArrayIndexOutOfBoundsException e){
      System.out.println("Insufficient Arguments");
    }
  }//close main
}//close class Server
