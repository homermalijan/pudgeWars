import java.io.*;
import java.net.*;
import java.util.LinkedList;

public class GameServer extends Thread{
  //class attributes
  private LinkedList<Socket> clients = new LinkedList<Socket>();
  private ServerSocket serverSocket;

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
            System.out.println(client.getRemoteSocketAddress() + "connected...");
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
