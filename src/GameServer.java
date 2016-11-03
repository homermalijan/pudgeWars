import java.io.*;
import java.net.*;
import java.util.LinkedList;

public class GameServer extends Thread{
  //class attributes
  private ServerSocket serverSocket;

  public GameServer(int port) throws IOException{
    serverSocket = new ServerSocket(port);
    serverSocket.setSoTimeout(10000);
  }//close constructor


 public void run(){
   final LinkedList<Socket> clients = new LinkedList<Socket>();

   System.out.println("listening at port " + serverSocket.getLocalPort() + "...");
   new Thread(){
     public void run(){
        while(true){
          try{
            final Socket server = serverSocket.accept();
            clients.add(server);
            new Thread(){
              public void run(){
                try{
                  DataInputStream in = new DataInputStream(server.getInputStream());
                  System.out.println(in.readUTF());

                  for(Socket s : clients){
                    DataOutputStream out = new DataOutputStream(s.getOutputStream());
                  }
                }catch(Exception e){
                  e.printStackTrace();
                }
              }
            }.start();

          }catch(Exception e){
            e.printStackTrace();
          }
        }
     }
   }.start();

    // boolean connected = true;
    //
    // while(connected){
    //    try{
    //       /* Start accepting data from the ServerSocket */
    //       serverSocket = new ServerSocket(serverSocket.getLocalPort());
    //       Socket server = serverSocket.accept();
    //       System.out.println("Just connected to " + server.getRemoteSocketAddress());
    //
    //       /* Read data from the ClientSocket */
    //       DataInputStream in = new DataInputStream(server.getInputStream());
    //       System.out.println(in.readUTF());
    //
    //       DataOutputStream out = new DataOutputStream(server.getOutputStream());
    //
    //       /* Send data to the ClientSocket */
    //       out.writeUTF("Thank you for connecting to " + server.getLocalSocketAddress() + "\nGoodbye!");
    //       server.close();
    //       connected = false;
    //       System.out.println("Server ended the connection to "+ server.getRemoteSocketAddress());
    //    }catch(SocketTimeoutException s){
    //       System.out.println("Socket timed out!");
    //       break;
    //    }catch(IOException e){
    //       System.out.println("Usage: java GreetingServer <port no.>");
    //       break;
    //    }
    // }//close while
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
