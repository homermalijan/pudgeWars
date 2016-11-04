import java.net.*;
import java.io.*;
import java.util.Scanner;

public class GameClient{
    public static void main(String [] args){
        final Scanner sc = new Scanner(System.in);
        try{
            final String username = args[2];
            String serverName = args[0]; //get IP address of server from first param
            int port = Integer.parseInt(args[1]); //get port from second param

            /* Open a ClientSocket and connect to ServerSocket */
            System.out.println("Connecting to " + serverName + " on port " + port);
            final Socket client = new Socket(serverName, port);

            //Thread for scanning line to be sent to the server
            Thread send = new Thread(){
              public void run(){
                try{
                  while(true){
                    System.out.print(client.getLocalSocketAddress() + ": ");
                    String message = sc.nextLine();
                    OutputStream outToServer = client.getOutputStream();
                    DataOutputStream out = new DataOutputStream(outToServer);
                    out.writeUTF("\n" + username + ": " + message);
                    out.flush();
                  }
                }catch(Exception e){
                  try{
                      client.close();
                  }catch(Exception e2){
                    e2.printStackTrace();
                  }
                }
              }
            };
            send.start();

            //Thread for receiving lines sent by the server
            new Thread(){
              public void run(){
                try{
                  while(true){
                    InputStream inFromServer = client.getInputStream();
                    DataInputStream in = new DataInputStream(inFromServer);
                    while(true) System.out.println(in.readUTF());
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

            /* Receive data from the ServerSocket */

            //insert missing line for closing the socket from the client side
        }catch(IOException e){
            e.printStackTrace();
            System.out.println("Cannot find (or disconnected from) Server");
        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Usage: java GreetingClient <server ip> <port no.> '<your message to the server>'");
        }
    }
}
