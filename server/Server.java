package server;




// A Java program for a Server
import java.net.*;
import java.io.*;
import java.util.Properties;

public class Server {
  //initialize socket and input stream
  private Socket          socket   = null;
  private ServerSocket    server   = null;
  private DataInputStream in       =  null;
  private static final String OVER = "Over";
  private static final String SHUTDOWN = "shutdown";
  private static final String STOP_SERVER = "stopServer";

  // constructor with port
  public Server(int port,Properties properties)

  {
     boolean loopInProcess = true;
     String shutdownFile = properties.getProperty("shutdownFile");
      // starts server and waits for a connection
    try {
      server = new ServerSocket(port);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    while(loopInProcess) {
      try {

        System.out.println("Server started");

        System.out.println("Waiting for a client ...");

        socket = server.accept();
        System.out.println("Client accepted");

        // takes input from the client socket
        in = new DataInputStream(
            new BufferedInputStream(socket.getInputStream()));

        //String line = in.readUTF();

        // reads message from client until "Over" is sent
        while (true) {
          try {
            String line = in.readUTF();
            System.out.println(line);

            if (OVER.equals(line)) {
              break;
            } else if (SHUTDOWN.equals(line)) {
              createFile(shutdownFile);
              loopInProcess = false;
              break;
            } else if(STOP_SERVER.equals(line)) {
              loopInProcess = false;
              break;
            }
          } catch (IOException i) {
            System.out.println(i);
            break;
          }
        }
        System.out.println("Closing connection");

        // close connection
        socket.close();
        in.close();
      } catch (IOException i) {
        System.out.println(i);
      }
    }
    System.exit(0);
  }

  private void createFile(String shutdownFile) {
     File shutDown = new File(shutdownFile);
    try {
      shutDown.createNewFile();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
