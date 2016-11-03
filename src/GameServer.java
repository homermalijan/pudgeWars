import java.io.*;
import java.net.*;
import java.util.LinkedList;

public class GameServer extends Thread{
  //class attributes
  private ServerSocket serverSocket;

  public GameServer(int port) throws IOException{
    serverSocket = new ServerSocket(port);
    serverSocket.setSoTimeout(120000);
  }//close constructor


 public void run(){
   final LinkedList<Socket> clients = new LinkedList<Socket>();

   System.out.println("listening at port " + serverSocket.getLocalPort() + "...");
   new Thread(){
     public void run(){
        while(true){
          //wait for connections
          try{
            final Socket server = serverSocket.accept();
            System.out.println("connected");
            clients.add(server);
            //another thread for every connection to receive
            new Thread(){
              public void run(){
                try{
                  while(true){
                    DataInputStream in = new DataInputStream(server.getInputStream());
                    System.out.println(in.readUTF());
                    for(Socket s : clients){
                      if(s != server){
                        DataOutputStream out = new DataOutputStream(s.getOutputStream());
                        out.writeUTF(in.readUTF());
                      }
                    }
                  }
                }catch(Exception e){
                  e.printStackTrace();
                }
              }
            }.start();

          }catch(Exception e){
            e.printStackTrace();
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
